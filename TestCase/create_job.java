// Test Case 21
	String path = System.getProperty("user.dir");
	String jobName = "/jobTest.job";
	// create job with jobName is "jobTest.job"
	JobExecutable executable = new GroovyScheduledJob(path + jobName, "class", "sender");
	JobDetail jd = jobfacade.createJob(executable);
	assertNotNull(jd);
	assertEquals("DEFAULT.jobTest.job", jd.getKey().toString());
	assertEquals("class org.wiperdog.jobmanager.internal.ObjectJob", jd.getJobClass().toString());
	
// Test Case 22
	String path = System.getProperty("user.dir");
	String jobName = "/ ";
	// create job with jobName is empty
	JobExecutable executable = new GroovyScheduledJob(path + jobName, "class", "sender");
	JobDetail jd = jobfacade.createJob(executable);
	assertTrue(exception.getMessage().contains("Job name cannot be empty"));