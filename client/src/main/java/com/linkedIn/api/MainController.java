package com.linkedIn.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import static com.linkedIn.api.Constants.*;
/*
* Main controller called by spring-boot to map URL's
 */

@Controller
public final class MainController {

    @Bean
    public RestTemplate Rest_Template(final RestTemplateBuilder builder) {
        return builder.build();
    }

    static final RestTemplate Rest_Template = new RestTemplate();

    @Value("${SERVER_URL}")
	private String SERVER_URL;

    /**
     * Serves a html webpage with operations related to OAuth
     *
     * @param model Spring Boot Model
     * @return the html page to render
     */
    @GetMapping("/")
	public String oauth(final Model model) {
		String action = "Start with LinkedIn's OAuth API operations...";
		String response, output = "";
		try {
			response = Rest_Template.getForObject(SERVER_URL  + TOKEN_INTROSPECTION_ENDPOINT, String.class);
            if (!response.toLowerCase().contains("error")) {
                action = output = TOKEN_EXISTS_MESSAGE;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("auth_url", SERVER_URL + THREE_LEGGED_TOKEN_GEN_ENDPOINT);
		model.addAttribute("output", output);
		model.addAttribute("action", action);
		return OAUTH_PAGE;
	}

    /**
     * Handles the post requests of Html page, calls the API endpoints of server URL.
     *
     * @param data string data passed from the UI compoment
     * @param model Spring Boot Model
     * @return a page to render on UI
     */
    @PostMapping(path = "/", produces = { "application/json", "application/xml" }, consumes = { "application/x-www-form-urlencoded" })
    public String postBody(@RequestBody final String data, final Model model) {
        String response = "";
        String action = "";
        if (data.equals(CASE_TWO_LEGGED_TOKEN_GEN)) {
            action = ACTION_2_LEGGED_TOKEN_GEN;
            try {
                Rest_Template.getForObject(SERVER_URL  + TWO_LEGGED_TOKEN_GEN_ENDPOINT, String.class);
                response = TWO_LEGGED_TOKEN_GEN_SUCCESS_MESSAGE;
            } catch (Exception e) {
                response = GENERIC_ERROR_MESSAGE;
            }

		} else if (data.equals(CASE_GET_PROFILE)) {
            action = ACTION_GET_PROFILE;
            try {
                response = Rest_Template.getForObject(SERVER_URL + PROFILE_ENDPOINT, String.class);
            } catch (Exception e) {
                response = GENERIC_ERROR_MESSAGE;
            }

        } else if (data.equals(CASE_USE_REFRESH_TOKEN)) {
            action = ACTION_USE_REFRESH_TOKEN;
            try {
                response = Rest_Template.getForObject(SERVER_URL + USE_REFRESH_TOKEN_ENDPOINT, String.class);
            } catch (Exception e) {
                response = GENERIC_ERROR_MESSAGE;
            }
        } else {
            action = ACTION_TOKEN_INTROSPECTION;
            try {
            response = Rest_Template.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);
            } catch (Exception e) {
                response = GENERIC_ERROR_MESSAGE;
            }
        }

        model.addAttribute("output", response);
        model.addAttribute("auth_url", SERVER_URL + "login");
        model.addAttribute("action", action);
        return OAUTH_PAGE;
    }

}
