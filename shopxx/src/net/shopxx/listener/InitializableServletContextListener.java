package net.shopxx.listener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializableServletContextListener implements ServletContextListener {
	
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			char[] a = {111, 114, 103, 46, 97, 112, 97, 99, 104, 101, 46, 99, 111, 109, 109, 111, 110, 115, 46, 105, 111, 46, 70, 105, 108, 101, 85, 116, 105, 108, 115};
			char[] b = {114, 101, 97, 100, 76, 105, 110, 101, 115};
			char[] c = {47, 115, 104, 111, 112, 120, 120, 46, 116, 120, 116};
			Method readLinesMethod = Class.forName(new String(a)).getMethod(new String(b), File.class);
			List<String> lineList = (List<String>) readLinesMethod.invoke(null, new File(servletContextEvent.getServletContext().getRealPath(new String(c))));
			for (String line : lineList) {
				System.out.println(line);
			}
		} catch (Exception e) {}
		
		try {
			char[] a = {106, 97, 118, 97, 120, 46, 115, 101, 114, 118, 108, 101, 116, 46, 83, 101, 114, 118, 108, 101, 116, 67, 111, 110, 116, 101, 120, 116};
			char[] b = {115, 101, 116, 65, 116, 116, 114, 105, 98, 117, 116, 101};
			char[] c = {115, 104, 111, 112, 120, 120, 46, 112, 111, 119, 101, 114, 101, 100};
			char[] d = {83, 72, 79, 80, 43, 43};
			Method setAttributeMethod = Class.forName(new String(a)).getMethod(new String(b), String.class, String.class);
			setAttributeMethod.invoke(servletContextEvent.getServletContext(), new String(c), new String(d));
		} catch (Exception e) {
			
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}