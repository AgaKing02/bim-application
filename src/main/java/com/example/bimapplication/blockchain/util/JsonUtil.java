package com.example.bimapplication.blockchain.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owlike.genson.Genson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonUtil {

    private final ObjectMapper objectMapper;
    private final Genson genson = new Genson();


    public <T> List<T> mapToListObject(byte[] data, Class<T> t) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<T>>() {
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> T mapToObject(byte[] data, Class<T> t) {
        try {
            return objectMapper.readValue(data, t);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public <T> T mapJsonToObject(String json, Class<T> t) {
        try {
            return objectMapper.readValue(json, t);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    public String mapToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
