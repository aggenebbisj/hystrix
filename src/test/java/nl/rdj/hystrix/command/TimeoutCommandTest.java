package nl.rdj.hystrix.command;

import nl.rdj.hystrix.resource.LatentResource;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Timeout pattern
 */
public class TimeoutCommandTest {
    
    @Test
    public void should_get_normal_response() {
        LatentResource resource = new LatentResource(50);
        String result = new TimeoutCommand(resource, 100).execute();
        assertThat(result, is("Some data"));
    }
    
    @Test
    public void should_get_fallback_response() {
        LatentResource resource = new LatentResource(150);
        String result = new TimeoutCommand(resource, 100).execute();
        assertThat(result, is("Resource timed out"));
    }
    
}
