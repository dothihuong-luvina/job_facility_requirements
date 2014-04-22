// Test Case 1
	// create job class with jobClassName doesn't exist
	String jobClassName = "class1";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	assertEquals(jobClassName, jc.getName());
	assertEquals(concurrency, jc.getConcurrency());
	assertEquals(maxruntime, jc.getMaxRunTime());
	assertEquals(maxwaittime, jc.getMaxWaitTime());
	
// Test Case 2
	String jobClassName = "class1";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// create job class jc1 with jobClassName doesn't exit
	jc1 = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	// create job class jc2 with jobClassName already exist
	jc2 = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	assertEquals(jc1, jc2);
	assertEquals(jobClassName, jc2.getName());
	assertEquals(concurrency, jc2.getConcurrency());
	assertEquals(maxruntime, jc2.getMaxRunTime());
	assertEquals(maxwaittime, jc2.getMaxWaitTime());
	
// Test Case 3
	String jobClassName = "";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// can not create job class with value is empty
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	assertNull(jc);
	assertTrue(exception.getMessage().contains("JobListener name cannot be empty"));;
	
// Test Case 4
	String jobClassName = null;
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// can not create job class with value is empty
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	assertNull(jc);
	assertTrue(exception.getMessage().contains("JobListener name cannot be empty"));