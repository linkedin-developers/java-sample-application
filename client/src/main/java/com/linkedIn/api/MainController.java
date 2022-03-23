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

import java.util.logging.Level;
import java.util.logging.Logger;
import static com.linkedIn.api.Constants.TOKEN_INTROSPECTION_ENDPOINT;
import static com.linkedIn.api.Constants.TOKEN_EXISTS_MESSAGE;
import static com.linkedIn.api.Constants.THREE_LEGGED_TOKEN_GEN_ENDPOINT;
import static com.linkedIn.api.Constants.OAUTH_PAGE;
import static com.linkedIn.api.Constants.CASE_TWO_LEGGED_TOKEN_GEN;
import static com.linkedIn.api.Constants.ACTION_2_LEGGED_TOKEN_GEN;
import static com.linkedIn.api.Constants.TWO_LEGGED_TOKEN_GEN_ENDPOINT;
import static com.linkedIn.api.Constants.TWO_LEGGED_TOKEN_GEN_SUCCESS_MESSAGE;
import static com.linkedIn.api.Constants.GENERIC_ERROR_MESSAGE;
import static com.linkedIn.api.Constants.CASE_GET_PROFILE;
import static com.linkedIn.api.Constants.ACTION_GET_PROFILE;
import static com.linkedIn.api.Constants.PROFILE_ENDPOINT;
import static com.linkedIn.api.Constants.CASE_USE_REFRESH_TOKEN;
import static com.linkedIn.api.Constants.ACTION_USE_REFRESH_TOKEN;
import static com.linkedIn.api.Constants.USE_REFRESH_TOKEN_ENDPOINT;
import static com.linkedIn.api.Constants.ACTION_TOKEN_INTROSPECTION;

/**
 * Main controller called by spring-boot to handle OAuth actions at
 * http://localhost:8989 (Default)
 */

@Controller
public final class MainController {

    @Bean
    public RestTemplate Rest_Template(final RestTemplateBuilder builder) {
        return builder.build();
    }

    static final RestTemplate Rest_Template = new RestTemplate();
    private Logger logger = Logger.getLogger(MainController.class.getName());

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
		String response = "";
        String output = "";
		try {
            response = Rest_Template.getForObject(SERVER_URL  + TOKEN_INTROSPECTION_ENDPOINT, String.class);
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

        logger.log(Level.INFO, "Completed execution for rendering OAuth page. The model values are output: {0},action: {1}.", new String[]{output, action});
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

        logger.log(Level.INFO, "Handling on click of marketing page bubttons. Button cliked is {0}", data);

        if (data.equals(CASE_TWO_LEGGED_TOKEN_GEN)) {
            action = ACTION_2_LEGGED_TOKEN_GEN;
            try {
                Rest_Template.getForObject(SERVER_URL  + TWO_LEGGED_TOKEN_GEN_ENDPOINT, String.class);
                response = TWO_LEGGED_TOKEN_GEN_SUCCESS_MESSAGE;
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = GENERIC_ERROR_MESSAGE;
            }

		} else if (data.equals(CASE_GET_PROFILE)) {
            action = ACTION_GET_PROFILE;
            try {
                response = Rest_Template.getForObject(SERVER_URL + PROFILE_ENDPOINT, String.class);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = GENERIC_ERROR_MESSAGE;
            }

        } else if (data.equals(CASE_USE_REFRESH_TOKEN)) {
            action = ACTION_USE_REFRESH_TOKEN;
            try {
                response = Rest_Template.getForObject(SERVER_URL + USE_REFRESH_TOKEN_ENDPOINT, String.class);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = GENERIC_ERROR_MESSAGE;
            }
        } else {
            action = ACTION_TOKEN_INTROSPECTION;
            try {
            response = Rest_Template.getForObject(SERVER_URL + TOKEN_INTROSPECTION_ENDPOINT, String.class);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = GENERIC_ERROR_MESSAGE;
            }
        }

        model.addAttribute("output", response);
        model.addAttribute("auth_url", SERVER_URL + THREE_LEGGED_TOKEN_GEN_ENDPOINT);
        model.addAttribute("action", action);

        logger.log(Level.INFO, "Completed execution on button click. The output is {0}", response);
        return OAUTH_PAGE;
    }

}
