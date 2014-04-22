// Test Case 11
	String jobClassName = "class1";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// create job class with jobClassName doesn't exist
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	// assign job class with jobClassName already exists
	jf.assignJobClass("testJob01",jobClassName);
	jf.assignJobClass("testJob02",jobClassName);
	assertEquals("[DEFAULT.testJob01, DEFAULT.testJob02]", jc.getAssignedList().toString());
	// revoke job with jobClassName already exists
	jf.revokeJobClass("testJob02", jobClassName);
	assertEquals("[DEFAULT.testJob01]", jc.getAssignedList().toString());
	
// Test Case 12
	// revoke job with jobClassName doesn't exists
	jf.revokeJobClass("testJob02", "ABC");
	assertTrue(exception.getMessage().contains("no such jobclass(ABC) exist"));
	
// Test Case 13
	String jobClassName = "class1";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// create job class with jobClassName doesn't exist
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	// assign job class with jobClassName already exists
	jf.assignJobClass("testJob01",jobClassName);
	jf.assignJobClass("testJob02",jobClassName);
	assertEquals("[DEFAULT.testJob01, DEFAULT.testJob02]", jc.getAssignedList().toString());
	// revoke job with jobName does not exist
	jf.revokeJobClass("testJob03", jobClassName);
	assertEquals("[DEFAULT.testJob01, DEFAULT.testJob02]", jc.getAssignedList().toString());
	
// Test Case 14
	// revoke job with jobName is empty and jobClassName already exists
	jf.revokeJobClass("null", "ABC");
	assertTrue(exception.getMessage().contains("Name cannot be null"));
	
// Test Case 15
	// revoke job with jobClassName is empty
	jf.revokeJobClass("testJob02", "");
	assertTrue(exception.getMessage().contains("no such jobclass() exist"));
	
// Test Case 16
	// revoke job with jobClassName is null
	jf.revokeJobClass("testJob02", null);
	assertTrue(exception.getMessage().contains("no such jobclass(null) exist"));