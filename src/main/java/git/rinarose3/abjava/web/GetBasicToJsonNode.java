package git.rinarose3.abjava.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.Objects;

public class GetBasicToJsonNode {

    public static JsonNode GetBodyResponse (String url, String username, String password) throws Exception {

        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeaderValue);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String jsonBody = response.getBody();

            if (jsonBody != null && !Objects.equals(jsonBody, "")) {
                ObjectMapper objectMapper = new ObjectMapper();

                return objectMapper.readTree(jsonBody);
            }
            else {
                throw new Exception("Answer is empty");
            }
        }
        else {
            throw  new Exception("HTTP request failed with status code: " + response.getStatusCode().value());
        }
    }
}
