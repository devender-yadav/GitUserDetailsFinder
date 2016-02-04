package com.dev;

import java.util.List;

public class Test {
	public static void main(String[] args) {

		List<String> userNameList = GitUsersUtil.getGitUserNameForks("impetus-opensource", "Kundera");

		List<String> emailIdList = GitUsersUtil.getGitEmailForks(userNameList);

		FileUtils.createFile(emailIdList, "/home/impadmin/forkedUserList_" + System.currentTimeMillis());
	}
}
