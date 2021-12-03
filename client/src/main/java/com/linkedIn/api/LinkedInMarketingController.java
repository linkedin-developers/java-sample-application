package com.linkedIn.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


@Controller
public final class LinkedInMarketingController {

	private RestTemplate lmsTemplate = new RestTemplate();

	@Value("${SERVER_URL}")
	private String SERVER_URL;

	/**
	 * Serves a html webpage with operations related to OAuth
	 **/

	@GetMapping("/Marketing")
	public String oauth(final Model model) throws Exception {
		String action = "Please Click login with linkedIn Button to generate access token!";
		String response = "";
		try {
			response = lmsTemplate.getForObject(SERVER_URL + "token_introspection", String.class);
			if (response != "Error Introspecting access token!") {
				action = "Valid access token is ready to use!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("auth_url", SERVER_URL + "login");
		model.addAttribute("output", "Output");
		model.addAttribute("action", action);
		return "marketingtemplate";
	}

	/**
	 * Handles the post requests of Html page, calls the API endpoints of server URL.
	 * To return a response and updates it on UI
	 **/

	@PostMapping(path = "/Marketing")
	public String postBody(@RequestBody final String data, final Model model) throws Exception {
		String response = "";
		Object Find_Ad_Account = null;
		Object Get_user_org_access = null;

		switch (data) {
			case "token_introspection=Token+Introspection":
				try {
					response = lmsTemplate.getForObject(SERVER_URL + "token_introspection", String.class);
				} catch (Exception e) {
					response = "Error retrieving the data";
				}
				break;

			case "Find_ad_account=Find+Ad+Accounts":
				try {
					response = lmsTemplate.getForObject(SERVER_URL + "Find_ad_account", String.class);
					Find_Ad_Account = parseJSON(response);
					response = "Find Ad Accounts by Authenticated User:- ";
				} catch (Exception e) {
					response = "Error retrieving the data";
				}
				break;

			case "Get_user_org_access=Find+Org+Access":
				try {
					response = lmsTemplate.getForObject(SERVER_URL + "Get_user_org_access", String.class);
					Get_user_org_access = parseJSON(response);
					response = "Find Ad Account roles of Authenticated User:- ";
				} catch (Exception e) {
					response = "Error retrieving the data";
				}
				break;
			default:
				response = "No API calls made!";
		}

		model.addAttribute("output", response);
		model.addAttribute("Find_ad_account", Find_Ad_Account);
		model.addAttribute("Get_user_org_access", Get_user_org_access);
		model.addAttribute("action", "Making Server API request...");
		return "marketingtemplate";
	}

	public Object parseJSON(final String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Map < String, Object > jsonMap = objectMapper.readValue(response,
				new TypeReference < Map < String, Object >> () {});
		Object elements = jsonMap.get("elements");
		return elements;
	}

}