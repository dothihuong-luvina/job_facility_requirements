// This is a glue code which bond 
//   the 'requirement script' with module implementations.
//

static def prepare_env(tester) {
	tester.metaClass.sleep = 
		{
			millisec 
				-> 
			println "sleeping " + millisec + "ms"
			Thread.sleep(millisec) 
		}
	tester.metaClass.add_job = 
		{
			name, 
			schedule, 
			what
				->
			
		}
	tester.metaClass.get_job_result = 
		{
			name
				-> 
		}
	tester.metaClass.get_job_result_data = 
		{
			name
				-> 
		}
	tester.create_jobclass = 
		{
			name, 
			concurrency, 
			maxruntime, 
			maxwaittime
				-> 
		}
	tester.assign_job_to_jobclass = 
		{
			job, 
			jobclass
				->
		}
	tester.schedule_job = 
		{
			job, 
			schedule
				->
		}
}

def add_job(name, sched, what) {
}

