package nl.rdj.hystrix.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import nl.rdj.hystrix.resource.LatentResource;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Load shedder pattern with semaphores
 */
public class SemaphoreCommandTest {
    
    @Test
    public void should_return_fallback_response_on_overload() throws InterruptedException, ExecutionException {
        List<Future<String>> commands = new ArrayList<>();
        LatentResource resource = new LatentResource(500);
        
        ExecutorService threadpool = Executors.newFixedThreadPool(10);
        
        // Use up all available threads
        for (int i=0; i < 10; i++)
            commands.add(threadpool.submit(new SemaphoreCommandInvocation(resource)));

        // Wait a moment to make sure all commands are started
        Thread.sleep(250);
        
        // Call will be rejected as all semaphores are in use
        String result = new SemaphoreCommand(resource).execute();
        assertThat(result, is("Fallback response"));
        
        // All other calls should succeed
        for (Future<String> response : commands) 
            assertThat(response.get(), is("Some data"));
    }

    private static class SemaphoreCommandInvocation implements Callable<String> {
        private final LatentResource resource;

        public SemaphoreCommandInvocation(LatentResource resource) {
            this.resource = resource;
        }

        @Override
        public String call() throws Exception {
           return new SemaphoreCommand(resource).execute();
        }
    }
    
}
