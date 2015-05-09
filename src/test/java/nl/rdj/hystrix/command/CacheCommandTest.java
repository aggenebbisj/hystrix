package nl.rdj.hystrix.command;

import nl.rdj.hystrix.cache.CacheClient;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Caching
 */
public class CacheCommandTest {
    
    private final CacheClient cache = new CacheClient();
    
    @Test
    public void should_execute_without_cache() {
        String result = new CacheCommand(cache, false, "ping").execute();
        assertThat(result, is("ping-o"));
    }
    
    @Test
    public void should_execute_with_cache() {
        new CacheCommand(cache, false, "ping").execute();
        String result = new CacheCommand(cache, true, "ping").execute();
        assertThat(result, is("Cached: ping-o"));
    }

    @Test
    public void should_not_retrieve_any_value() {
        String result = new CacheCommand(cache, true, "ping").execute();
        assertThat(result, is("Cached: null"));
    }
}
