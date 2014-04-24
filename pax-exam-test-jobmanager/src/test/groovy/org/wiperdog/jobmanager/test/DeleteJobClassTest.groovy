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
public class DeleteJobClassTest {
	
	public DeleteJobClassTest() {
	}
	
	String path = System.getProperty("user.dir");
	def jf;
	
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
	 * Delete job class already exists.
	 * Expected: delete job class success. 
	 */
	@Test
	public void delete_job_class_01() {
		try {
			String jcname = "class";
			int concurrency = 2;
			long maxruntime = 400;
			long maxwaittime = 500;
			// create job class with jobClassName doesn't exist
			def jc = jf.createJobClass(jcname, concurrency, maxwaittime, maxruntime);
			jf.assignJobClass("test01", jcname);
			assertEquals("[DEFAULT.test01]", jc.getAssignedList().toString());
			// delete job class class already exists
			jf.deleteJobClass(jcname);
			jf.assignJobClass("test02", jcname);
		} catch (Exception e) {
			System.out.println(e);
			assertTrue(e.getMessage().contains("no such jobclass(class)"));
			System.out.println(e);
		}
	}

	/**
	 * Delete job class does not exists. 
	 * Expected: delete job class failure.
	 */
	@Test
	public void delete_job_class_02() {
		try {
			// delete job class does not exist
			jf.deleteJobClass("classTest");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass(classTest)"));
			System.out.println(e);
		}
	}
	
	/**
	 * Delete job class with job class name is empty. 
	 * Expected: delete job class failure.
	 */
	@Test
	public void delete_job_class_03() {
		try {
			String jcname = "";
			// delete job class with job class name is empty
			jf.deleteJobClass(jcname);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass()"));
			System.out.println(e);
		}
	}
	
	/**
	 * Delete job class with job class name is null 
	 * Expected: delete job class failure.
	 */
	@Test
	public void delete_job_class_04() {
		try {
			String jcname = null;
			// delete job class with job class name is null
			jf.deleteJobClass(jcname);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("no such jobclass(null)"));
			System.out.println(e);
		}
	}
}
	
