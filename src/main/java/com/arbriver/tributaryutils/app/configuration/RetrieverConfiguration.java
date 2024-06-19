package com.arbriver.tributaryutils.app.configuration;

import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import com.arbriver.tributaryutils.lib.model.service.ReactiveRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class RetrieverConfiguration {
    @Autowired
    private List<ReactiveRetriever> retrievers;

    @Bean("retriever")
    public ReactiveRetriever getRetriever(Environment env) {
        try {
            String updateRetriever = env.getProperty(CommonConstants.RETRIEVER_CLASS);
            for (ReactiveRetriever retriever : retrievers) {
                if (retriever.getClass().getSimpleName().equals(updateRetriever)) {
                    return retriever;
                }
            }
            throw new ClassNotFoundException(updateRetriever + " not found");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
