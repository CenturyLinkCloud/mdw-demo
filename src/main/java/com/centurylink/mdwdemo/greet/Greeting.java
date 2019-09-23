package com.centurylink.mdwdemo.greet;

import org.springframework.stereotype.Component;

@Component
public class Greeting {

    private final long id;
    private final String content;

    /**
     * Default values when autowired.
     */
    public Greeting() {
        id = 12345;
        content = "World";
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}