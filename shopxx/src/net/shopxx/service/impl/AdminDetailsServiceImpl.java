package net.shopxx.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.dao.AdminDao;
import net.shopxx.entity.Admin;
import net.shopxx.entity.Role;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 管理权限认证
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB3694CBA6550E55995F7798B7B95AE5B
 * ============================================================================
 */

@Service("adminDetailsServiceImpl")
public class AdminDetailsServiceImpl implements UserDetailsService {

	private static final long serialVersionUID = 2653636739190406891L;

	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;

	@Transactional
	public Admin loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		Admin admin = adminDao.getAdminByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
		}
		
		// 解除管理员账户锁定
		Setting setting = SettingUtil.getSetting();
		if (admin.getIsAccountLocked() == true) {
			if (setting.getIsLoginFailureLock() == true) {
				int loginFailureLockTime = setting.getLoginFailureLockTime();
				if (loginFailureLockTime != 0) {
					Date lockedDate = admin.getLockedDate();
					Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					Date now = new Date();
					if (now.after(nonLockedTime)) {
						admin.setLoginFailureCount(0);
						admin.setIsAccountLocked(false);
						admin.setLockedDate(null);
						adminDao.update(admin);
					}
				}
			} else {
				admin.setLoginFailureCount(0);
				admin.setIsAccountLocked(false);
				admin.setLockedDate(null);
				adminDao.update(admin);
			}
		}
		admin.setAuthorities(getGrantedAuthorities(admin));
		return admin;
	}

	private GrantedAuthority[] getGrantedAuthorities(Admin admin) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (Role role : admin.getRoleSet()) {
			for (String authority : role.getAuthorityList()) {
				grantedAuthorities.add(new GrantedAuthorityImpl(authority));
			}
		}
		return grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]);
	}

}