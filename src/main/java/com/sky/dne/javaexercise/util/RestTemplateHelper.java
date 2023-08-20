package com.sky.dne.javaexercise.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestTemplateHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHelper.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public static String token = "";

	public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
		HttpEntity<R> request = new HttpEntity<>(body);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
		return readValue(response, javaType);
	}

	private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
		T result = null;
		if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
			try {
				result = objectMapper.readValue(response.getBody(), javaType);
			} catch (IOException e) {
				LOGGER.info(e.getMessage());
			}
		} else {
			LOGGER.info("No data found {}", response.getStatusCode());
		}
		return result;
	}

	public <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + token);

			HttpEntity<String> entity = new HttpEntity<String>("", headers);
//			ResponseEntity<String> response = restTemplate.getForEntity(url, entity, String.class);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
			return readValue(response, javaType);
		} catch (HttpClientErrorException exception) {
			if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
				LOGGER.info("No data found {}", url);
			} else {
				LOGGER.info("rest client exception", exception.getMessage());
				exception.printStackTrace();
			}
		}
		return null;
	}

	public <T, R> T putForEntity(String url, R body, Map<String, String> params) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<R> request = new HttpEntity<>(body, headers);
		return null;
	}

	public void delete(String url, Object... uriVariables) {
		try {
			restTemplate.delete(url);
		} catch (RestClientException exception) {
			LOGGER.info(exception.getMessage());
		}
	}

}
