package nl.rdj.hystrix.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import nl.rdj.hystrix.resource.LatentResource;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Load shedder pattern
 */
public class LatentCommandTest {
    
    @Test
    public void should_return_fallback_response_on_overload() throws InterruptedException, ExecutionException {
        List<Future<String>> commands = new ArrayList<>();
        LatentResource resource = new LatentResource(500);
        
        // Use up all available threads
        for (int i=0; i < 10; i++)
            commands.add(new LatentCommand(resource).queue());

        // Call will be rejected as thread pool is exhausted
        String result = new LatentCommand(resource).execute();
        assertThat(result, is("Fallback response"));
        
        // All other calls should succeed
        for (Future<String> response : commands) 
            assertThat(response.get(), is("Some data"));
    }
    
}
