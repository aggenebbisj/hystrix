package nl.rdj.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import nl.rdj.hystrix.resource.LatentResource;

public class LatentCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP = "default";
    
    private final LatentResource resource;

    public LatentCommand(LatentResource resource) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
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
