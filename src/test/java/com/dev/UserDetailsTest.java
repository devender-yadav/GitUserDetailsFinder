package com.dev;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dev.entity.UserDetail;
import com.dev.util.UserType;

public class UserDetailsTest {

	private static final String ORG_NAME = "devender-yadav";
	private static final String REPO_NAME = "GitUserDetails";

	@Test
	public void getSubscribersList() {

		List<UserDetail> userDetailList = GitUsersDetails.getUserDetails(ORG_NAME, REPO_NAME, UserType.SUBSCRIBERS);
		Assert.assertTrue(userDetailList.size() > 0);
	}

}