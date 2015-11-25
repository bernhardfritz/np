package exercise1;

import java.util.Date;

public class MyEvent implements Event {
	
	private final String origin;
	
	private final Date date;
	
	public MyEvent(String origin) {
		this.origin = origin;
		this.date = new Date();
	}

	@Override
	public String getMessage() {
		return "Origin: " + origin + " - Message: " + date;
	}
}