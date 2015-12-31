package exercise2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatacenterMain {

	public static void main(String[] args) {
		List<Datacenter> datacenterList = new ArrayList<Datacenter>();
		datacenterList.add(new Datacenter("Datacenter 1", new BigDecimal(1)));
		datacenterList.add(new Datacenter("Datacenter 2", new BigDecimal(2)));
		datacenterList.add(new Datacenter("Datacenter 3", new BigDecimal(3)));
		
		new JobGenerator(datacenterList).start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (Datacenter datacenter : datacenterList) {
			datacenter.setDatacenterList(datacenterList);
			datacenter.start();
		}
	}
}