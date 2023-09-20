package git.rinarose3.abjava.web;

import com.fasterxml.jackson.databind.JsonNode;
import git.rinarose3.abjava.models.AddressBook;
import git.rinarose3.abjava.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

@Service
public class WebService {

    private final AddressBookRepository addressBookRepository;

    @Autowired
    public WebService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    public HashMap<String, HashMap<String, String>> getFieldNames() {
        String url = "https://localhost:8443/v3/api-docs";
        String username = "git.rinarose3.abjava.web";
        String password = "rinarose3";

        try {
            JsonNode jsonNode = GetBasicToJsonNode.GetBodyResponse(url, username, password);
            return extractFieldNames(jsonNode);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public HashMap<String, HashMap<String, String>> extractFieldNames(JsonNode jsonNode) {

        HashMap<String, HashMap<String, String>> fieldNames = new HashMap<>();
        try {
            Set<Map.Entry<String, JsonNode>> abProperties = jsonNode.findValue("AddressBook").get("properties").properties();

            for (Map.Entry<String, JsonNode> property : abProperties) {

                String propertyKey = property.getKey();

                if (!propertyKey.equals("id")) {

                    HashMap<String, String> item = new HashMap<>();
                    item.put("label", property.getValue().get("description").asText());
                    item.put("value", null);
                    fieldNames.put(propertyKey, item);
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return fieldNames;
    }

    public HashMap<String, HashMap<String, String>> getFieldNames(Long pid) {

        HashMap<String, HashMap<String, String>> fieldNames = getFieldNames();

        Optional<AddressBook> oab = addressBookRepository.findById(pid);

        if (oab.isPresent()) {
            AddressBook ab = oab.get();

            for (Field field : ab.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                try {
                    HashMap<String, String> item = fieldNames.get(fieldName);
                    item.put("value", (String)field.get(ab));
                    fieldNames.put(fieldName, item);
                } catch (Exception e) {
                    System.err.println("An error occurred: " + e.getMessage());
                }
            }
        }
        return fieldNames;
    }
}
