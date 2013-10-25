<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@include file="common.jsp"%>
<%
	String databaseType = (String) session.getAttribute("databaseType");
	String databaseHost = (String) session.getAttribute("databaseHost");
	String databasePort = (String) session.getAttribute("databasePort");
	String databaseUsername = (String) session.getAttribute("databaseUsername");
	String databasePassword = (String) session.getAttribute("databasePassword");
	String databaseName = (String) session.getAttribute("databaseName");
	String tablePrefix = (String) session.getAttribute("tablePrefix");
	String isInitializeDemo = (String) session.getAttribute("isInitializeDemo");
	
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
	} else if (StringUtils.isEmpty(isInitializeDemo)) {
		status = "error";
		message = "初始化DEMO数据参数错误!";
	}
	
	if (status.equals("success")) {
		String jdbcUrl = null;
		File demoSqlFile = null;
		Integer tableNameMaxLength = null;
		
		if (databaseType.equalsIgnoreCase("mysql")) {
			jdbcUrl = "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
			demoSqlFile = new File(realPath + "/install/data/mysql/demo.sql");
			tableNameMaxLength = MYSQL_TABLE_NAME_MAX_LENGTH;
		} else if (databaseType.equalsIgnoreCase("sqlserver")) {
			jdbcUrl = "jdbc:sqlserver://" + databaseHost + ":" + databasePort + ";DatabaseName=" + databaseName;
			demoSqlFile = new File(realPath + "/install/data/sqlserver/demo.sql");
			tableNameMaxLength = SQLSERVER_TABLE_NAME_MAX_LENGTH;
		} else if (databaseType.equalsIgnoreCase("oracle")) {
			jdbcUrl = "jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":" + databaseName;
			demoSqlFile = new File(realPath + "/install/data/oracle/demo.sql");
			tableNameMaxLength = ORACLE_TABLE_NAME_MAX_LENGTH;
		} else {
			status = "error";
			message = "参数错误!";
		}
		
		if (status.equals("success")) {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			
			String currentSQL = null;
			try {
				connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
				statement = connection.createStatement();
				if (demoSqlFile != null) {
					StringBuffer stringBuffer = new StringBuffer();
					List<String> demoSqlLineList = FileUtils.readLines(demoSqlFile, "utf-8");
					for (String line : demoSqlLineList) {
						if (!StringUtils.startsWith(line, "--")) {
							stringBuffer.append(line + "\n");
						}
					}
					
					String demoSql = stringBuffer.toString();
					String shopxxTableRegEx = "\\[" + TABLE_PREFIX_SIGN.replace("{", "\\{").replace("}", "\\}") + "\\w+\\]";
					Pattern shopxxTablePattern = Pattern.compile(shopxxTableRegEx);
					Matcher shopxxTableMatcher = shopxxTablePattern.matcher(demoSql);
					while (shopxxTableMatcher.find()) {
						String tableString = shopxxTableMatcher.group();
						if (tableString.length() - TABLE_PREFIX_SIGN.length() + tablePrefix.length() - 2 > tableNameMaxLength) {
							String newTableString = StringUtils.substring(tableString, 1, tableNameMaxLength + 1 + TABLE_PREFIX_SIGN.length() - tablePrefix.length());
							demoSql = demoSql.replace(tableString, newTableString);
						} else {
							String newTableString = StringUtils.substring(tableString, 1, tableString.length() - 1);
							demoSql = demoSql.replace(tableString, newTableString);
						}
					}
					
					demoSql = demoSql.replace(TABLE_PREFIX_SIGN, tablePrefix);
					if (databaseType.equalsIgnoreCase("mysql")) {
						String mysqlVersion = null;
						try {
							resultSet = statement.executeQuery("select version()");
							resultSet.next();
							mysqlVersion = resultSet.getString(1);
							resultSet.close();
						} catch (SQLException e0) {
							status = "error";
							message = "获取MySQL数据库版本失败!";
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
								message = "获取MySQL数据库版本失败!";
								exception = stackToString(e1);
							}
						}
						if (mysqlVersion.compareTo("5.0") < 0) {
							if (mysqlVersion.compareTo("5.0") < 0) {
								demoSql = demoSql.replace("b'0'", "'0'").replace("b'1'", "'1'");
							}
						}
					}
					String[] initSqlArray = demoSql.split(";\n");
					
					for (String sql : initSqlArray) {
						if (!StringUtils.startsWith(sql, "--")) {
							if (StringUtils.isNotEmpty(sql)) {
								currentSQL = sql;
								statement.executeUpdate(sql);
							}
						}
					}
				} else {
					status = "error";
					message = "DEMO.SQL文件不存在!";
				}
			} catch (SQLException e) {
				status = "error";
				message = "JDBC执行错误!";
				exception = stackToString(e);
				if (currentSQL != null) {
					exception = "SQL: " + currentSQL + "<br />" + exception;
				}
			} catch (IOException e) {
				status = "error";
				message = "DEMO.SQL文件读取失败!";
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
		}
	}
	
	ObjectMapper mapper = new ObjectMapper();
	Map<String, String> jsonMap = new HashMap<String, String>();
	jsonMap.put("status", status);
	jsonMap.put("message", message);
	jsonMap.put("exception", exception);
	mapper.writeValue(response.getWriter(), jsonMap);
%>