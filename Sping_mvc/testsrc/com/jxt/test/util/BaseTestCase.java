package com.jxt.test.util;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseTestCase extends TestCase {

	private static ApplicationContext appContext;
	TransactionStatus status;
	DataSourceTransactionManager transactionManager;

	public static ApplicationContext getApplicationContext() {
		if (appContext == null) {
			try {
                String[] configLocations = { "classpath:config/spring/biz_root.xml", 
                        "classpath:config/spring/web_root.xml"
                      };
				appContext = new ClassPathXmlApplicationContext(configLocations);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return appContext;
	}

	public static Object getBean(String beanName) throws Exception {
		try {
			return getApplicationContext().getBean(beanName);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * set rollback point
	 * 
	 * @throws Exception
	 */
	public void onSetUpInTransaction() throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transactionManager = (DataSourceTransactionManager) getBean("ProvisionTransactionManager");
		status = transactionManager.getTransaction(def);
	}

	/**
	 * rollback
	 * 
	 */
	public void onTearDownInTransaction() {
		transactionManager.rollback(status);
	}
}
