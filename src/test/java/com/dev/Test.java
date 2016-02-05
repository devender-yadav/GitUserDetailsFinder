package com.dev;

import java.util.List;

import com.dev.util.FileUtils;
import com.dev.util.UserType;

public class Test {
	public static void main(String[] args) {
		
		List<String> userNameList = GitUsersDetails.getGitUserNames("impetus-opensource", "Kundera", UserType.FORKS);

		List<String> emailIdList = GitUsersDetails.getGitEmailForks(userNameList);

		FileUtils.createFile(emailIdList, "/home/impadmin/forkedUserList_" + System.currentTimeMillis());
	}
}
