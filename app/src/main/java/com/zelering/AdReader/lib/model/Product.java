package com.zelering.AdReader.lib.model;

import java.io.Serializable;

/**
 * Created by lcom64 on 18/7/17.
 */

public class Product implements Serializable {
    String name;
    String uri;

    public Product(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
