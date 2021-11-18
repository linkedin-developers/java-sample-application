package com.example.servingwebcontent;

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
public final class LMSController {

	private RestTemplate lmsTemplate = new RestTemplate();

	@Value("${SERVER_URL}")
	private String SERVER_URL;
    
	/**
	 * Serves a html webpage with operations related to OAuth
	 **/

	@GetMapping("/lms")
	public String oauth(final Model model) {
		String action = "Please Click login with linkedIn Button to generate access token!";
		String response = "";
		try {
			response = lmsTemplate.getForObject(SERVER_URL  + "token_introspection", String.class);
		    if(response != "Error Introspecting access token!")
            {
                action = "Valid access token is ready to use!";
            }
		} catch (Exception e) {
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
		String response = "";
		String output = "";
		Object Find_ad_account = null;
		Object Get_user_org_access = null;
        
		switch (data) {
			case "token_introspection=Token+Introspection":
				try {
					output = lmsTemplate.getForObject(SERVER_URL  + "token_introspection", String.class);
				    

				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
			  break;

			case "Find_ad_account=Find+Ad+Accounts":
				try {
					response = lmsTemplate.getForObject(SERVER_URL  + "Find_ad_account", String.class);
				    Find_ad_account  = parseJSON(response);
					output = "Find Ad Accounts";
				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
				break;

			case "Get_user_org_access=Get+user+org+access":
				try {
					response = lmsTemplate.getForObject(SERVER_URL  + "Get_user_org_access", String.class);
				    Get_user_org_access = parseJSON(response);
					output = "Get user org access";
				} catch (Exception e) {
					response = "Error retrieving the data";
                	e.printStackTrace();
				}
				break;
			default: response = "No API calls made!";
		}

		model.addAttribute("output", output);
		model.addAttribute("Find_ad_account", Find_ad_account);
		model.addAttribute("Get_user_org_access", Get_user_org_access);
		model.addAttribute("action", "Making Server API request...");
		return "lms";
	}

	public Object parseJSON(final String response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = objectMapper.readValue(response,
				new TypeReference<Map<String, Object>>(){});
		Object elements = jsonMap.get("elements");
		return elements;
	}

}
