package org.wiperdog.jobmanager.test;

import javax.inject.Inject;
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
import static org.junit.Assert.*;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CreateJobClassTest {
	
	public CreateJobClassTest() {
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
		mavenBundle("org.quartz-scheduler", "quartz", "2.2.1").startLevel(3),
		//mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.1").startLevel(3),		
		mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.3-SNAPSHOT").startLevel(3),
		junitBundles()
		);
	}
	
	@Before
	public void setup() throws Exception {
		jf = context.getService(context.getServiceReference("org.wiperdog.jobmanager.JobFacade"));
		
		// Set parameters for assign func
		jobClassName = "class1";
		concurrency = 2;
		maxruntime = 400;
		maxwaittime = 500;
	}
	
	@After
	public void shutdown() throws Exception {
	}
	
	/**
	 * Create job class with jobClassName doesn't exist.
	 * Expected: create job class success.
	 */
	@Test
	public void create_job_class_01() throws Exception {
		// create job class with jobClassName doesn't exist
		def jc = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		assertEquals(jobClassName, jc.getName());
		assertEquals(concurrency, jc.getConcurrency());
		assertEquals(maxruntime, jc.getMaxRunTime());
		assertEquals(maxwaittime, jc.getMaxWaitTime());
	}

	/**
	 * Create job class with jobClassName already exist. 
	 * Expected: create job class success.
	 */
	@Test
	public void create_job_class_02() throws Exception {
		// create job class jc1 with jobClassName doesn't exit
		def jc1 = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		// create job class jc2 with jobClassName already exist
		def jc2 = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		assertEquals(jc1, jc2);
		assertEquals(jobClassName, jc2.getName());
		assertEquals(concurrency, jc2.getConcurrency());
		assertEquals(maxruntime, jc2.getMaxRunTime());
		assertEquals(maxwaittime, jc2.getMaxWaitTime());
	}

	/**
	 * Create job class with jobClassName is empty.
	 * Expected: create job class failure.
	 */
	@Test
	public void create_job_class_03() {
		jobClassName = "";
		def jc = null;
		try{
			// create job class with jobClassName is empty
			jc = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		} catch (Exception e) {
			assertNull(jc);
			assertTrue(e.getMessage().contains("JobListener name cannot be empty"));
			System.out.println(e);
		}
	}

	/**
	 * Create job class with jobClassName is null 
	 * Expected: create job class failure.
	 */
	@Test
	public void create_job_class_04() {
		jobClassName = null;
		def jc = null;
		try{
			// create job class with jobClassName is null
			jc = jf.createJobClass(jobClassName, concurrency, maxwaittime, maxruntime);
		} catch (Exception e) {
			assertNull(jc);
			assertTrue(e.getMessage().contains("JobListener name cannot be empty"));
			System.out.println(e);
		}
	}
}
