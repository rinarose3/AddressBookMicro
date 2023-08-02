package git.rinarose3.abjava.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WebService {

    public List<HashMap<String, String>> getFieldNames() {
        String swaggerUrl = "https://localhost:8443/v3/api-docs";
        String username = "git.rinarose3.abjava.web";
        String password = "rinarose3";

        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeaderValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(swaggerUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String swaggerJson = response.getBody();
                System.out.println(swaggerJson);

                if (swaggerJson != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(swaggerJson);
                    JsonNode addressBookNode = jsonNode.findValue("AddressBook");
                    System.out.println(addressBookNode);

                    if (addressBookNode != null) {
                        JsonNode addressBookProperties = addressBookNode.get("properties");
                        System.out.println("Properties for AddressBook:");
                        System.out.println(addressBookProperties);

                        List<HashMap<String, String>> fieldNames = new ArrayList<>();
                        Iterator<String> fieldNames2 = addressBookProperties.fieldNames();

                        for (JsonNode property : addressBookProperties) {
                            String fieldName = fieldNames2.next(); // Get the field name
                            if (fieldName.equals("id")) {
                                continue;
                            }
                            System.out.println(fieldName);

                            // Check if the field information is not null and contains the "description" field
                            if (property.has("description")) {
                                String description = property.get("description").asText();
                                String value = null;



                                HashMap<String, String> item = new HashMap<>();
                                item.put("field", fieldName);
                                item.put("label", description);
                                item.put("value", value);
                                fieldNames.add(item);
                            } else {
                                // If "description" field is not found directly in the property, check for nested properties
                                JsonNode nestedProperties = property.path("properties");
                                if (nestedProperties != null && nestedProperties.has(fieldName) && nestedProperties.get(fieldName).has("description")) {
                                    String description = nestedProperties.get(fieldName).get("description").asText();
                                    String value = null;

                                    HashMap<String, String> item = new HashMap<>();
                                    item.put("field", fieldName);
                                    item.put("label", description);
                                    item.put("value", value);
                                    fieldNames.add(item);
                                } else {
                                    System.err.println("Description not found for field: " + fieldName);
                                }
                            }
                        }

                        System.out.println("Список:" + fieldNames);
                        return fieldNames;
                    } else {
                        System.err.println("AddressBook not found in the JSON.");
                    }
                } else {
                    System.err.println("Failed to retrieve Swagger description. Status code: " + response.getStatusCodeValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<HashMap<String,String>> findById(Long id){
        List<HashMap<String, String>> findById = new ArrayList<>();
        return findById;
    }
}
