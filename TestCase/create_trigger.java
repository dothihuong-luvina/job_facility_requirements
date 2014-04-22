// Test Case 27
	String jobName = "jobTest";
	// create Trigger with jobName, delay, interval
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	assertNotNull(tr);
	assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	
// Test Case 28
	String jobName = "jobTest";
	// create Trigger with jobName
	Trigger tr = jf.createTrigger(jobName);
	assertNotNull(tr);
	assertEquals("DEFAULT.jobTest", tr.getKey().toString());

// Test Case 29
	String jobName = "jobTest";
	// create Trigger with jobName, long
	Trigger tr = jf.createTrigger(jobName, 10);
	assertNotNull(tr);
	assertEquals("DEFAULT.jobTest", tr.getKey().toString());

// Test Case 30
	String jobName = "jobTest";
	// create Trigger with jobName, Date
	Trigger tr = jf.createTrigger(jobName, "10:10:10.1");
	assertNotNull(tr);
	assertEquals("DEFAULT.jobTest", tr.getKey().toString());

// Test Case 31
	String jobName = "jobTest";
	// create Trigger with jobName, crontab
	Trigger tr = jf.createTrigger(jobName, "0/60 * * * * ?");
	assertNotNull(tr);
	assertEquals("DEFAULT.jobTest", tr.getKey().toString());

// Test Case 32
	String jobName = "";
	// create Trigger with jobName, delay, interval
	// value of jobName is empty
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	assertTrue(exception.getMessage().contains("Trigger name cannot be null or empty"));
	
// Test Case 33
	String jobName = null;
	// create Trigger with jobName, delay, interval
	// value of jobName is null
	Trigger tr = jf.createTrigger(jobName, 0, 200);
	assertTrue(exception.getMessage().contains("Trigger name cannot be null or empty"));
