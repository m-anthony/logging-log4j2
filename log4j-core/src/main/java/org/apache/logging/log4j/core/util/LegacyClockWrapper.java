package org.apache.logging.log4j.core.util;

public class LegacyClockWrapper extends AbstractPreciseClock {
    
    private final Clock clock;
    
    public LegacyClockWrapper(Clock clock) {
        super();
        this.clock = clock;
    }

    @Override
    public long currentTimeMillis() {
        return clock.currentTimeMillis();
    }

}
