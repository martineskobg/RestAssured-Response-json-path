package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class Main {
    final static String URL = "https://api.publicapis.org/";
    static String category;
    static String api;

    public static void main(String[] args) {
        RestAssured.baseURI = URL;

        Response responseEntries = RestAssured.given().get("entries");
        Response responseCategories = RestAssured.given().get("categories");

        JsonPath jsonPathEntries = responseEntries.jsonPath();
        JsonPath jsonPathCategories = responseCategories.jsonPath();

        // Get String
        for (int i = 0; i < 3; i++) {
            String apiName = jsonPathEntries.getString(String.format("entries[%d].API", i));
            String category = jsonPathEntries.getString(String.format("entries[%d].Category", i));
            System.out.println("Api name: " + apiName + " Category: " + category);
        }

        // Get Int
        int categoriesCount = jsonPathCategories.getInt("count");
        System.out.println("\nCount of categories: " + categoriesCount);

        // Get List
        List<String> categories = jsonPathCategories.getList("categories");
        System.out.println("\nAll Categories as List " + categories);

        // Get Json Object
        Object object = jsonPathEntries.getJsonObject("entries[1]");
        System.out.println("\nAs Object: " + object.toString());

        // Get Map
        System.out.print("\nGet Map:\n");
        Map<Object, Object> entriesMap = jsonPathEntries.getMap("entries[10]");
        entriesMap.entrySet().forEach(entry->{
            System.out.println(entry.getKey()+ " -> " + entry.getValue());
        });
    }
}
