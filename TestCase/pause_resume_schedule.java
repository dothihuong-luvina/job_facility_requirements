// Test Case 41
	// check pause and resume schedule
	String jcname1 = "class";
	int concurrency = 1;
	long maxruntime = 20000; 
	long maxwaittime = 20000;
	String jobName1 = "jobTest1";
	JobClass jc = jf.createJobClass(jcname1, concurrency, maxruntime, maxwaittime);
	jf.assignJobClass(jobName1, jcname1);
	Trigger tr1 = jf.createTrigger(jobName1, 0, 1);
	jf.scheduleJob(jd, tr1);
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
	while(true) {
		// pause schedule
		jf.pause();
		Thread.sleep(500);
		assertEquals(0, jc.getCurrentRunningCount());
		// resume schedule
		jf.resume();
		Thread.sleep(500);
		assertEquals(1, jc.getCurrentRunningCount());
		break;
	}