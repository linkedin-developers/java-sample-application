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
import java.util.logging.Logger;
import java.util.logging.Level;
import static com.linkedIn.api.Constants.TOKEN_INTROSPECTION_ENDPOINT;
import static com.linkedIn.api.Constants.TOKEN_EXISTS_MESSAGE;
import static com.linkedIn.api.Constants.LMS_PAGE;
import static com.linkedIn.api.Constants.THREE_LEGGED_TOKEN_GEN_ENDPOINT;
import static com.linkedIn.api.Constants.CASE_TOKEN_INTROSPECTION;
import static com.linkedIn.api.Constants.GENERIC_ERROR_MESSAGE;
import static com.linkedIn.api.Constants.CASE_FIND_AD_ACCOUNTS;
import static com.linkedIn.api.Constants.FIND_AD_ACCOUNTS_ENDPOINT;
import static com.linkedIn.api.Constants.FIND_AD_ACCOUNTS_MESSAGE;
import static com.linkedIn.api.Constants.CASE_GET_USER_ORG_ROLES;
import static com.linkedIn.api.Constants.GET_USER_ORG_ACCESS_ENDPOINT;
import static com.linkedIn.api.Constants.DEFAULT_MESSAGE;
import static com.linkedIn.api.Constants.FIND_USER_ROLES_MESSAGE;

/**
 * LMS controller for handling the actions on the marketing page at
 * http://localhost:8989/marketing (Default)
 */
@Controller
public final class LinkedInMarketingController {

	private RestTemplate lmsTemplate = new RestTemplate();
	private Logger logger = Logger.getLogger(LinkedInMarketingController.class.getName());


	@Value("${SERVER_URL}")
	private String SERVER_URL;

	/**
	 *
	 * Serves a html webpage with operations related to OAuth + LMS functions
	 *
	 * @param model Spring Boot Model
	 * @return HTML page to render
	 */
	@GetMapping("/marketing")
	public String oauth(final Model model) {
		String action = "Start with LinkedIn's LMS APIs!";
		String response = "";
		String output = "";
		try {
			response = lmsTemplate.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);

			logger.log(Level.INFO, "Validating if a token is already in session. Response from token introspection end point is: {0}", response);

			if (!response.toLowerCase().contains("error")) {
				action = TOKEN_EXISTS_MESSAGE;
				output = TOKEN_EXISTS_MESSAGE;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		model.addAttribute("auth_url", SERVER_URL + THREE_LEGGED_TOKEN_GEN_ENDPOINT);
		model.addAttribute("output", output);
		model.addAttribute("action", action);

		logger.log(Level.INFO, "Completed execution for rendering marketing page. The model values are output: {0},action: {1}.", new String[]{output, action});

		return LMS_PAGE;
	}

	/**
	 * Handles the post requests of Html page, calls the API endpoints of server URL.
	 *
	 * @param data string data from the UI component
	 * @param model Spring Boot Model
	 * @return HTML page to render
	 */
	@PostMapping(path = "/marketing")
	public String postBody(@RequestBody final String data, final Model model) {
		String response = "";
		Object Find_Ad_Account = null;
		Object Get_user_org_access = null;

		logger.log(Level.INFO, "Handling on click of marketing page bubttons. Button cliked is {0}", data);

		switch (data) {
			case CASE_TOKEN_INTROSPECTION:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					response = GENERIC_ERROR_MESSAGE;
				}
				break;

			case CASE_FIND_AD_ACCOUNTS:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + FIND_AD_ACCOUNTS_ENDPOINT, String.class);
					if (response.toLowerCase().contains("error")) {
						response = parseJSON(response).toString();
					} else {
						Find_Ad_Account = parseJSON(response);
						response = FIND_AD_ACCOUNTS_MESSAGE;
					}
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					response = GENERIC_ERROR_MESSAGE;
				}
				break;

			case CASE_GET_USER_ORG_ROLES:
				try {
					response = lmsTemplate.getForObject(SERVER_URL + GET_USER_ORG_ACCESS_ENDPOINT, String.class);
					if (response.toLowerCase().contains("error")) {
						response = parseJSON(response).toString();
					} else {
						Get_user_org_access = parseJSON(response);
						response = FIND_USER_ROLES_MESSAGE;
					}
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
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

		logger.log(Level.INFO, "Completed execution on button click. The output is {0}", response);

		return LMS_PAGE;
	}

	/**
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private Object parseJSON(final String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Map < String, Object > jsonMap = objectMapper.readValue(response,
				new TypeReference < Map < String, Object >>() { } );
		if (jsonMap.containsKey("elements")) {
			return jsonMap.get("elements");
		} else {
			return jsonMap.get("message");
		}
	}

}
