package exercise2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class JobGenerator extends Thread {
	
	private List<Datacenter> datacenterList;

	public JobGenerator(List<Datacenter> datacenterList) {
		this.datacenterList = datacenterList;
	}

	@Override
	public void run() {
		int i = 1;
		while(true) {
			for (Datacenter datacenter : datacenterList) {
				Job job = new Job("Job " + i++, new BigDecimal(Math.random() * 700.0 + 300.0).setScale(2, RoundingMode.HALF_UP), 
						new BigDecimal(Math.random() * 90.0 + 10.0).setScale(2, RoundingMode.HALF_UP));
				datacenter.addJob(job);
				System.out.println("[JobGenerator]: Added " + job + " to " + datacenter.getName() + ".");
				System.out.println("[JobGenerator]: JobDeque from " + datacenter.getName() + ": " + datacenter.getJobDeque());
			}
			
			try {
				sleep((long) (Math.random() * 1000.0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}