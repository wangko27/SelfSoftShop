package com.jxt.service;

import java.util.List;

import com.jxt.domain.SmsInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface SmsInfoService {

	Long createSmsInfo(SmsInfo t);

	int modifySmsInfo(SmsInfo t);

	int removeSmsInfo(SmsInfo t);

	SmsInfo getSmsInfo(SmsInfo t);

	List<SmsInfo> getSmsInfoList(SmsInfo t);

	Long getSmsInfoCount(SmsInfo t);

	List<SmsInfo> getSmsInfoPaginatedList(SmsInfo t);

}