# What is a URL Frontier?
A URL frontier is the component of a web crawler that maintains a list of URLs to be crawled next. The URLs are prioritized based on certain criteria (e.g. depth, relevance, etc.) and the crawler retrieves the pages at the URLs and extracts more URLs to add to the frontier. The frontier acts as a queue, with new URLs being added and old URLs being removed as they are processed. The URL frontier is an important component of a web crawler as it helps ensure that the crawl is focused and efficient, rather than randomly accessing pages on the web.

## Tech Stack
This URL frontier has been implemented using Java, Spring Integration, MongoDB, Redis, and Kafka.

### Crawling Integration Flow
1. A URL object is received through an inbound gateway in the following format:
   
  `{
        "link":"https://wwws.google.com/"
   }`

2. The URL object is then sent to a Spring Integration transformer module to normalize the requested URL.

3. After normalizing the URL, a Spring Integration filter component is used to check if the URL exists in the Redis cache.

4. A head request is sent and required parameters (etag, lastModified) from the header are extracted and saved to the object. A default score value is also set for prioritizing the URL in the Redis cache ZSET. The object format after this step will be:
  
  `{link: "https://www.yahoo.com/", etag: "4bc53d48-690e-4cce-905a-adf77f0262ea", lastModified: "Sat, 11 Feb 2023 13:32:59 IRST", score: 1676109777458}`

5. After completing the process above, the URL is sent to the storage channel which is a pub-sub channel with 3 subscribers:

  `redisActivator`, `kafkaActivator`, `mongoActivator`

### Recrawling Integration Flow
To recrawl URLs, there is a poller that gets the oldest URLs from the Redis cache and checks if a webpage has been changed by sending a head request.