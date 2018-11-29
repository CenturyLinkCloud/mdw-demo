package com.centurylink.mdwdemo;

import com.centurylink.mdw.services.util.InitialRequest;
import com.centurylink.mdw.util.log.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan({"com.centurylink.mdw.boot.servlet","com.centurylink.mdw.hub.servlet"})
public class DemoApplication {

    public static void main(String[] args) {
        try {
            LoggerUtil.initializeLogging();
            SpringApplication.run(DemoApplication.class, args);
            new InitialRequest("#/issues/new").submit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}