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
import static com.linkedIn.api.Constants.*;


@Controller
public final class LinkedInMarketingController {

	private RestTemplate lmsTemplate = new RestTemplate();

	@Value("${SERVER_URL}")
	private String SERVER_URL;

	/**
	 *
	 * Serves a html webpage with operations related to OAuth
	 *
	 * @param model Spring Boot Model
	 * @return HTML page to render
	 */
	@GetMapping("/marketing")
	public String oauth(final Model model) {
		String action = "Start with LinkedIn's LMS APIs!";
		String response, output = "";
		try {
			response = lmsTemplate.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);
			if (!response.toLowerCase().contains("error")) {
				action = output = TOKEN_EXISTS_MESSAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("auth_url", SERVER_URL + "login");
		model.addAttribute("output", output);
		model.addAttribute("action", action);
		return LMS_PAGE;
	}

	/**
	 * Handles the post requests of Html page, calls the API endpoints of server URL.
	 * @param data string data from the UI component
	 * @param model Spring Boot Model
	 * @return HTML page to render
	 */
	@PostMapping(path = "/marketing")
	public String postBody(@RequestBody final String data, final Model model) {
		String response = "";
		Object Find_Ad_Account = null;
		Object Get_user_org_access = null;

		switch (data) {
			case CASE_TOKEN_INTROSPECTION:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);
				} catch (Exception e) {
					response = GENERIC_ERROR_MESSAGE;
				}
				break;

			case CASE_FIND_AD_ACCOUNTS:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + FIND_AD_ACCOUNTS_ENDPOINT, String.class);
					if(response.toLowerCase().contains("error")) {
						response = parseJSON(response).toString();
					} else {
						Find_Ad_Account = parseJSON(response);
						response = FIND_AD_ACCOUNTS_MESSAGE;
					}
				} catch (Exception e) {
					response = GENERIC_ERROR_MESSAGE;
				}
				break;

			case CASE_GET_USER_ORG_ROLES:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + GET_USER_ORG_ACCESS_ENDPOINT, String.class);
					if(response.toLowerCase().contains("error")) {
						response = parseJSON(response).toString();
					} else {
						Get_user_org_access = parseJSON(response);
						response = FIND_USER_ROLES_MESSAGE;
					}
				} catch (Exception e) {
					response = GENERIC_ERROR_MESSAGE;
				}
				break;
			default:
				response = DEFAULT_MESSAGE;
		}

		model.addAttribute("output", response);
		model.addAttribute("Find_ad_account", Find_Ad_Account);
		model.addAttribute("Get_user_org_access", Get_user_org_access);
		model.addAttribute("auth_url", SERVER_URL + THREE_LEGGED_TOKEN_GEN_ENDPOINT);
		model.addAttribute("action", "Making Server API request...");
		return LMS_PAGE;
	}

	public Object parseJSON(final String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Map < String, Object > jsonMap = objectMapper.readValue(response,
				new TypeReference < Map < String, Object >>() { } );
		if(jsonMap.containsKey("elements"))
			return jsonMap.get("elements");
		 else
		 	return jsonMap.get("message");
	}

}
