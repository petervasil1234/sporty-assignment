package com.sporty.assignment.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "true")
@Import(RocketMQAutoConfiguration.class)
public class RocketMqConfig {}
