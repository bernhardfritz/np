package exercise2.a;

import java.util.concurrent.atomic.*;

/**
 * NumberRange
 * <p/>
 * Number range class that does not sufficiently protect its invariants
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NumberRange {
    // INVARIANT: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(100);		// initial value changed to 100

    public void setLower(int i) {
        // Warning -- unsafe check-then-act
        if (i < upper.get()) {
        	try {
				Thread.sleep(1);		// added to force race conditions
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	lower.set(i);
        } else {
        	throw new IllegalArgumentException("Can't set lower to " + i + " > upper (" + upper + ")");
        }        
    }

    public void setUpper(int i) {
        // Warning -- unsafe check-then-act
        if (i > lower.get()) {
        	try {
				Thread.sleep(1);		// added to force race conditions
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	upper.set(i);
        } else {
        	throw new IllegalArgumentException("Can't set upper to " + i + " < lower (" + lower + ")");
        }
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
    
    public Integer getLower() {
    	return lower.get();
    }
    
    public Integer getUpper() {
    	return upper.get();
    }
}