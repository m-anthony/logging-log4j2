package org.apache.logging.log4j.core.util;

/**
 * Provides the time stamp used in log events, with nanosecond resolution (but not necessarily nanosecond accuracy).
 * To avoid object allocation, acquiring a time stamp requires several method calls that should follow that pattern:
 * 
 * <pre>
 * {@code
 *     long timeReference = preciseClock.getTimeReference();
 *     long nanoTimeAdjustment = preciseClock.getNanoTimeAdjustment(timeReference);
 *     if (nanoTimeAdjustment == -1L) {
 *         timeReference = preciseClock.updateTimeReference();
 *         nanoTimeAdjustment = preciseClock.getNanoTimeAdjustment(timeReference);
 *         if (nanoTimeAdjustment == -1L) {
 *             throw new AssertionError();
 *         }
 *     }}</pre>
 *
 * When converting these values to the desired time stamp structure, keep in mind that nanoTimeAdjustment can be
 * negative, so rounding towards zero will occur if you need to divide to convert to a lower resolution time stamp
 */
public interface PreciseClock extends Clock {
    // TODO : example
    
    public static final int NANOS_PER_SECOND = 1_000_000_000;
    public static final int NANOS_PER_MILLI = 1_000_000;
    
    /**
     * @return the current cached time reference to call {@link #getNanoTimeAdjustment(long)} with
     */
    public long getTimeReference();

    /**
     * Computes a new time reference suitable to call {@link #getNanoTimeAdjustment(long)}.<br>
     * Subsequent calls to {@link #getTimeReference()} will return the computed value
     * 
     * @return the new time reference
     */
    public long updateTimeReference();

    /**
     * Computes the difference in nanoseconds between current time and the provided time reference.<br>
     * Callers are expected to handle the special return value -1 by calling this method again with a more accurate time
     * reference which can be provided by {@link #updateTimeReference()}
     * 
     * @param timeReference
     *            a number of second after midnight, January 1, 1970 UTC that should be used as a time reference
     * @return the number of nanoseconds between the current time and the time reference, or -1 if the gap is greater or
     *         equals to 2 ^ 32 seconds
     */
    public long getNanoTimeAdjustment(long timeReference);

}
