package exercise1;

import java.util.Date;

public class MyEvent implements Event {
	
	private String origin;
	
	private Date date;
	
	public MyEvent(String origin) {
		this.origin = origin;
		this.date = new Date();
	}

	@Override
	public String getMessage() {
		return "Origin: " + origin + " - " + date;
	}
}