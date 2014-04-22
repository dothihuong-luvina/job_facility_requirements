Test case(or requirement description) of Job facilities.  
========================================================
The list requirement description for new jobmanager:  
1. Create job class with following parameters: jobClassName, concurrency, maxwaittime, maxruntime.  
	- Create job class with jobClassName doesn't exist => Expected: create job class success.  
	- Create job class with jobClassName already exist => Expected: create job class success.  
	- Create job class with jobClassName is empty => Expected: create job class failure.  
	- Create job class with jobClassName is null => Expected: create job class failure.  
2. Assign job to job class  
	- Assign job to job class already exists => Expected: job will be add to list assigned.  
	- Assign job to job class does not exists => Expected: error occur "no such jobclass(className) exist".  
	- Assign job class with jobName is empty => Expected: error occur "Name cannot be empty".  
	- Assign job class with jobName is null => Expected: error occur "Name cannot be null".  
	- Assign job class with jobClassName is empty => Expected: error occur "no such jobclass() exist".  
	- Assign job class with jobClassName is null => Expected: error occur "no such jobclass(null) exist".  
3. Revoke job from job class  
	- Revoke job with jobClassName already exists => Expected: the assigned list will be revoke the job corresponding.  
	- Revoke job with jobClassName doesn't exists => Expected: revoke job failure and error occur "no such jobclass(className) exist".  
	- Revoke job with jobName does not exist => Expected: the assigned list will not change.  
	- Revoke job with jobName is null => Expected: revoke job failure and error occur "Name cannot be null".  
	- Revoke job with jobClassName is empty => Expected: revoke job failure and error occur "no such jobclass() exist".  
	- Revoke job with jobClassName is null => Expected: revoke job failure and error occur "no such jobclass(null) exist".  
4. Delete job class  
	- Delete job class already exists => Expected: delete job class success.  
	- Delete job class does not exists => Expected: delete job class failure.  
	- Delete job class with job class name is empty => Expected: delete job class failure.  
	- Delete job class with job class name is null => Expected: delete job class failure.  
5. Create job  
	- Create job with jobName is not empty => Expected: create job success.  
	- Create job with jobName is empty => Expected: create job failure. Error occur "Job name cannot be empty".  
6. Remove job  
	- Remove the jobdetail already exist => Expected: remove the jobdetail success.  
7. Current Running Count  
	- Get current running count with one job is running. Prevent the concurrent executing job based on pre-configured number of concurrency. => Expected: only one job running and CurrentRunningCount is 1.  
	- Get current running count with two job is running. Prevent the concurrent executing job based on pre-configured number of concurrency. => Expected: two job running and CurrentRunningCount is 2.  
8. Create trigger  
	- Create trigger with the parameters: jobName, delay, interval => Expected: create trigger success.  
	- Create trigger with only one parameters: jobName => Expected: create trigger success.  
	- Create trigger with the parameters: jobName, long => Expected: create trigger success.  
	- Create trigger with the parameters: jobName, Date => Expected: create trigger success.  
	- Create trigger with the parameters: jobName, crontab => Expected: create trigger success.  
	- Create trigger with value of jobName is empty => Expected: create trigger failure.  
	- Create trigger with value of jobName is null => Expected: create trigger failure.  
9. Schedule and re-schedule for job  
	- Create schedule for job with jobDetail and trigger. The schedule does not exist.  
		+ Create trigger with the parameters: jobName, delay, interval  
		+ Create trigger with only one parameters: jobName  
		+ Create trigger with the parameters: jobName, long  
		+ Create trigger with the parameters: jobName, Date  
		+ Create trigger with the parameters: jobName, crontab  
	    => Expected: create schedule success.  
	- Create schedule for job with jobDetail and trigger. The schedule already exist. => Expected: create schedule success.  
	- Create schedule for job with jobDetail is null => Expected: create schedule failure.  
	- Create schedule for job with Trigger is null. => Expected: create schedule failure.  
10. Un-schedule a job  
	- Unschedule with the trigger already exist => Expected: the trigger corresponding to the job will be remove.  
	- Unschedule with the trigger does not exist => Expected: Can not unschedule with the trigger does not exist into schedule.  
	- Unschedule with the trigger is empty => Expected: Can not unschedule with null  
11. Interrupt a job  
	- Interrupt running job => Expected: Interrupt running job success.  
	- Interrupt running job if job run time > maxruntime of job class. => Expected: Interrupt running job success.  
	- Interrupt if job wait time > maxwaittime of job class. => Expected: Waitting job will be given up.  
12. Pause schedule, Resume the schedule  
	- pause and resume the schedule => Expected: pause and resume the schedule success.  
13. Search job in a job class  
	- Search job class with the job has been assigned to class => Expected: The array will be contains job class corresponding to the job.  
	- Search job class with the job is not assigned to class => Expected: The array does not contains job class corresponding to the job.  
	- Search job class with jobName is empty or null => Expected: The array does not contains job class corresponding to the job.  

