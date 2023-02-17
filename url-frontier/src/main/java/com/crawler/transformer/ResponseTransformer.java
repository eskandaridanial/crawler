package com.crawler.transformer;

import com.crawler.entity.Response;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class ResponseTransformer {

    @Transformer
    public Response transform() {
        return new Response(0, "Your request has been submitted successfully.");
    }
}
