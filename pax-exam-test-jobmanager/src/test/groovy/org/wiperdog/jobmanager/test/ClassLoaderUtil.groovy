package org.wiperdog.jobmanager.test

import org.codehaus.groovy.tools.RootLoader;

public class ClassLoaderUtil {
	
	/**
	 * load class
	 * @param path path to class need to load
	 * @return class
	 */
	public Class getCls(String path) {
		//ClassLoader parent = Thread.currentThread().getContextClassLoader();
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class jobExecutableCls = loader.parseClass(new File(path));
		return jobExecutableCls;
	}
}
