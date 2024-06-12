package com.arbriver.tributaryutils.lib.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestService {


//    RestTemplate restTemplate;
//
//    public ResponseEntity<JsonNode> consumeGET(String url, Map<String, String> queryVars, Map<String, String> headersMap) {
//        HttpHeaders headers = new HttpHeaders();
//        headersMap.forEach(headers::set);
//        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
//
//        //build url template
//        String newURL = buildURLTemplate(queryVars, url);
//
//        return restTemplate.exchange(newURL, HttpMethod.GET, requestEntity, JsonNode.class, queryVars);
//    }
//
//    public ResponseEntity<JsonNode> consumePOST(String url, JsonNode body, Map<String, String> queryVars, Map<String, String> headersMap) {
//        HttpHeaders headers = new HttpHeaders();
//        headersMap.forEach(headers::set);
//        HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(body, headers);
//        //build url template
//        String newURL = buildURLTemplate(queryVars, url);
//
//        return restTemplate.exchange(newURL, HttpMethod.POST, requestEntity, JsonNode.class, queryVars);
//    }
//
//    private String buildURLTemplate(Map<String, String> queryVars, String url) {
//        if(queryVars.isEmpty()) {
//            return url;
//        }
//        StringBuilder newURL = new StringBuilder(url + "?");
//        for(String key : queryVars.keySet()) {
//            newURL.append(key).append("={").append(key).append("}&");
//        }
//        newURL.deleteCharAt(newURL.length() - 1);
//
//        return newURL.toString();
//    }
}
