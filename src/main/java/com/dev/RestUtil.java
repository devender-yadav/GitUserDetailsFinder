package com.dev;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

public class RestUtil {

	public static String makeGETRequest(String url) {
		return ClientBuilder.newClient().target(url).request().get(String.class);
	}

	public static String makePOSTRequest(String url, String resquestBody) {
		// connection is closed automatically after readEntity()
		return ClientBuilder.newClient().target(url).request().post(Entity.json(resquestBody)).readEntity(String.class);
	}

}
