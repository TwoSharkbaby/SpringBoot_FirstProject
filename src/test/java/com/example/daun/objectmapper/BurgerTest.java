package com.example.daun.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurgerTest {

    @Test
    public void 자바_객체를_JSON으로_변환() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("통세우 패티", "순쇠고기 패티","토마토");
        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);
        String json = objectMapper.writeValueAsString(burger);
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통세우 패티\",\"순쇠고기 패티\",\"토마토\"]}";
        assertEquals(expected, json);
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
    }

    @Test
    public void JSON을_자바객체로_변환() throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name","맥도날드 슈비버거");
        objectNode.put("price",5500);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add("통세우 패티");
        arrayNode.add("순쇠고기 패티");
        arrayNode.add("토마토");
        objectNode.set("ingredients", arrayNode);
        String json = objectNode.toString();
        Burger burger = objectMapper.readValue(json, Burger.class);
        List<String> ingredients = Arrays.asList("통세우 패티", "순쇠고기 패티","토마토");
        Burger expected = new Burger("맥도날드 슈비버거", 5500, ingredients);
        assertEquals(expected.toString(), burger.toString());
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
        System.out.println(burger.toString());
    }

}