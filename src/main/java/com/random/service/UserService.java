package com.random.service;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.random.config.ConfigValue;
import com.random.model.User;
import lombok.extern.log4j.Log4j2;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ConfigValue configValue;



    public ResponseEntity getUserBySeed(String seed) throws Exception, HttpClientErrorException {
        Map<String, String> params = new HashMap<>();
        params.put("seed", seed);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = UriComponentsBuilder.fromHttpUrl(configValue.getUriRandomEndpoint()).queryParam("seed", seed).toUriString();
        log.info("request -> {}" ,url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity, String.class,params);
        log.info("response : '{}'",response);
        if(response.getStatusCode().is2xxSuccessful()){
            return mappingUserFromResponse(response.getBody());
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    private ResponseEntity mappingUserFromResponse(String response){
        DocumentContext docCtx = JsonPath.parse(response);
        String gender = docCtx.read("$.results[0].gender");
        String firstname = docCtx.read("$.results[0].name.first");
        String lastname = docCtx.read("$.results[0].name.last");
        String email = docCtx.read("$.results[0].email");
        User user = new User();
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGender(gender);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
