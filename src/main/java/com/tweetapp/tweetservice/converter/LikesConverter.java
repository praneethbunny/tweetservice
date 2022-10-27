package com.tweetapp.tweetservice.converter;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LikesConverter implements DynamoDBTypeConverter<String , List<String>>{

	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Override
	public String convert(List<String> likes) {
		try {
			log.info("inside like converter");
			return MAPPER.writeValueAsString(likes);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in Like unconverter");
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> unconvert(String object) {
		// TODO Auto-generated method stub
		// List<String> likes = new ArrayList<>();
		try {
			log.info("inside like unconverter");
			return  MAPPER.readValue(object, new TypeReference<List<String>>(){});
		} catch (JsonMappingException e) {
			log.error("JsonMappingException in Like unconverter");
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in Reply converter");
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}