package com.dev;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dev.entity.UserDetail;
import com.dev.util.FileUtils;
import com.dev.util.UserType;

public class UserDetailsTest {

	private static final String ORG_NAME = "devender-yadav";
	private static final String REPO_NAME = "GitUserDetails";

	@Test
	public void getSubscribersList() {

		List<UserDetail> userDetailList = GitUsersDetails.getUserDetails(ORG_NAME, REPO_NAME, UserType.SUBSCRIBERS);
		Assert.assertTrue(userDetailList.size() > 0);
		
		List<String> userDetails = new ArrayList<>();
		for( UserDetail u : userDetailList){
			userDetails.add(u.toString());
		}
		
//		FileUtils.createFile(userDetails, "/home/impadmin/subscribers"+System.currentTimeMillis()+".csv");
	}

}