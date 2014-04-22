// Test Case 23
	String path = System.getProperty("user.dir");
	String jobName = "/jobTest.job";
	// create job with jobName is "jobTest.job"
	JobExecutable executable = new GroovyScheduledJob(path + jobName, "class", "sender");
	JobDetail jd = jobfacade.createJob(executable);
	assertEquals("DEFAULT.jobTest.job", jd.getKey().toString());
	// remove jobdetail
	boolean flag = jf.removeJob(jd);
	assertTrue(flag);