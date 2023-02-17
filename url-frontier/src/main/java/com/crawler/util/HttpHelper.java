package com.crawler.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class HttpHelper {

    private final RestTemplate restTemplate;

    public HttpHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * an exception-less head request sender.
     **/
    public ResponseEntity<Void> sendHeadRequest(String url) {
        try {
            return restTemplate.exchange(url, HttpMethod.HEAD, null, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
        }
    }
}
