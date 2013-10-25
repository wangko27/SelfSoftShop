package com.jxt.service;

import java.util.List;

import com.jxt.domain.District;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface DistrictService {

	Long createDistrict(District t);

	int modifyDistrict(District t);

	int removeDistrict(District t);

	District getDistrict(District t);

	List<District> getDistrictList(District t);

	Long getDistrictCount(District t);

	List<District> getDistrictPaginatedList(District t);

}