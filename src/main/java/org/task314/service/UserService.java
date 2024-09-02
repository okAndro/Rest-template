package org.task314.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.task314.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private static final String USER_API_URL = "http://94.198.50.185:7081/api/users/";

    @Autowired
    private RestTemplate restTemplate;

    private String sessionId;

    public List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(USER_API_URL, User[].class);
        extractSessionID(response.getHeaders());
        return Arrays.asList(response.getBody());
    }

    public String saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(USER_API_URL, request, String.class);
        return response.getBody();
    }

    public String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(USER_API_URL, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    public String deleteUser(Long id) {
        String url = USER_API_URL + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }

    private void extractSessionID(HttpHeaders headers) {
        List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            this.sessionId = cookies.get(0).split(";")[0];
        }
    }
}