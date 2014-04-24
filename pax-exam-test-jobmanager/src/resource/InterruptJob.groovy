import java.io.File;
import org.quartz.JobDataMap;
import org.wiperdog.jobmanager.JobExecutable;

public class InterruptJob implements JobExecutable {
	String status;
	boolean isInterrupt
	String classOfJob; 
	String sender;
	String jobName;
	
	public InterruptJob(String jobname, String classOfJob, String sender) {
		this.status = "init";
		this.jobName = jobname
		this.classOfJob = classOfJob;
		this.sender = sender;
		this.isInterrupt = false
	}
	
	public Object execute(JobDataMap params) throws InterruptedException {
		this.status = "running and will be interrupt"
		this.isInterrupt = true
		
		// TODO Auto-generated method stub
		Thread.sleep(10000)
		this.status = "finish"		
		this.isInterrupt = false
		return null;
	}

	public String getName() {
		return this.jobName;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public boolean checkInterrupt() {
		return this.isInterrupt;
	}
	
	public String getArgumentString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void stop(Thread thread) {
		// TODO Auto-generated method stub
		thread.interrupt()
	}
}