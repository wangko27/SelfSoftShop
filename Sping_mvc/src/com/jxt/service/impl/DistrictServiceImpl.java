package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.DistrictDao;
import com.jxt.domain.District;
import com.jxt.service.DistrictService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class DistrictServiceImpl implements DistrictService {
	private DistrictDao districtDao;
	

	public Long createDistrict(District t) {
		return this.districtDao.insertEntity(t);
	}

	public District getDistrict(District t) {
		return this.districtDao.selectEntity(t);
	}

	public Long getDistrictCount(District t) {
		return this.districtDao.selectEntityCount(t);
	}

	public List<District> getDistrictList(District t) {
		return this.districtDao.selectEntityList(t);
	}

	public int modifyDistrict(District t) {
		return this.districtDao.updateEntity(t);
	}

	public int removeDistrict(District t) {
		return this.districtDao.deleteEntity(t);
	}

	public List<District> getDistrictPaginatedList(District t) {
		return this.districtDao.selectEntityPaginatedList(t);
	}

}
