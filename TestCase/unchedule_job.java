// Test Case 34
	JobDetail jd = jf.createJob(executable);
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	// process schedule for job
	jf.scheduleJob(jd, tr);
	assertEquals("DEFAULT.jobTest", jf.getTrigger(jobName).getKey().toString());
	// unschedule for trigger already exist
	jf.unscheduleJob(tr);
	assertNull(jf.getTrigger(jobName));

// Test Case 35
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	// unschedule for trigger does not exist
	jf.unscheduleJob(tr);
	assertTrue(exception.toString().contains("java.lang.NullPointerException"));

// Test Case 36
	Trigger tr = null;
	// unschedule for trigger does not exist
	jf.unscheduleJob(tr);
	assertTrue(exception.toString().contains("java.lang.NullPointerException"));