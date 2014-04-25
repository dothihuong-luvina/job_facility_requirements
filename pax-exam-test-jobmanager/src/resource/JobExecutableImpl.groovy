import java.io.File;
import org.quartz.JobDataMap;
import org.wiperdog.jobmanager.JobExecutable;

public class JobExecutableImpl implements JobExecutable {
	String fullPathOfFile;
	String classOfJob; 
	String sender;
	String jobName;
	
	public JobExecutableImpl(String fullPathOfFile, String classOfJob, String sender) {
		this.fullPathOfFile = fullPathOfFile;
		this.classOfJob = classOfJob;
		this.sender = sender;
	}
	
	public Object execute(JobDataMap params) throws InterruptedException {
		println "JobExecutable start========="
		Thread.sleep(1000)
		println "JobExecutable end========="
		return null;
	}

	public String getName() {
		File f = new File(fullPathOfFile);
		jobName = f.getName();
		return jobName;
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
