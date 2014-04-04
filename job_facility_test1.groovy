#!/usr/bin/env groovy
// This test script is for describing the requirement.
//  Because this "requirement script" is just a raugh sketch, 
//   this may contains errors.
//

Glue.prepare_env this

// Test 1

	// We need to specify `jobname`, `schedule`, `what to execute`
	add_job("job1", "now", { context -> 
		// contents of job
		// Job contents can be written in any programming language.
		//
		// Job can set result string.
		context.setResult "job is starting."
		println "job is starging"
		Thread.sleep(5000)
		context.setResult "job is finishing."
		println "job is finishing."
	
		return true
	} )
	
	// wait for 10 seconds to finish the job.
	sleep(10000)
	
	// Job engine have to provide the way to access result data
	assert get_job_result("job1") == true
	
	get_job_result_data("job1") == """job is starting.
job is finishing."""

// Test 2

	// job facility have to provide "jobclass" feature.
	//  * jobclass has `name`, `concurrency`, `maxruntime`, `maxwaittime`
	create_jobclass("jobclass1", 1, 1000, 1000)
	
	// jobs can be assigned to a jobclass
	assign_job_to_jobclass("job1", "jobclass1")
	
	create_job("job2", "now", {
		Thread.sleep(5000)
		return true
	} )

	assign_job_to_jobclass("job2", "jobclass1")

	// the job previously created can be 'reschedule'd.
	schedule_job("job1", "now")

	sleep(10000)
	// there are no rule to decide which job should be executed
	//  if the number of schedule exceeded the number of concurrency.
	//   not both jobs have executed
	assert !  get_job_result("job1") && get_job_result("job2")
	//   one of 2 jobs have executed
	assert get_job_result("job1") || get_job_result("job2")

