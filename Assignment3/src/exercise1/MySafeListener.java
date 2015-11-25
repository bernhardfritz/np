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

    public static MySafeListener newInstance(String name) {
        MySafeListener safe = new MySafeListener(name);
        return safe;
    }
    
    public static void register(MySafeListener safe, EventSource source) {
    	source.registerListener(safe.listener);
    }

    public void doSomething(Event e) {
    	System.out.println("[" + listener.getName() + "] " +  e.getMessage());
    }
}