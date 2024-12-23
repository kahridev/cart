package com.trendyol.cart;

import io.micrometer.common.util.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CartApplication {

    public static void main(String[] args) {
        String requestFile = System.getProperty("requestFile");
        if (StringUtils.isBlank(requestFile)) {
            SpringApplication.run(CartApplication.class, args);
        } else {
            ApplicationContext context = new SpringApplicationBuilder(CartApplication.class).run(args);
            context.getBean(CommandRunner.class).executeCommands(requestFile, "output.txt");
        }
    }
}
