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
public class AssignJobClassTest {
	
	public AssignJobClassTest() {
	}
	
	String path = System.getProperty("user.dir");
	def jf;
	String jobClassName;
	int concurrency;
	long maxruntime;
	long maxwaittime;
	
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
		mavenBundle("org.quartz-scheduler", "quartz", "2.2.0").startLevel(3),
		mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.1").startLevel(3),
		junitBundles()
		);
	}
	
	@Before
	public void prepare() {
		jf = context.getService(context.getServiceReference("org.wiperdog.jobmanager.JobFacade"));
		
		// Set parameters for assign func
		jobClassName = "class1";
		concurrency = 2;
		maxruntime = 400;
		maxwaittime = 500;
	}

	@After
	public void finish() {
	}

	/**
	 * Assign job to job class already exists.
	 * Expected: job will be add to list assigned.
	 */
	@Test
	public void assign_job_class_01() throws Exception {
		// create job class with jobClassName doesn't exist
		def jc = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		// assign job class with jobClassName already exists
		jf.assignJobClass("testJob01",jobClassName);
		assertEquals("[DEFAULT.testJob01]", jc.getAssignedList().toString());
	}

	/**
	 * Assign job to job class does not exists.
	 * Expected: error occur "no such jobclass(className) exist".
	 */
	@Test
	public void assign_job_class_02() {
		try {
			// assign job class with jobClassName doesn't exist
			jf.assignJobClass("testJob01",jobClassName);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass(class1) exist"));
			System.out.println(e);
		}
	}

	/**
	 * Assign job class with jobName is empty.
	 * Expected: error occur "Name cannot be empty".
	 */
	@Test
	public void assign_job_class_03() {
		try{
			// create job class with jobClassName doesn't exist
			jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
			// assign job class with jobName is empty
			jf.assignJobClass("", jobClassName);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Name cannot be empty"));
			System.out.println(e);
		}
	}

	/**
	 * Assign job class with jobName is null.
	 * Expected: error occur "Name cannot be null".  
	 */
	@Test
	public void assign_job_class_04() {
		try{
			// create job class with jobClassName doesn't exist
			jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
			// assign job class with jobName is null
			jf.assignJobClass(null, jobClassName);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Name cannot be null"));
			System.out.println(e);
		}
	}

	/**
	 * Assign job class with jobClassName is empty.
	 * Expected: error occur "no such jobclass() exist". 
	 */
	@Test
	public void assign_job_class_05() {
		try{
			jobClassName = "";
			// assign job class with jobClassName is empty
			jf.assignJobClass("testJob01",jobClassName);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass() exist"));
			System.out.println(e);
		}
	}

	/**
	 * Assign job class with jobClassName is null.
	 * Expected: error occur "no such jobclass(null) exist". 
	 */
	@Test
	public void assign_job_class_06() {
		try {
			jobClassName = null;
			// assign job class with jobClassName is null
			jf.assignJobClass("testJob01",jobClassName);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass(null) exist"));
			System.out.println(e);
		}
	}
}
