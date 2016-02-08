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
	public void getForkedUserList() {

		List<String> userNameList = GitUsersDetails.getGitUserNames(ORG_NAME, REPO_NAME, UserType.SUBSCRIBERS);

		Assert.assertTrue(userNameList.size() > 0);

		List<UserDetail> userDetailList = GitUsersDetails.getUserDetails(userNameList);

		Assert.assertTrue(userDetailList.size() > 0);

	}

}
