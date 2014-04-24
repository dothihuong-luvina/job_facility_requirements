package org.wiperdog.jobmanager.test;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.junit.runner.JUnitCore;
import org.osgi.service.cm.ManagedService;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class SearchJobClassForJobTest {
	
	public SearchJobClassForJobTest() {
	}

	String path = System.getProperty("user.dir");
	def jf;
	String jobName;
	
	@Inject
	private org.osgi.framework.BundleContext context;
	
	@Configuration
	public Option[] config() {
		return options(
		cleanCaches(true),
		frameworkStartLevel(6),
		// felix log level
		systemProperty("felix.log.level").value("4"), // 4 = DEBUG
		// setup properties for fileinstall bundle.
		systemProperty("felix.home").value(path),
		// Pax-exam make this test code into OSGi bundle at runtime, so
		// we need "groovy-all" bundle to use this groovy test code.
		mavenBundle("org.codehaus.groovy", "groovy-all", "2.2.1").startLevel(2),
		mavenBundle("commons-collections", "commons-collections", "3.2.1").startLevel(2),
		mavenBundle("commons-beanutils", "commons-beanutils", "1.8.0").startLevel(2),
		mavenBundle("commons-digester", "commons-digester", "2.0").startLevel(2),
		wrappedBundle(mavenBundle("c3p0", "c3p0", "0.9.1.2").startLevel(3)),
		mavenBundle("org.wiperdog", "org.wiperdog.rshell.api", "0.1.0").startLevel(3),
		mavenBundle("org.quartz-scheduler", "quartz", "2.2.1").startLevel(3),
		//mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.1").startLevel(3),		
		mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.3-SNAPSHOT").startLevel(3),
		junitBundles()
		);
	}
	
	@Before
	public void setup() throws Exception {
		jf = context.getService(context.getServiceReference("org.wiperdog.jobmanager.JobFacade"));
	}
	
	@After
	public void shutdown() throws Exception {
	}
	
	/**
	 * Search job class with the job has been assigned to class 
	 * Expected: The array will be contains job class corresponding to the job.
	 */
	@Test
	public void search_job_class_for_job_01() throws Exception {
		// search job class with the job has been assigned to class
		String jcname = "class";
		int concurrency = 1;
		long maxruntime = 20000; 
		long maxwaittime = 20000;
		jobName = "jobTest1";
		jf.createJobClass(jcname, concurrency, maxruntime, maxwaittime);
		jf.assignJobClass(jobName, jcname);
		assertTrue(jf.findJobClassForJob(jobName).length > 0);
	}
	
	/**
	 * Search job class with the job is not assigned to class 
	 * Expected: The array does not contains job class corresponding to the job.
	 */
	@Test
	public void search_job_class_for_job_02() throws Exception {
		// search job class with the job is not assigned to class
		jobName = "jobTest";
		assertTrue(jf.findJobClassForJob(jobName).length == 0);
	}
	
	/**
	 * Search job class with jobName is empty or null.
	 * Expected: The array does not contains job class corresponding to the job.
	 */
	@Test
	public void search_job_class_for_job_03() throws Exception {
		// search job class with jobName is empty
		jobName = "";
		assertTrue(jf.findJobClassForJob(jobName).length == 0);
		// search job class with jobName is null
		jobName = null;
		assertTrue(jf.findJobClassForJob(jobName).length == 0);
	}
}
	
