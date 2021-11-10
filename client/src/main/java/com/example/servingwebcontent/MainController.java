package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

/*
* Main controller called by spring-boot to map URL's
 */

@Controller
public final class MainController {

    @Bean
    public RestTemplate Rest_Template(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    static final RestTemplate Rest_Template = new RestTemplate();
    static final String SERVER_URL = "http://localhost:8080/";


    /**
     * Serves a html webpage with operations related to OAuth
     **/

    @GetMapping("/")
	public String oauth(final Model model) {
		String action = "";
		String response = "";
		try {
			response = Rest_Template.getForObject(SERVER_URL  + "token_introspection", String.class);
			action = "Valid access token is ready to use!";
		} catch (Exception e) {
			action = "Please Click login with linkedIn Button to generate access token!";
            e.printStackTrace();
		}
		model.addAttribute("auth_url", SERVER_URL + "login");
		model.addAttribute("output", "Output");
		model.addAttribute("action", action);
		return "oauthli";
	}

    /**
     * Handles the post requests of Html page, calls the API endpoints of server URL.
     * To return a response and updates it on UI
     **/

    @PostMapping(path = "/", produces = {
        "application/json",
        "application/xml"
    }, consumes = {
        "application/x-www-form-urlencoded"
    })
    public String postBody(@RequestBody final String data, final Model model) {
        String response = "";
        String action = "";
        if (data.equals("two_legged_auth=2+Legged+Auth")) {
            action = "Creating 2 legged auth access token";
            try {
                response = Rest_Template.getForObject(SERVER_URL  + "two_legged_auth", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
                e.printStackTrace();
            }

		} else if (data.equals("profile=Get+Profile")) {
            action = "Getting public profile...";
            try {
                response = Rest_Template.getForObject(SERVER_URL + "profile", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
                e.printStackTrace();
            }

        } else if (data.equals("refresh_token=Refresh+Token")) {
            action = "Refreshing token...";
            try {
                response = Rest_Template.getForObject(SERVER_URL + "refresh_token", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
                e.printStackTrace();
            }
        } else {
            action = "Performing token introspection...";
            try {  
            response = Rest_Template.getForObject(SERVER_URL + "token_introspection", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
                e.printStackTrace();
            }
        }

        model.addAttribute("output", parseJSON(response));
        model.addAttribute("action", action);
        return "oauthli";
    }

    public Object parseJSON(final String response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = objectMapper.readValue(response,
				new TypeReference<Map<String, Object>>(){});
		Object elements = jsonMap.get("elements");
		return elements;
	}

}
