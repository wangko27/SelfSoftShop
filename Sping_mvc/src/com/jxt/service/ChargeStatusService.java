package com.jxt.service;

import java.util.List;

import com.jxt.domain.ChargeStatus;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface ChargeStatusService {

	Long createChargeStatus(ChargeStatus t);

	int modifyChargeStatus(ChargeStatus t);

	int removeChargeStatus(ChargeStatus t);

	ChargeStatus getChargeStatus(ChargeStatus t);

	List<ChargeStatus> getChargeStatusList(ChargeStatus t);

	Long getChargeStatusCount(ChargeStatus t);

	List<ChargeStatus> getChargeStatusPaginatedList(ChargeStatus t);

}