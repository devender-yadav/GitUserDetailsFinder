package com.dev;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dev.util.FileUtils;
import com.dev.util.UserType;

public class UserDetailsTest {

	private static final String ORG_NAME = "devender-yadav";

	private static final String REPO_NAME = "GitUserDetails";

	@Test
	public void getForkedUserList() {

		List<String> userNameList = GitUsersDetails.getGitUserNames(ORG_NAME, REPO_NAME, UserType.FORKS);

//		Assert.assertTrue(userNameList.size() > 0);

		List<String> emailIdList = GitUsersDetails.getEmailIds(userNameList);

//		Assert.assertTrue(emailIdList.size() > 0);

		FileUtils.createFile(emailIdList, "/home/impadmin/forkedUserList_" + System.currentTimeMillis());
	}

	@Test
	public void getSubscriberList() {

		List<String> userNameList = GitUsersDetails.getGitUserNames(ORG_NAME, REPO_NAME, UserType.SUBSCRIBERS);

		Assert.assertTrue(userNameList.size() > 0);

		List<String> emailIdList = GitUsersDetails.getEmailIds(userNameList);

		Assert.assertTrue(emailIdList.size() > 0);

		FileUtils.createFile(emailIdList, "/home/impadmin/subscriberList_" + System.currentTimeMillis());
	}

	@Test
	public void getStargazersList() {

		List<String> userNameList = GitUsersDetails.getGitUserNames(ORG_NAME, REPO_NAME, UserType.STARGAZERS);

//		Assert.assertTrue(userNameList.size() > 0);

		List<String> emailIdList = GitUsersDetails.getEmailIds(userNameList);

//		Assert.assertTrue(emailIdList.size() > 0);

		FileUtils.createFile(emailIdList, "/home/impadmin/stargazersList_" + System.currentTimeMillis());
	}
}
