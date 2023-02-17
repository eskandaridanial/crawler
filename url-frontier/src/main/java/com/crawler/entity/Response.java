package com.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Data
@AllArgsConstructor
public class Response {
    public int code;
    public String message;
}
