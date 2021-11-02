package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
		String action = "Access token is empty!";
		String AUTH_URL = SERVER_URL  + "login";
		model.addAttribute( "auth_url", AUTH_URL);
		model.addAttribute( "output", "response" );
		model.addAttribute( "action", action );
		return "lms";
	}

	/**
	 * Handles the post requests of Html page, calls the API endpoints of server URL.
	 * To return a response and updates it on UI
	 **/

	@PostMapping(path = "/lms", produces = {"application/json", "application/xml"}, consumes = {"application/x-www-form-urlencoded"})
	public String postBody(@RequestBody String POST_ARRAY, final Model model, @RequestParam(name = "account_id", required = false) final String account_id) {
		String response;

		switch(POST_ARRAY){
			case "token_introspection=Token+Introspection":
				response = lmsTemplate.getForObject(SERVER_URL  + "token_introspection", String.class);
			  break;
			case "Find_ad_account=Find+Ad+Accounts":
				response = lmsTemplate.getForObject(SERVER_URL  + "Find_ad_account", String.class);
				break;
			case "Get_user_org_access=Get+user+org+access":
				response = lmsTemplate.getForObject(SERVER_URL  + "Get_user_org_access", String.class);
				break;
			default: response = "No API calls made!";
		}

		model.addAttribute("output", response);
		model.addAttribute("account_id", "");
		model.addAttribute("action", "Making Server API request...");
		return "lms";
	}

}
