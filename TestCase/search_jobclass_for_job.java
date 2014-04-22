// Test Case 42
	// search job class with the job has been assigned to class
	String jcname1 = "class";
	int concurrency = 1;
	long maxruntime = 20000; 
	long maxwaittime = 20000;
	String jobName1 = "jobTest1";
	JobClass jc = jf.createJobClass(jcname1, concurrency, maxruntime, maxwaittime);
	jf.assignJobClass(jobName1, jcname1);
	assertTrue(jf.findJobClassForJob(jobName1).length > 0);
	
// Test Case 43
	// search job class with the job is not assigned to class
	String jobName = "jobTest";
	assertTrue(jf.findJobClassForJob(jobName).length == 0);
	
// Test Case 44
	// search job class with jobName is empty
	String jobName = "";
	assertTrue(jf.findJobClassForJob(jobName).length == 0);
	// search job class with jobName is null
	jobName = null;
	assertTrue(jf.findJobClassForJob(jobName).length == 0);