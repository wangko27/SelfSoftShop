package com.jxt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class JXTLogger implements Logger{
	private Logger logger = null;

	public JXTLogger(Class clazz) {
		logger = LoggerFactory.getLogger(clazz);
		// set log configuration file path
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(JXTLogger.class.getResourceAsStream("/config/log/logback.xml"));
		} catch (JoranException e) {
			e.printStackTrace();
		}
	}

	public static JXTLogger getLogger(Class clazz) {
		return new JXTLogger(clazz);
	}

	public void debug(String arg0) {
		logger.debug(arg0);
	}

	public void debug(String arg0, Object arg1) {
		logger.debug(arg0, arg1);

	}

	public void debug(String arg0, Object[] arg1) {
		logger.debug(arg0, arg1);

	}

	public void debug(String arg0, Throwable arg1) {
		logger.debug(arg0, arg1);

	}

	public void error(String arg0) {
		logger.error(arg0);

	}

	public void error(String arg0, Object arg1) {
		logger.error(arg0, arg1);

	}

	public void error(String arg0, Object[] arg1) {
		logger.error(arg0, arg1);

	}

	public void error(String arg0, Throwable arg1) {
		logger.error(arg0, arg1);

	}

	public void info(String arg0) {
		logger.info(arg0);

	}

	public void info(String arg0, Object arg1) {
		logger.info(arg0, arg1);

	}

	public void info(String arg0, Object[] arg1) {
		logger.info(arg0, arg1);

	}

	public void info(String arg0, Throwable arg1) {
		logger.info(arg0, arg1);

	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void trace(String arg0) {
		logger.trace(arg0);

	}

	public void trace(String arg0, Object arg1) {
		logger.trace(arg0, arg1);

	}

	public void trace(String arg0, Object[] arg1) {
		logger.trace(arg0, arg1);

	}

	public void trace(String arg0, Throwable arg1) {
		logger.trace(arg0, arg1);

	}

	public void warn(String arg0) {
		logger.warn(arg0);

	}

	public void warn(String arg0, Object arg1) {
		logger.warn(arg0, arg1);

	}

	public void warn(String arg0, Object[] arg1) {
		logger.warn(arg0, arg1);

	}

	public void warn(String arg0, Throwable arg1) {
		logger.warn(arg0, arg1);

	}

	@Override
	public void debug(Marker arg0, String arg1) {
		logger.debug(arg0, arg1);
		
	}

	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		logger.debug(arg0, arg1,arg2);
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void info(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDebugEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrorEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfoEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTraceEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

}
