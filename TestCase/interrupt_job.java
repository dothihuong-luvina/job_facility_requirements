// Test Case 37
	// interrupt job running
	String jobName1 = "jobTest1";
	String [] joblist = ["/bin/sleep", "10"]
	def jd1 = jf.createJob(jobName1, joblist, false);
	
	def tr1 = jf.createTrigger(jobName1, 100);
	jf.scheduleJob(jd1, tr1);
	
	Thread.sleep(1000)
	def jobRunningcount1 = jf.getJobRunningCount(jobName1)
	assertEquals(1, jobRunningcount1)
	
	//Interrupt job
	jf.interruptJob(jobName1)
	
	Thread.sleep(500)
	def jobRunningcount2 = jf.getJobRunningCount(jobName1)
	assertEquals(0, jobRunningcount2)
	
// Test Case 38
	// Interrupt running job if job run time > maxruntime of job class. 
	String jcname = "class";
	int concurrency = 1;
	long maxruntime = 1000;
	String jobName1 = "jobTest1";
	def jc = jf.createJobClass(jcname);
	jc.setConcurrency(concurrency)
	jc.setMaxRunTime(maxruntime)		
	
	String [] joblist = ["/bin/sleep", "10"]
	def jd1 = jf.createJob(jobName1, joblist, false);
	
	jc.addJob(jf.jobKeyForName(jobName1))
	
	def tr1 = jf.createTrigger(jobName1, 0);
	jf.scheduleJob(jd1, tr1);
	
	Thread.sleep(500)
	
	def jobRunningcount1 = jf.getJobRunningCount(jobName1)
	assertEquals(1, jobRunningcount1)
	
	//After 1.5s, job will be interrupted
	Thread.sleep(1000)
	def jobRunningcount2 = jf.getJobRunningCount(jobName1)
	assertEquals(0, jobRunningcount2)

// Test Case 39
	// Interrupt if job wait time > maxwaittime of job class
	String jcname = "class";
	int concurrency = 1;
	long maxruntime = 1000;
	long maxwaittime = 1000;
	String jobName1 = "jobTest1";
	String jobName2 = "jobTest2";
	def jc = jf.createJobClass(jcname, concurrency, maxwaittime, maxruntime);
	jf.assignJobClass(jobName1, jcname);
	jf.assignJobClass(jobName2, jcname);
	def executable1 = jobInteruptCls.newInstance(jobName1, "class", "sender");
	def executable2 = jobInteruptCls.newInstance(jobName2, "class", "sender");
	def jd1 = jf.createJob(executable1);
	def jd2 = jf.createJob(executable2);
	def tr1 = jf.createTrigger(jobName1, 0);
	def tr2 = jf.createTrigger(jobName2, 100);
	jf.scheduleJob(jd1, tr1);
	jf.scheduleJob(jd2, tr2);
	
	Thread.currentThread().sleep(500)
	// interrupt job running
	def jobStatus1 = executable1.getStatus()
	def isInterrupt1 = executable1.checkInterrupt()
	def jobStatus2 = executable2.getStatus()
	def isInterrupt2 = executable2.checkInterrupt()
	assertEquals(jobStatus1, "running and will be interrupt")		
	assertEquals(jobStatus2, "init")		
	assertEquals(isInterrupt1, true)		
	assertEquals(isInterrupt2, false)
	def listRunningJob1 = jf.getJobRunningCount(jobName1)
	assertEquals(1,listRunningJob1)
	def listRunningJob2 = jf.getJobRunningCount(jobName2)
	assertEquals(0,listRunningJob2)
	
	//Interrupt job
	Thread.currentThread().sleep(15000)
	println (jobName2 + " will be given up !!!")
	
	// wait for interrupt job
	jobStatus1 = executable1.getStatus()
	isInterrupt1 = executable1.checkInterrupt()
	jobStatus2 = executable2.getStatus()
	isInterrupt2 = executable2.checkInterrupt()
	assertEquals(jobStatus1, "finish")		
	assertEquals(jobStatus2, "init")		
	assertEquals(isInterrupt1, false)		
	assertEquals(isInterrupt2, false)
