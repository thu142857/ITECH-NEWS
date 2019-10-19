package com.itechnews.database.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;

public class JsonFactory {
    private static final String JSON_PATH = "classpath:db/factory/data.json";

    public static JsonNode getJsonNode(String nodeName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = ResourceUtils.getFile(JSON_PATH);
            //Map<String,Object> map = mapper.readValue(file, Map.class);
            JsonNode rootNode = mapper.readTree(file);
            return rootNode.get(nodeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
