package com.savadanko.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExampleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/files/input?noop=true")
                .log("Processing file: ${file:name}")
                .to("file:src/main/resources/files/output");
    }
}
