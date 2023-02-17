package com.crawler.activator;

import com.crawler.entity.Request;
import com.crawler.util.HttpHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class HeadActivator {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

    private final HttpHelper httpHelper;

    public HeadActivator(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    public boolean isModified(@Payload Request request) {
        if (request == null)
            return false;

        ResponseEntity<Void> response = httpHelper.sendHeadRequest(request.getLink());
        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            String lastModified = responseHeaders.getFirst("Last-Modified");
            String etag = responseHeaders.getFirst("ETag");
            return (lastModified != null && !lastModified.equals(request.getLastModified()))
                    || (etag != null && !etag.equals(request.getEtag()));
        }
        return false;
    }

    public Request setParams(@Payload Request request) {
        ResponseEntity<Void> response = httpHelper.sendHeadRequest(request.getLink());
        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            request.setLastModified(responseHeaders.getFirst("Last-Modified") != null
                    ? responseHeaders.getFirst("Last-Modified")
                    : dateFormat.format(new Date()));
            request.setEtag(responseHeaders.getFirst("ETag") != null
                    ? responseHeaders.getFirst("ETag")
                    : UUID.randomUUID().toString());
        }
        return request;
    }
}
