package com.arbriver.tributaryutils.app;

import com.arbriver.tributaryutils.lib.model.RetrievalService;
import com.arbriver.tributaryutils.lib.model.UpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class RetrievalServiceConfiguration {
    @Autowired
    private List<RetrievalService> retrievers;

    @Bean("retriever")
    @ConditionalOnProperty(prefix = "processor", name = "class")
    public RetrievalService getProcessor(Environment env) {
        try {
            String updateRetriever = env.getProperty(CommonConstants.RETRIEVER_CLASS);
            for (RetrievalService retriever : retrievers) {
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
