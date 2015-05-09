package nl.rdj.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE;
import nl.rdj.hystrix.resource.LatentResource;

public class SemaphoreCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP = "default";
    
    private final LatentResource resource;

    public SemaphoreCommand(LatentResource resource) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionIsolationStrategy(SEMAPHORE)
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
        return "Fallback response";
    }
    
}
