package com.jxt.service;

import java.util.List;

import com.jxt.domain.ChargeHistory;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface ChargeHistoryService {

	Long createChargeHistory(ChargeHistory t);

	int modifyChargeHistory(ChargeHistory t);

	int removeChargeHistory(ChargeHistory t);

	ChargeHistory getChargeHistory(ChargeHistory t);

	List<ChargeHistory> getChargeHistoryList(ChargeHistory t);

	Long getChargeHistoryCount(ChargeHistory t);

	List<ChargeHistory> getChargeHistoryPaginatedList(ChargeHistory t);

}