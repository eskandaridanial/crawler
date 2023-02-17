package com.crawler.config;

import com.crawler.entity.Request;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class ExtractActivator {


    public List<String> extract(@Payload Request request) throws IOException {
        List<String> links = new ArrayList<>();
        URL url = new URL(request.getLink());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the web page content into a string buffer
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // Use regular expressions to find all URLs in the page content
        Pattern pattern = Pattern.compile("<a\\s+href\\s*=\\s*\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content.toString());
        while (matcher.find()) {
            String link = matcher.group(1);
            // Ignore "mailto:" links and links that start with "#"
            if (!link.startsWith("mailto:") && !link.startsWith("#")) {
                // Make the link absolute if it's a relative link
                if (link.startsWith("/")) {
                    link = url.getProtocol() + "://" + url.getHost() + link;
                }
                links.add(link);
            }
        }

        return links;
    }
}
