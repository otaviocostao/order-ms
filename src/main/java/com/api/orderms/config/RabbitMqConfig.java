package com.api.orderms.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String orderCreatedQueue = "orderms-order-created";
}
