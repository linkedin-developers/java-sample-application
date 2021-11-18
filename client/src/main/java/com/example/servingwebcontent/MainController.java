package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${SERVER_URL}")
	private String SERVER_URL;


    /**
     * Serves a html webpage with operations related to OAuth
     **/

    @GetMapping("/")
	public String oauth(final Model model) throws Exception {
		String action = "Please Click login with linkedIn Button to generate access token!";
		String response = "";
		try {
			response = Rest_Template.getForObject(SERVER_URL  + "token", String.class);
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
		return "index";
	}

    /**
     * Handles the post requests of Html page, calls the API endpoints of server URL.
     * To return a response and updates it on UI
     **/

    @PostMapping(path = "/", produces = { "application/json", "application/xml" }, consumes = { "application/x-www-form-urlencoded" })
    public String postBody(@RequestBody final String data, final Model model) throws Exception {
        String response = "";
        String action = "";
        if (data.equals("two_legged_auth=2+Legged+Auth")) {
            action = "Creating 2 legged auth access token";
            try {
                response = Rest_Template.getForObject(SERVER_URL  + "two_legged_auth", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
            }

		} else if (data.equals("profile=Get+Profile")) {
            action = "Getting public profile...";
            try {
                response = Rest_Template.getForObject(SERVER_URL + "profile", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
            }

        } else if (data.equals("refresh_token=Refresh+Token")) {
            action = "Refreshing token...";
            try {
                response = Rest_Template.getForObject(SERVER_URL + "refresh_token", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
            }
        } else {
            action = "Performing token introspection...";
            try {  
            response = Rest_Template.getForObject(SERVER_URL + "token_introspection", String.class);
            } catch (Exception e) {
                response = "Error retrieving the data";
            }
        }

        model.addAttribute("output", response);
        model.addAttribute("action", action);
        return "index";
    }

}
