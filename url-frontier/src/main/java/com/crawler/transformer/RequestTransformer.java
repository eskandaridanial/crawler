package com.crawler.transformer;

import com.crawler.entity.Request;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class RequestTransformer {

    @Transformer
    public Request normalize(@Payload Request request) throws URISyntaxException {
        URI normalized = new URI(request.getLink());
        normalized = new URI(normalized.getScheme().toLowerCase(),
                normalized.getUserInfo(),
                normalized.getHost().toLowerCase(),
                normalized.getPort(),
                normalized.getPath(),
                normalized.getQuery(),
                normalized.getFragment());
        request.setLink(normalized.toString());
        return request;
    }
}
