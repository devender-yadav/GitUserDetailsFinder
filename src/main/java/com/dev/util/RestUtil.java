package com.dev.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class RestUtil {

	public static String makeGETRequest(String url) {
		return ClientBuilder.newClient().target(url).request().get(String.class);
	}

	public static String makeGETRequest(String url, String userName, String password) {

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
		Client client = ClientBuilder.newClient();
		client.register(feature);
		return client.target(url).request().get(String.class);
	}

	public static String makePOSTRequest(String url, String resquestBody) {
		return ClientBuilder.newClient().target(url).request().post(Entity.json(resquestBody)).readEntity(String.class);
	}

	public static String makePOSTRequest(String url, String resquestBody, String userName, String password) {

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
		Client client = ClientBuilder.newClient();
		client.register(feature);
		return client.target(url).request().post(Entity.json(resquestBody)).readEntity(String.class);
	}

}
