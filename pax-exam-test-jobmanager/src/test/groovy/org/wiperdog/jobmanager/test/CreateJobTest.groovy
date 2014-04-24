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
public class CreateJobTest {
	
	public CreateJobTest() {
	}
	
	public static final String PATH_TO_CLASS = "src/resource/JobExecutableImpl.groovy";
	String path = System.getProperty("user.dir");
	def jf;
	Class jobExecutableCls;
	
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
		
		// load class
		ClassLoaderUtil lc = new ClassLoaderUtil();
		jobExecutableCls = lc.getCls(PATH_TO_CLASS);
	}
	
	@After
	public void shutdown() throws Exception {
	}
	
	/**
	 * Create job with jobName is not empty.
	 * Expected: create job success.
	 */
	@Test
	public void create_job_01() throws Exception {
		String jobName = "/jobTest.job";
		// create job with jobName is "jobTest.job"
		def executable = jobExecutableCls.newInstance(path + jobName, "class", "sender");
		def jd = jf.createJob(executable);
		assertNotNull(jd);
		assertEquals("DEFAULT.jobTest.job", jd.getKey().toString());
		assertEquals("class org.wiperdog.jobmanager.internal.ObjectJob", jd.getJobClass().toString());
	}

	/**
	 * Create job with jobName is empty.
	 * Expected: create job failure. Error occur "Job name cannot be empty".
	 */
	@Test
	public void create_job_02() {
		try {
			String jobName = "/ ";
			// create job with jobName is empty
			def executable = jobExecutableCls.newInstance(path + jobName, "class", "sender");
			jf.createJob(executable);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Job name cannot be empty"));
			System.out.println(e);
		}
	}
}
