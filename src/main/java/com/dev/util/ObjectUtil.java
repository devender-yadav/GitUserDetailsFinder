package com.dev.util;

import com.dev.entity.UserDetail;

public class ObjectUtil {

	public static boolean checkNullAndEmpty(UserDetail userDetail) {

		return (userDetail.getCompany() != null && !userDetail.getCompany().equals("null"))
				|| (userDetail.getEmail() != null && !userDetail.getEmail().equals("null"))
				|| (userDetail.getLocation() != null && !userDetail.getLocation().equals("null"))
				|| (userDetail.getName() != null && !userDetail.getName().equals("null"));
	}

}
