package nl.rdj.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import nl.rdj.hystrix.cache.CacheClient;

public class CacheCommand extends HystrixCommand<String> {
    private static final String COMMAND_GROUP = "primary";
    private final CacheClient cache;
    private final boolean failPrimary;
    private final String request;

    public CacheCommand(CacheClient cache, boolean failPrimary, String request) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
        this.cache = cache;
        this.failPrimary = failPrimary;
        this.request = request;
    }
    
    @Override
    protected String run() throws Exception {
        if (failPrimary)
            throw new RuntimeException("Failure of primary");
        
        String response = request + "-o";
        cache.add(request, response);
        return response;
    }

    @Override
    protected String getFallback() {
        return "Cached: " + new FBCommand(cache, request).execute();
    }

    private static class FBCommand extends HystrixCommand<String>{
        private static final String COMMAND_GROUP = "fallback";
        private final CacheClient cache;
        private final String request;
        
        public FBCommand(CacheClient cache, String request) {
            super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
            this.cache = cache;
            this.request = request;
        }

        @Override
        protected String run() throws Exception {
            return cache.get(request);
        }
    }
    
    

}
