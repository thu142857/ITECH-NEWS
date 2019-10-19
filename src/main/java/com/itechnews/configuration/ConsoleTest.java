package com.itechnews.configuration;

import com.itechnews.database.factory.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;

public class ConsoleTest {
    public static void main(String[] args) {
        //get category names from data.json
        JsonNode nodePosts = JsonFactory.getJsonNode("posts");

        if (nodePosts != null) {
            nodePosts.forEach((item) -> {
                item.get("tags").forEach(jsonNode -> {
                    System.out.println("xx=" + jsonNode.asText());
                });
                System.out.println("======");
            });
        }
    }
}
