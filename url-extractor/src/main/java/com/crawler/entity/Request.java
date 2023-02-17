package com.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Data
public class Request implements Serializable {
    private String link;
}
