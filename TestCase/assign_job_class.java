	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_01() {
		String jobClassName = "class1";
		int concurrency = 2;
		long maxruntime = 400;
		long maxwaittime = 500;
		// create job class with jobClassName doesn't exist
		jc1 = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		// assign job class with jobClassName already exists
		jf.assignJobClass("testJob01",jobClassName);
		assertEquals("[DEFAULT.testJob01]", jc1.getAssignedList().toString());
	}

	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_02() {
		String jobClassName = "class1";
		// assign job class with jobClassName doesn't exist
		jf.assignJobClass("testJob01",jobClassName);
		assertTrue(exception.getMessage().contains("no such jobclass(class1) exist"));
	}

	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_03() {
		String jobClassName = "class1";
		int concurrency = 2;
		long maxruntime = 400;
		long maxwaittime = 500;
		// create job class with jobClassName doesn't exist
		jc1 = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		// assign job class with jobName is empty
		jf.assignJobClass("", jobClassName);
		assertTrue(exception.getMessage().contains("Name cannot be empty"));
	}

	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_04() {
		String jobClassName = "class1";
		int concurrency = 2;
		long maxruntime = 400;
		long maxwaittime = 500;
		// create job class with jobClassName doesn't exist
		jc1 = createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		// assign job class with jobName is null
		jf.assignJobClass(null, jobClassName);
		assertTrue(exception.getMessage().contains("Name cannot be null"));
	}

	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_05() {
		String jobClassName = "";
		// assign job class with jobClassName is empty
		jf.assignJobClass("testJob01",jobClassName);
		assertTrue(exception.getMessage().contains("no such jobclass() exist"));
	}

	/**
	 * Check 
	 * Expected: 
	 */
	@Test
	public void assign_job_class_06() {
		String jobClassName = null;
		// assign job class with jobClassName is empty
		jf.assignJobClass("testJob01",jobClassName);
		assertTrue(exception.getMessage().contains("no such jobclass(null) exist"));
	}
