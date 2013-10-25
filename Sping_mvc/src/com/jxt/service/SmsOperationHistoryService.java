package com.jxt.service;

import java.util.List;

import com.jxt.domain.SmsOperationHistory;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface SmsOperationHistoryService {

	Long createSmsOperationHistory(SmsOperationHistory t);

	int modifySmsOperationHistory(SmsOperationHistory t);

	int removeSmsOperationHistory(SmsOperationHistory t);

	SmsOperationHistory getSmsOperationHistory(SmsOperationHistory t);

	List<SmsOperationHistory> getSmsOperationHistoryList(SmsOperationHistory t);

	Long getSmsOperationHistoryCount(SmsOperationHistory t);

	List<SmsOperationHistory> getSmsOperationHistoryPaginatedList(SmsOperationHistory t);

}