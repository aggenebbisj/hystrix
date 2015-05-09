package nl.rdj.hystrix.command;

import nl.rdj.hystrix.command.CircuitBreakerCommand;
import com.netflix.hystrix.HystrixCircuitBreaker;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Circuit breaker pattern
 */
public class CircuitBreakerCommandTest {
    
    @Test
    public void should_get_normal_response() {
        CircuitBreakerCommand command = new CircuitBreakerCommand("ClosedBreaker", false);
        String result = command.execute();
        assertThat(result, is("Some result"));
        
        HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(command.getCommandKey());
        assertTrue(breaker.allowRequest());
    }
    
    @Test
    public void should_get_fallback_response() {
        CircuitBreakerCommand command = new CircuitBreakerCommand("OpenBreaker", true);
        String result = command.execute();
        assertThat(result, is("Fallback response"));
        
        HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(command.getCommandKey());
        assertFalse(breaker.allowRequest());
    }
    
}
