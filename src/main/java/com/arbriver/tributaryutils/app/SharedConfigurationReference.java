package com.arbriver.tributaryutils.app;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@AutoConfiguration
@EnableMongoRepositories(basePackages = "com.arbriver")
@ComponentScan
public class SharedConfigurationReference {}
