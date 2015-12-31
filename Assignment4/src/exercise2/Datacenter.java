package exercise2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Datacenter extends Thread {
	
	private BlockingDeque<Job> jobDeque;
	
	private BigDecimal computingPower;
	
	private BigDecimal account;
	
	private List<Datacenter> datacenterList;
	
	public Datacenter(String name, BigDecimal computingPower) {
		super(name);
		this.jobDeque = new LinkedBlockingDeque<Job>();
		this.computingPower = computingPower;
		this.account = BigDecimal.ZERO;
	}

	@Override
	public void run() {
		while (true) {
			Job selectedJob = null;
			
			if (!jobDeque.isEmpty()) {
				selectedJob = runOwnJob(selectedJob);
			} else {
				stealJobFromOtherDatacenter(selectedJob);
			}
			
			if (selectedJob != null) {
				try {
					sleep((selectedJob.getComputationWork().divide(computingPower, RoundingMode.HALF_UP)).longValue());
					account = account.add(selectedJob.getMoneyAmount());
					System.out.println("[" + getName() + "]: Earned " + selectedJob.getMoneyAmount() + " with execution of " + selectedJob + ".");
					System.out.println("[" + getName() + "]: Account: " + account);
					System.out.println("[" + getName() + "]: JobDeque: " + jobDeque);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Job runOwnJob(Job selectedJob) {
		Job firstJob = jobDeque.peekFirst();
		Job lastJob = jobDeque.peekLast();
		
		try {
			if (firstJob != null && lastJob != null) {
				if (firstJob.getMoneyWorkProportion().compareTo(lastJob.getMoneyWorkProportion()) > 0) {
					selectedJob = jobDeque.takeFirst();
				} else {
					selectedJob = jobDeque.takeLast();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return selectedJob;
	}
	
	public Job stealJobFromOtherDatacenter(Job selectedJob) {
		Datacenter selectedDatacenter = null;
		boolean first = false;
		for (Datacenter datacenter : datacenterList) {
			if (!datacenter.getJobDeque().isEmpty()) {
				Job tempJob = datacenter.getJobDeque().peekFirst();
				if (tempJob != null) {
					if (selectedDatacenter == null || selectedJob == null || tempJob.getMoneyWorkProportion().compareTo(selectedJob.getMoneyWorkProportion()) > 0) {
						selectedDatacenter = datacenter;
						first = true;
						selectedJob = tempJob;
					}
				}
				
				tempJob = datacenter.getJobDeque().peekLast();
				if (tempJob != null) {
					if (selectedDatacenter == null || selectedJob == null || tempJob.getMoneyWorkProportion().compareTo(selectedJob.getMoneyWorkProportion()) > 0) {
						selectedDatacenter = datacenter;
						first = false;
						selectedJob = tempJob;
					}
				}
			}
		}
		
		if (selectedDatacenter != null) {
			try {
				if (first) {
					selectedJob = selectedDatacenter.getJobDeque().takeFirst();
				} else {
					selectedJob = selectedDatacenter.getJobDeque().takeLast();
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("[" + getName() + "]: Stole " + selectedJob + " from " + selectedDatacenter.getName() + ".");
		}
		
		return selectedJob;
	}
	
	public void addJob(Job job) {
		try {
			jobDeque.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public BlockingDeque<Job> getJobDeque() {
		return jobDeque;
	}
	
	public void setDatacenterList(List<Datacenter> datacenterList) {
		this.datacenterList = new ArrayList<Datacenter>(datacenterList);
		this.datacenterList.remove(this);
	}
}