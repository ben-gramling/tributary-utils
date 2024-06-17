package com.arbriver.tributaryutils.app;

import com.arbriver.tributaryutils.lib.model.RetrievalService;
import com.arbriver.tributaryutils.lib.reactor.service.BasicRestService;
import com.arbriver.tributaryutils.lib.reactor.service.BasicRetrieverParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class RetrievalServiceConfiguration {
    @Autowired
    private List<RetrievalService> retrievers;
    @Autowired
    private List<BasicRetrieverParser> basicParsers;
    @Autowired
    private List<BasicRestService> basicRestServices;

    @Bean("retriever")
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

    @Bean("parser")
    public BasicRetrieverParser getParser(Environment env) {
        try {
            String updateParser = env.getProperty(CommonConstants.PARSER_CLASS);
            for (BasicRetrieverParser parser : basicParsers) {
                if (parser.getClass().getSimpleName().equals(updateParser)) {
                    return parser;
                }
            }
            throw new ClassNotFoundException(updateParser + " not found");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean("restservice")
    public BasicRestService getRestService(Environment env) {
        try {
            String service = env.getProperty(CommonConstants.REST_SERVICE_CLASS);
            for (BasicRestService restService : basicRestServices) {
                if (restService.getClass().getSimpleName().equals(service)) {
                    return restService;
                }
            }
            throw new ClassNotFoundException(service + " not found");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
