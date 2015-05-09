package nl.rdj.hystrix.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LatentResource {

    private final long latency;

    public LatentResource(long latency) {
        this.latency = latency;
    }
    
    public String getData() {
        addLatency(latency);
        return "Some data";
    }

    private void addLatency(long latency) {
        try {
            Thread.sleep(latency);
        } catch (InterruptedException ex) {
            Logger.getLogger(LatentResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
