package nl.rdj.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import nl.rdj.hystrix.resource.LatentResource;

public class TimeoutCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP = "default";
    
    private final LatentResource resource;
    private int timeout;

    public TimeoutCommand(LatentResource resource, int timeout) {
        super(
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(
                    HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(timeout)
                )
        );
        this.resource = resource;
    }
    
    @Override
    protected String run() throws Exception {
        return resource.getData();
    }

    @Override
    protected String getFallback() {
        return "Resource timed out";
    }
    
}
