package com.dev;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dev.entity.UserDetail;
import com.dev.util.Constants;
import com.dev.util.RestUtil;
import com.dev.util.UserType;

public class GitUsersDetails {

	public static List<String> getGitUserNames(String orgName, String repoName, UserType userType) {

		List<String> userNameList = new ArrayList<>();
		String url = getRepoUrl(orgName, repoName) + Constants.FORWARD_SALSH + userType.toString().toLowerCase();
		String resp = "";
		int pageNum = 1;
		while (!resp.trim().equals("[]")) {
			String modifiedUrl = url + "?page=" + pageNum + "&per_page=100";
			System.out.println(modifiedUrl);
			resp = RestUtil.makeGETRequest(modifiedUrl);

			String usernameUrl = null;
			JSONArray array = new JSONArray(resp);

			for (int j = 0; j < array.length(); j++) {
				JSONObject jObject = array.getJSONObject(j);

				if (userType.equals(UserType.FORKS)) {
					usernameUrl = (String) jObject.getJSONObject("owner").get("url");

				} else {
					usernameUrl = (String) jObject.get("url");
				}

				System.out.println(usernameUrl);
				userNameList.add(usernameUrl);
			}
			pageNum++;
		}

		System.out.println("Total number of " + userType + " - " + userNameList.size());
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

	public static List<String> getEmailIds(List<String> userNameList) {

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

		System.out.println("Email Ids of " + emailList.size() + " people are available.");
		return emailList;
	}

	public static List<UserDetail> getUserDetails(List<String> userNameList) {

		List<UserDetail> userDetailList = new ArrayList<>();

		for (String userName : userNameList) {
			
			UserDetail userDetail = new UserDetail();
			String resp = RestUtil.makeGETRequest(userName);
			JSONObject jObject = new JSONObject(resp);

			String name = (String) jObject.get("name");
			String company = (String) jObject.get("company");
			String location = (String) jObject.get("location");
			String email = (String) jObject.get("email");

			if (!name.equals("null")) {
				userDetail.setName(name);
			}
			
			if (!company.equals("null")) {
				userDetail.setName(company);
			}
			
			if (!location.equals("null")) {
				userDetail.setName(location);
			}
			
			if (!email.equals("null")) {
				userDetail.setName(email);
			}
			
			userDetailList.add(userDetail);
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
