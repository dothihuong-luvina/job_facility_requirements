// Test Case 17
	String jobClassName = "class1";
	int concurrency = 2;
	long maxruntime = 400;
	long maxwaittime = 500;
	// create job class with jobClassName doesn't exist
	jc = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
	jf.assignJobClass("test01", jcname1);
	assertEquals("[DEFAULT.test01]", jc.getAssignedList().toString());
	// del class already exists
	jf.deleteJobClass(jcname1);
	jf.assignJobClass("test02", jcname1);
	assertTrue(exception.getMessage().contains("no such jobclass(class1) exist"));
	
// Test Case 18
	// del class does not exist
	jf.deleteJobClass("classTest");
	assertTrue(exception.getMessage().contains("no such jobclass(classTest) exist"));
	
// Test Case 19
	// del class with jobClassName is empty
	jf.deleteJobClass("");
	assertTrue(exception.getMessage().contains("no such jobclass()"));
	
// Test Case 20
	// del class with jobClassName is empty
	jf.deleteJobClass(null);
	assertTrue(exception.getMessage().contains("no such jobclass(null)"));