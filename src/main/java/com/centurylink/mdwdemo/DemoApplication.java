package com.centurylink.mdwdemo;

import com.centurylink.mdw.services.util.InitialRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan({"com.centurylink.mdw.boot.servlet","com.centurylink.mdw.hub.servlet"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        new InitialRequest("#/issues/new").submit();
    }
}