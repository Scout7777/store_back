package com.histsys.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

public class JsonUtils {
    private static JsonMapper jsonMapper = new JsonMapper();

    public static String toJson(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T toObject(Class<T> clazz, String json) {
        try {
            return jsonMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
