package com.arbriver.tributaryutils.app.configuration;

import com.arbriver.tributaryutils.lib.service.BasicRestService;
import com.arbriver.tributaryutils.lib.service.BasicRetrieverParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class RetrievalServiceConfiguration {
    @Autowired
    private List<BasicRetrieverParser> basicParsers;
    @Autowired
    private List<BasicRestService> basicRestServices;

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
