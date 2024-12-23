package com.trendyol.cart;

import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@Log4j2
@SpringBootApplication
public class CartApplication {

    public static void main(String[] args) {
        String requestFile = System.getProperty("requestFilePath");
        if (StringUtils.isBlank(requestFile)) {
            SpringApplication.run(CartApplication.class, args);
        } else {
            ApplicationContext context = new SpringApplicationBuilder(CartApplication.class).run(args);
            context.getBean(CommandRunner.class).executeCommands(requestFile, "output.txt");
        }
    }
}
