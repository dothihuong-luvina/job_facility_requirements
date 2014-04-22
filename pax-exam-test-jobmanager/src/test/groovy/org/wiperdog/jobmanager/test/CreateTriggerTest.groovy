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
public class CreateTriggerTest {

	public CreateTriggerTest() {
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
		mavenBundle("org.quartz-scheduler", "quartz", "2.2.0").startLevel(3),
		mavenBundle("org.wiperdog", "org.wiperdog.jobmanager", "0.2.1").startLevel(3),
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
	 * Create trigger with the parameters: jobName, delay, interval
	 * Expected: create trigger success.
	 */
	@Test
	public void create_trigger_01() throws Exception {
		String jobName = "jobTest";
		// create Trigger with jobName, delay, interval
		def tr = jf.createTrigger(jobName, 0, 200);
		assertNotNull(tr);
		assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	}

	/**
	 * Create trigger with only one parameters: jobName
	 * Expected: create trigger success. Job will be start now.
	 */
	@Test
	public void create_trigger_02() throws Exception {
		String jobName = "jobTest";
		// create Trigger with jobName
		def tr = jf.createTrigger(jobName);
		assertNotNull(tr);
		assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	}

	/**
	 * Create trigger with the parameters: jobName, long
	 * Expected: create trigger success. 
	 */
	@Test
	public void create_trigger_03() throws Exception {
		String jobName = "jobTest";
		// create Trigger with jobName, long
		def tr = jf.createTrigger(jobName, 10);
		assertNotNull(tr);
		assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	}

	/**
	 * Create trigger with the parameters: jobName, Date
	 * Expected: create trigger success.
	 */
	@Test
	public void create_trigger_04() throws Exception {
		Date date = new Date();
		String jobName = "jobTest";
		// create Trigger with jobName, Date
		def tr = jf.createTrigger(jobName, date);
		assertNotNull(tr);
		assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	}

	/**
	 * Create trigger with the parameters: jobName, crontab.
	 * Expected: create trigger success.
	 */
	@Test
	public void create_trigger_05() throws Exception {
		String jobName = "jobTest";
		// create Trigger with jobName, crontab
		def tr = jf.createTrigger(jobName, "0/60 * * * * ?");
		assertNotNull(tr);
		assertEquals("DEFAULT.jobTest", tr.getKey().toString());
	}

	/**
	 * Create trigger with value of jobName is empty.
	 * Expected: create trigger failure.
	 */
	@Test
	public void create_trigger_06() {
		try {
			String jobName = "";
			// create Trigger with jobName, delay, interval
			// value of jobName is empty
			jf.createTrigger(jobName, 0, 200);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Trigger name cannot be null or empty"));
			System.out.println(e);
		}
	}

	/**
	 * Create trigger with value of jobName is null. 
	 * Expected: create trigger failure.
	 */
	@Test
	public void create_trigger_07() {
		try {
			String jobName = null;
			// create Trigger with jobName, delay, interval
			// value of jobName is null
			jf.createTrigger(jobName, 0, 200);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Trigger name cannot be null or empty"));
			System.out.println(e);
		}
	}
}
