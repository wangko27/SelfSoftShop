package com.jxt.service;

import java.util.List;

import com.jxt.domain.City;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface CityService {

	Long createCity(City t);

	int modifyCity(City t);

	int removeCity(City t);

	City getCity(City t);

	List<City> getCityList(City t);

	Long getCityCount(City t);

	List<City> getCityPaginatedList(City t);

}