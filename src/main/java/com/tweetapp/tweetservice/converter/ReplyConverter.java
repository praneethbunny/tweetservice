package com.tweetapp.tweetservice.converter;

import java.io.IOException;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweetservice.model.TweetReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplyConverter<T extends Object> implements DynamoDBTypeConverter<String , List<String>> {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Override
	public String convert(List<String> replies) {
		try {
			log.info("inside reply conerter");
			return MAPPER.writeValueAsString(replies);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in Reply converter");
			throw new RuntimeException(e);
		}
	}

	@Override
    public List<String> unconvert(String object) {
        try {
        	log.info("inside reply unconverter");
            T unconvertedObject = MAPPER.readValue(object, 
                new TypeReference<T>() {
            });
            return (List<String>) unconvertedObject;
        } catch (IOException e) {
        	log.error("IOException in reply unconverter");
            e.printStackTrace();
        }
        return null;
    }
}