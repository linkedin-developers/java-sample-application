package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


@Controller
public final class LMSController {

	private RestTemplate lmsTemplate;
	private String SERVER_URL = "http://localhost:8989/";


	/**
	 * Serves a html webpage with operations related to OAuth
	 **/

	@GetMapping("/lms")
	public String oauth(final Model model) {
		String action = "";
		String response = "";
		try {
			response = lmsTemplate.getForObject(SERVER_URL  + "token_introspection", String.class);
			action = "Valid access token is ready to use!";
		} catch (Exception e) {
			action = "Please Click login with linkedIn Button to generate access token!";
			e.printStackTrace();
		}
		model.addAttribute("auth_url", SERVER_URL + "login");
		model.addAttribute("output", "Output");
		model.addAttribute("action", action);
		return "lms";
	}

	/**
	 * Handles the post requests of Html page, calls the API endpoints of server URL.
	 * To return a response and updates it on UI
	 **/

	@PostMapping(path = "/lms", produces = {"application/json", "application/xml"}, consumes = {"application/x-www-form-urlencoded"})
	public String postBody(@RequestBody final String data, final Model model) {
		String response;

		switch (data) {
			case "token_introspection=Token+Introspection":
				try {
					response = lmsTemplate.getForObject(SERVER_URL  + "token_introspection", String.class);
				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
			  break;

			case "Find_ad_account=Find+Ad+Accounts":
				try {
					response = lmsTemplate.getForObject(SERVER_URL  + "Find_ad_account", String.class);
				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
				break;

			case "Get_user_org_access=Get+user+org+access":
				try {
					response = lmsTemplate.getForObject(SERVER_URL  + "Get_user_org_access", String.class);
				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
				break;
			default: response = "No API calls made!";
		}

		model.addAttribute("output", response);
		model.addAttribute("action", "Making Server API request...");
		return "lms";
	}

}
