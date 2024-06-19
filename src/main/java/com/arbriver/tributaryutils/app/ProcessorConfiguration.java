package com.arbriver.tributaryutils.app;

import com.arbriver.tributaryutils.lib.reactor.model.ReactiveProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import org.springframework.core.env.Environment;
import java.util.List;

@Configuration
public class ProcessorConfiguration {
    @Autowired
    private List<ReactiveProcessor> reactiveProcessors;

    @Bean("processor")
    @ConditionalOnProperty(prefix = "processor", name = "class")
    public ReactiveProcessor getProcessor(Environment env) {
        try {
            String updateProcessor = env.getProperty(CommonConstants.PROCESSOR_CLASS);
            for (ReactiveProcessor processor : reactiveProcessors) {
                if (processor.getClass().getSimpleName().equals(updateProcessor)) {
                    return processor;
                }
            }
            throw new ClassNotFoundException(updateProcessor + " not found");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
