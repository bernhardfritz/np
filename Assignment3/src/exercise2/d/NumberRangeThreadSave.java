package exercise2.d;

import java.util.concurrent.atomic.*;

import exercise2.a.NumberRange;

/**
 * NumberRange
 * <p/>
 * Number range class that does not sufficiently protect its invariants
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NumberRangeThreadSave extends NumberRange {
    // INVARIANT: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(100);

    public synchronized void setLower(int i) {
        // Warning -- unsafe check-then-act
        if (i < upper.get()) {
        	try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	lower.set(i);
        } else {
        	throw new IllegalArgumentException("Can't set lower to " + i + " > upper (" + upper + ")");
        }        
    }

    public synchronized void setUpper(int i) {
        // Warning -- unsafe check-then-act
        if (i > lower.get()) {
        	try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	upper.set(i);
        } else {
        	throw new IllegalArgumentException("Can't set upper to " + i + " < lower (" + lower + ")");
        }
    }

    public synchronized boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
    
    public synchronized Integer getLower() {
    	return lower.get();
    }
    
    public synchronized Integer getUpper() {
    	return upper.get();
    }
}