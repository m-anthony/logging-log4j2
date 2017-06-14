package org.apache.logging.log4j.core.util;

public abstract class AbstractPreciseClock implements PreciseClock {

    private static final long MAXIMUM_ADJUSTMENT_SECONDS = 1L << 32;

    // no need for volatile there, value should be changed every 136 years only, and even if it happens 
    // because of clock adjustment the worst scenario that can happen is getNanoTimeAdjustment() returning -1
    private long timeReference = 0L;

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getTimeReference() {
        return timeReference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long updateTimeReference() {
        // use a value slightly in the past, so that getNanoTimeAdjustment will not return 
        // the special value -1 when called with the updated time reference
        long newReference = this.currentTimeMillis() / 1000 - 1;
        timeReference = newReference;
        return newReference;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getNanoTimeAdjustment(final long timeReference) {
        long currentTimeMillis = this.currentTimeMillis();
        if (Math.abs(currentTimeMillis / 1000 - timeReference) < MAXIMUM_ADJUSTMENT_SECONDS) {
            return NANOS_PER_MILLI * (currentTimeMillis - 1000 * timeReference);
        } else {
            return -1L;
        }
    }

}
