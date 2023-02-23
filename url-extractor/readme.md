# What is a URL Extractor?
A URL extractor is a program or tool that scans a webpage or document and extracts all of the URLs that it contains. This can be useful for a variety of purposes, such as building a list of links to crawl, finding broken links on a website, or analyzing the link structure of a webpage. URL extractors typically use regular expressions or other pattern matching techniques to identify and extract URLs from the HTML or text of a document. They can also be used to extract other types of data, such as email addresses or phone numbers, from a webpage or document.

### Tech Stack
A URL frontier has been implemented using Java, Spring Integration, and Kafka.

### Extracting Integration Flow
1. First, the extractor component consumes the message from the kafka topic. (The link that was published by the URL-FRONTIER)


2. Then, the consumed URL will be passed through the extractorActivator and all the links inside the given URL will be extracted by regular expression pattern matching technique.


3. After extracting all the links, they will be passed through an outbound gateway which call the URL-FRONTIER API.  