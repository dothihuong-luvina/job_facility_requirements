// Test Case 40
	// number job running will be corresponding to concurrency
	String jcname1 = "class";
	int concurrency = 1;
	long maxruntime = 20000; 
	long maxwaittime = 20000;
	String jobName1 = "jobTest1";
	String jobName2 = "jobTest2";
	JobClass jc = jf.createJobClass(jcname1, concurrency, maxruntime, maxwaittime);
	jf.assignJobClass(jobName1, jcname1);
	jf.assignJobClass(jobName2, jcname1);
	Trigger tr1 = jf.createTrigger(jobName1, 0, 1);
	jf.scheduleJob(jd, tr1);
	Trigger tr2 = jf.createTrigger(jobName2, 0, 10);
	jf.scheduleJob(jd, tr2);
	boolean flag = false
	while(true){
		if(jc.getCurrentRunningCount() > 0) {
			System.out.println(jc.getCurrentRunningCount());
			assertEquals(1, jc.getCurrentRunningCount());
			flag = true;
			break;
		}
	}
	assertTrue(flag)