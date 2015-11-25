package exercise1;

public class MySafeListener {
    private final EventListener listener;

    private MySafeListener(String name) {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
            
            public String getName() {
            	return name;
            }
        };
    }

    public static MySafeListener newInstance(String name, EventSource source) {
        MySafeListener safe = new MySafeListener(name);
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    	System.out.println(Thread.currentThread().getName() + ": " + e.getMessage());
    }
}