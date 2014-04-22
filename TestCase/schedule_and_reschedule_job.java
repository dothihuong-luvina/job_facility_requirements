// Test Case 30
	JobDetail jd = jf.createJob(executable);
	// Create trigger with the parameters: jobName, delay, interval.
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	// process schedule for job
	jf.scheduleJob(jd, tr);

// Test Case 31
	JobDetail jd = jf.createJob(executable);
	// Create trigger with only one parameters: jobName
	Trigger tr = jf.createTrigger(jobName);
	// process schedule for job
	jf.scheduleJob(jd, tr);

// Test Case 32
	JobDetail jd = jf.createJob(executable);
	// Create trigger with the parameters: jobName, long.
	Trigger tr = jf.createTrigger(jobName, 10);
	// process schedule for job
	jf.scheduleJob(jd, tr);

// Test Case 33
	JobDetail jd = jf.createJob(executable);
	// Create trigger with the parameters: jobName, Date.
	Trigger tr = jf.createTrigger(jobName, "10:10:10.1");
	// process schedule for job
	jf.scheduleJob(jd, tr);

// Test Case 34
	JobDetail jd = jf.createJob(executable);
	// Create trigger with the parameters: jobName, crontab.
	Trigger tr = jf.createTrigger(jobName, "0/60 * * * * ?");
	// process schedule for job
	jf.scheduleJob(jd, tr);

// Test Case 36
	JobDetail jd = jf.createJob(executable);
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	// process schedule for job
	jf.scheduleJob(jd, tr);
	Thread.sleep(5000);
	// re-schedule for job
	tr = jf.createTrigger(jobName, 10, 100);
	jf.scheduleJob(jd, tr);

// Test Case 37
	JobDetail jd = null;
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	// process schedule for job with jobdetail is null
	jf.scheduleJob(jd, tr);
	assertTrue(exception.toString().contains("java.lang.NullPointerException"));

// Test Case 38
	JobDetail jd = jf.createJob(executable);
	Trigger tr = null;
	// process schedule for job with Trigger is null
	jf.scheduleJob(jd, tr);
	assertTrue(exception.toString().contains("java.lang.NullPointerException")); 
