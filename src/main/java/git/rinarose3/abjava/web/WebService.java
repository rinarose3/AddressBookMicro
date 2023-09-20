package git.rinarose3.abjava.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class WebService {

    public List<HashMap<String, String>> getFieldNames() {
        String url = "https://localhost:8443/v3/api-docs";
        String username = "git.rinarose3.abjava.web";
        String password = "rinarose3";

        try {
            JsonNode jsonNode = GetBasicToJsonNode.GetBodyResponse(url, username, password);
            return extractFieldNames(jsonNode);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public List<HashMap<String, String>> extractFieldNames(JsonNode jsonNode) {

        List<HashMap<String, String>> fieldNames = new ArrayList<>();
        try {
            Set<Map.Entry<String, JsonNode>> abProperties = jsonNode.findValue("AddressBook").get("properties").properties();

            for (Map.Entry<String, JsonNode> property : abProperties) {

                String propertyKey = property.getKey();

                if (!propertyKey.equals("id")) {

                    HashMap<String, String> item = new HashMap<>();
                    item.put("field", propertyKey);
                    item.put("label", property.getValue().get("description").asText());
                    item.put("value", null);
                    fieldNames.add(item);
                }
            }
        }catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return fieldNames;
    }
}
