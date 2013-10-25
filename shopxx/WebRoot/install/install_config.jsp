<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Node"%>
<%@page import="org.dom4j.io.OutputFormat"%>
<%@page import="org.dom4j.io.XMLWriter"%>
<%@page import="java.sql.*"%>
<%@include file="common.jsp"%>
<%
	String databaseType = (String) session.getAttribute("databaseType");
	String databaseHost = (String) session.getAttribute("databaseHost");
	String databasePort = (String) session.getAttribute("databasePort");
	String databaseUsername = (String) session.getAttribute("databaseUsername");
	String databasePassword = (String) session.getAttribute("databasePassword");
	String databaseName = (String) session.getAttribute("databaseName");
	String tablePrefix = (String) session.getAttribute("tablePrefix");
	
	String status = "success";
	String message = "";
	String exception = "";
	
	if (StringUtils.isEmpty(databaseType)) {
		status = "error";
		message = "数据库类型不允许为空!";
	} else if (StringUtils.isEmpty(databaseHost)) {
		status = "error";
		message = "数据库主机不允许为空!";
	} else if (StringUtils.isEmpty(databasePort)) {
		status = "error";
		message = "数据库端口不允许为空!";
	} else if (StringUtils.isEmpty(databaseUsername)) {
		status = "error";
		message = "数据库用户名不允许为空!";
	} else if (StringUtils.isEmpty(databaseName)) {
		status = "error";
		message = "数据库名称不允许为空!";
	} else if (StringUtils.isEmpty(tablePrefix)) {
		status = "error";
		message = "数据库表前缀不允许为空!";
	}
	
	String databasePropertiesDescription = "SHOP++ DATABASE CONFIG";
	
	if (status.equals("success")) {
		String jdbcDriver = null;
		String jdbcUrl = null;
		String hibernateDialect = null;
		Integer tableNameMaxLength = null;
		
		if (databaseType.equalsIgnoreCase("mysql")) {
			jdbcDriver = "com.mysql.jdbc.Driver";
			jdbcUrl = "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
			hibernateDialect = "org.hibernate.dialect.MySQLDialect";
			tableNameMaxLength = MYSQL_TABLE_NAME_MAX_LENGTH;
		} else if (databaseType.equalsIgnoreCase("sqlserver")) {
			jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			jdbcUrl = "jdbc:sqlserver://" + databaseHost + ":" + databasePort + ";databasename=" + databaseName;
			tableNameMaxLength = SQLSERVER_TABLE_NAME_MAX_LENGTH;
			
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
				statement = connection.createStatement();
				String sqlServerVersion = null;
				try {
					resultSet = statement.executeQuery("select cast(serverproperty('productversion') as varchar)");
					resultSet.next();
					sqlServerVersion = resultSet.getString(1);
					resultSet.close();
				} catch (SQLException e0) {
					status = "error";
					message = "获取SQL Server数据库版本失败!";
					exception = stackToString(e0);
					try {
						if(resultSet != null) {
							resultSet.close();
							resultSet = null;
						}
						if(statement != null) {
							statement.close();
							statement = null;
						}
						if(connection != null) {
							connection.close();
							connection = null;
						}
					} catch (SQLException e1) {
						status = "error";
						message = "获取SQL Server数据库版本失败!";
						exception = stackToString(e1);
					}
				}
				
				if (status.equals("success")) {
					if (StringUtils.startsWith(sqlServerVersion, "8.")) {
						hibernateDialect = "org.hibernate.dialect.SQLServerDialect";
					} else if (StringUtils.startsWith(sqlServerVersion, "9.")) {
						hibernateDialect = "org.hibernate.dialect.SQLServer2005Dialect";
					} else if (StringUtils.startsWith(sqlServerVersion, "10.")) {
						hibernateDialect = "org.hibernate.dialect.SQLServer2008Dialect";
					} else {
						hibernateDialect = "org.hibernate.dialect.SQLServerDialect";
					}
				}
			} catch (SQLException e) {
				status = "error";
				message = "JDBC执行错误!";
				exception = stackToString(e);
			} finally {
				try {
					if(resultSet != null) {
						resultSet.close();
						resultSet = null;
					}
					if(statement != null) {
						statement.close();
						statement = null;
					}
					if(connection != null) {
						connection.close();
						connection = null;
					}
				} catch (SQLException e) {
					status = "error";
					message = "JDBC执行错误!";
					exception = stackToString(e);
				}
			}
		} else if (databaseType.equalsIgnoreCase("oracle")) {
			jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			jdbcUrl = "jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":" + databaseName;
			hibernateDialect = "org.hibernate.dialect.OracleDialect";
			tableNameMaxLength = ORACLE_TABLE_NAME_MAX_LENGTH;
		} else {
			status = "error";
			message = "参数错误!";
		}
		
		if (status.equals("success")) {
			try {
				File databasePropertiesFile = new ClassPathResource(DATABASE_PROPERTIES_PATH).getFile();
				
				Properties properties = new Properties();
				properties.put("jdbc.driver", jdbcDriver);
				properties.put("jdbc.url", jdbcUrl);
				properties.put("jdbc.username", databaseUsername);
				properties.put("jdbc.password", databasePassword);
				properties.put("hibernate.dialect", hibernateDialect);
				properties.put("hibernate.show_sql", "false");
				properties.put("hibernate.format_sql", "false");
				properties.put("hibernate.hbm2ddl.auto", "update");
				properties.put("hibernate.cache.use_second_level_cache", "false");
				properties.put("hibernate.cache.provider_class", "org.hibernate.cache.OSCacheProvider");
				properties.put("hibernate.cache.use_query_cache", "false");
				properties.put("hibernate.jdbc.fetch_size", "50");
				properties.put("hibernate.jdbc.batch_size", "30");
				properties.put("pool.initialPoolSize", "10");
				properties.put("pool.minPoolSize", "10");
				properties.put("pool.maxPoolSize", "50");
				properties.put("pool.maxIdleTime", "7200");
				properties.put("pool.acquireIncrement", "5");
				properties.put("pool.checkoutTimeout", "10000");
				properties.put("pool.maxIdleTimeExcessConnections", "10");
				properties.put("namingStrategy.tablePrefix", tablePrefix);
				properties.put("namingStrategy.isAddUnderscores", "true");
				properties.put("namingStrategy.maxLength", tableNameMaxLength.toString());
		        OutputStream outputStream = new FileOutputStream(databasePropertiesFile);
		        properties.store(outputStream, databasePropertiesDescription);
		        outputStream.close();
			} catch (IOException e) {
				status = "error";
				message = "DATABASE.PROPERTIES文件写入失败!";
				exception = stackToString(e);
			}
		}
	}
	
	if (status.equals("success")) {
		try {
			File backupWebConfigFile = new File(realPath + BACKUP_WEB_XML_PATH);
			File webConfigFile = new File(realPath + WEB_XML_PATH);
			FileUtils.copyFile(backupWebConfigFile, webConfigFile);
		} catch (IOException e) {
			status = "error";
			message = "BACKUP_WEB.XML文件读取失败!";
			exception = stackToString(e);
		}
	}
	
	if (status.equals("success")) {
		try {
			File shopxxXmlFile = new ClassPathResource(SHOPXX_XML_PATH).getFile();
			
			SAXReader shopxxXmlSaxReader = new SAXReader();
			Document shopxxXmlDocument = shopxxXmlSaxReader.read(shopxxXmlFile);
			
			Node shopxxXmlNode = shopxxXmlDocument.selectSingleNode("/shopxx/setting/contextPath");
			shopxxXmlNode.setText(request.getContextPath());
			
			OutputFormat shopxxXmlOutputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			shopxxXmlOutputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			shopxxXmlOutputFormat.setIndent(true);// 设置是否缩进
			shopxxXmlOutputFormat.setIndent("	");// 以TAB方式实现缩进
			shopxxXmlOutputFormat.setNewlines(true);// 设置是否换行
			XMLWriter shopxxXmlXMLWriter = new XMLWriter(new FileOutputStream(shopxxXmlFile), shopxxXmlOutputFormat);
			shopxxXmlXMLWriter.write(shopxxXmlDocument);
			shopxxXmlXMLWriter.close();
		} catch (IOException e) {
			status = "error";
			message = "SHOPXX_XML文件写入失败!";
			exception = stackToString(e);
		}
	}
	
	if (status.equals("success")) {
		try {
			FileUtils.writeStringToFile(new File(realPath + BUILD_HTML_CONFIG_PATH), "SHOP++ BUILD HTML - SHOPXX.NET");
		} catch (IOException e) {
			status = "error";
			message = "BUILD_HTML_CONFIG_PATH文件写入失败!";
			exception = stackToString(e);
		}
	}
	
	if (status.equals("success")) {
		try {
			FileUtils.writeStringToFile(new File(realPath + INSTALL_LOCK_CONFIG_PATH), "SHOP++ INSTALL LOCK - SHOPXX.NET");
		} catch (IOException e) {
			status = "error";
			message = "INSTALL_LOCK_CONFIG文件写入失败!";
			exception = stackToString(e);
		}
	}
	
	ObjectMapper mapper = new ObjectMapper();
	Map<String, String> jsonMap = new HashMap<String, String>();
	jsonMap.put("status", status);
	jsonMap.put("message", message);
	jsonMap.put("exception", exception);
	mapper.writeValue(response.getWriter(), jsonMap);
%>