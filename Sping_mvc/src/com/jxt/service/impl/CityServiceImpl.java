package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.CityDao;
import com.jxt.domain.City;
import com.jxt.service.CityService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class CityServiceImpl implements CityService {
	private CityDao cityDao;
	

	public Long createCity(City t) {
		return this.cityDao.insertEntity(t);
	}

	public City getCity(City t) {
		return this.cityDao.selectEntity(t);
	}

	public Long getCityCount(City t) {
		return this.cityDao.selectEntityCount(t);
	}

	public List<City> getCityList(City t) {
		return this.cityDao.selectEntityList(t);
	}

	public int modifyCity(City t) {
		return this.cityDao.updateEntity(t);
	}

	public int removeCity(City t) {
		return this.cityDao.deleteEntity(t);
	}

	public List<City> getCityPaginatedList(City t) {
		return this.cityDao.selectEntityPaginatedList(t);
	}

}
