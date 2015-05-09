package nl.rdj.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CircuitBreakerCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP = "default";
    
    public CircuitBreakerCommand(String name, boolean isOpen) {
        super(
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                    HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(isOpen)
                )
        );
    }
    
    @Override
    protected String run() throws Exception {
        return "Some result";
    }

    @Override
    protected String getFallback() {
        return "Fallback response";
    }
    
}
