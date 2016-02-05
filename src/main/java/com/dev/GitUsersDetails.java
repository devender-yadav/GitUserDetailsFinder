package com.dev;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dev.util.Constants;
import com.dev.util.RestUtil;
import com.dev.util.UserType;

public class GitUsersDetails {

	public static List<String> getGitUserNames(String orgName, String repoName, UserType userType) {

		List<String> userNameList = new ArrayList<>();
		String url = getRepoUrl(orgName, repoName) + Constants.FORWARD_SALSH + userType;
		String resp = "";
		int pageNum = 1;
		while (!resp.trim().equals("[]")) {

			String modifiedUrl = url + "?page=" + pageNum + "&per_page=100";

			System.out.println("##################################################################################");
			System.out.println(modifiedUrl);
			System.out.println("##################################################################################");
			resp = RestUtil.makeGETRequest(modifiedUrl);

			String usernameUrl = null;

			JSONArray array = new JSONArray(resp);

			for (int j = 0; j < array.length(); j++) {
				JSONObject jObject = array.getJSONObject(j);

				if (userType.equals(UserType.FORKS)) {
					usernameUrl = (String) jObject.getJSONObject("owner").get("url");
				}

				else {
					usernameUrl = (String) jObject.get("url");
				}

				System.out.println(usernameUrl);
				userNameList.add(usernameUrl);
			}
			pageNum++;
		}

		System.out.println("Total number of forks - " + userNameList.size());
		return userNameList;
	}

	public static List<String> getGitUserNameForks(String orgName, String repoName) {

		List<String> userNameList = new ArrayList<>();
		String forkUrl = getRepoUrl(orgName, repoName) + Constants.FORWARD_SALSH + Constants.FORKS;
		String resp = "";
		int pageNum = 1;
		while (!resp.trim().equals("[]")) {

			String modifiedUrl = forkUrl + "?page=" + pageNum + "&per_page=100";

			System.out.println("##################################################################################");
			System.out.println(modifiedUrl);
			System.out.println("##################################################################################");
			resp = RestUtil.makeGETRequest(modifiedUrl);

			JSONArray array = new JSONArray(resp);

			for (int j = 0; j < array.length(); j++) {
				JSONObject jObject = array.getJSONObject(j);
				String url = (String) jObject.getJSONObject("owner").get("url");
				System.out.println(url);
				userNameList.add(url);
			}
			pageNum++;
		}

		System.out.println("Total number of forks - " + userNameList.size());
		return userNameList;
	}

	public static List<String> getGitEmailForks(List<String> userNameList) {

		List<String> emailList = new ArrayList<>();

		for (String userName : userNameList) {

			String resp = RestUtil.makeGETRequest(userName);

			JSONObject jObject = new JSONObject(resp);

			Object email = jObject.get("email");

			if (email != null && !email.toString().equals("null")) {
				String emailId = (String) email;
				System.out.println(emailId);
				emailList.add(emailId);
			}
		}

		System.out.println("Email Ids of " + emailList.size() + " people who forked your REPO are available.");

		return emailList;

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

		System.out.println(url);
		return url;
	}

}
