package com.dev;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GitUsersUtil {

	private static final String HTTP_HEADER = "https://";
	private static final String GITHUB_API_NAME = "api.github.com";
	private static final String REPOS = "repos";
	private static final String FORKS = "forks";

	public static List<String> getGitUserNameForks(String orgName, String repoName) {

		List<String> userNameList = new ArrayList<>();
		String forkUrl = getForkUrl(orgName, repoName);

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

			if (email != null  && !email.toString().equals("null")) {
				String emailId = (String) email;
				System.out.println(emailId);
				emailList.add(emailId);
			}

		}

		System.out.println("Email Ids of " + emailList.size() + " people who forked your REPO available.");

		return emailList;

	}

	private static String getForkUrl(String orgName, String repoName) {
		StringBuilder builder = new StringBuilder();
		builder.append(HTTP_HEADER);
		builder.append(GITHUB_API_NAME);
		builder.append("/");
		builder.append(REPOS);
		if (orgName != null) {
			builder.append("/" + orgName);
		} else {
			System.err.println("ORG Name can't be null");
		}

		if (repoName != null) {
			builder.append("/" + repoName);
		} else {
			System.err.println("REPO Name name can't be null");
		}

		builder.append("/" + FORKS);

		String url = builder.toString();

		System.out.println(url);
		return url;
	}

}
