package com.dev;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dev.entity.UserDetail;
import com.dev.util.Constants;
import com.dev.util.ObjectUtil;
import com.dev.util.RestUtil;
import com.dev.util.UserType;
import com.google.gson.Gson;

public class GitUsersDetails {

	public static List<String> getGitUserNames(String orgName, String repoName, UserType userType, String userName,
			String password) {

		List<String> gitUserNameUrlList = new ArrayList<>();
		String url = getRepoUrl(orgName, repoName) + Constants.FORWARD_SALSH + userType.toString().toLowerCase();
		String resp = "";
		int pageNum = 1;
		while (!resp.trim().equals("[]")) {
			String modifiedUrl = url + "?page=" + pageNum + "&per_page=100";
			System.out.println(modifiedUrl);

			if (userName == null && password == null) {
				resp = RestUtil.makeGETRequest(modifiedUrl);
			} else {
				resp = RestUtil.makeGETRequest(modifiedUrl, userName, password);
			}

			String userNameUrl = null;
			JSONArray jArray = new JSONArray(resp);

			for (int j = 0; j < jArray.length(); j++) {
				JSONObject jObject = jArray.getJSONObject(j);

				if (userType.equals(UserType.FORKS)) {
					userNameUrl = (String) jObject.getJSONObject("owner").get("url");

				} else {
					userNameUrl = (String) jObject.get("url");
				}

				System.out.println(userNameUrl);
				gitUserNameUrlList.add(userNameUrl);
			}
			pageNum++;
		}

		System.out.println("Total number of " + userType + " - " + gitUserNameUrlList.size());
		return gitUserNameUrlList;
	}

	public static List<String> getGitUserNames(String orgName, String repoName, UserType userType) {

		return getGitUserNames(orgName, repoName, userType, null, null);

	}

	public static List<UserDetail> getUserDetails(List<String> userNameUrlList) {

		return getUserDetails(userNameUrlList, null, null);

	}

	public static List<UserDetail> getUserDetails(List<String> userNameUrlList, String userName, String password) {

		List<UserDetail> userDetailList = new ArrayList<>();

		for (String userNameUrl : userNameUrlList) {

			String resp = "";

			if (userName == null && password == null) {
				resp = RestUtil.makeGETRequest(userNameUrl);
			} else {
				resp = RestUtil.makeGETRequest(userNameUrl, userName, password);
			}
			JSONObject jObject = new JSONObject(resp);
			Gson gson = new Gson();
			UserDetail userDetail = gson.fromJson(jObject.toString(), UserDetail.class);
			if (ObjectUtil.checkNullAndEmpty(userDetail)) {
				System.out.println(userDetail.toString());
				userDetailList.add(userDetail);
			}
		}
		return userDetailList;
	}

	private static String getRepoUrl(String orgName, String repoName) {
		StringBuilder builder = new StringBuilder();
		builder.append(Constants.HTTP_HEADER);
		builder.append(Constants.GITHUB_API_NAME);
		builder.append(Constants.FORWARD_SALSH);
		builder.append(Constants.REPOS);
		if (orgName != null) {
			builder.append(Constants.FORWARD_SALSH + orgName);
		} else {
			System.err.println("ORG Name can't be null");
		}

		if (repoName != null) {
			builder.append(Constants.FORWARD_SALSH + repoName);
		} else {
			System.err.println("REPO Name name can't be null");
		}

		String url = builder.toString();

		return url;
	}

}
