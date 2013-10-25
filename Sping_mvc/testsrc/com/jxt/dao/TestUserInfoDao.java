package com.jxt.dao;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.jxt.domain.UserInfo;
import com.jxt.test.util.BaseTestCase;


public class TestUserInfoDao extends BaseTestCase{
	static SqlSessionFactory sqlSessionFactory = null;
	static {
		try {
			sqlSessionFactory = (SqlSessionFactory) getBean("sqlSessionFactory");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	  @Before 
	  public void before() throws Exception {  
	       //sqlSessionFactory = (SqlSessionFactory) getBean("sqlSessionFactory");  
	  }  
	
	   @Test  
       public void testFind() {  
              SqlSession sqlSession = sqlSessionFactory.openSession();  
              try {  
            	     UserInfoDao userInfoDao = sqlSession.getMapper(UserInfoDao.class);
            	     UserInfo userInfo = new UserInfo();
            	     userInfo.setId(Integer.valueOf("1"));
            	      userInfo = userInfoDao.selectEntity(userInfo);
                     System.out.println(userInfo.getId() + "--" + userInfo.getName());  
              } finally {  
                     sqlSession.close();  
              }  
       } 
}
