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
    static final String SERVER_URL = "http://127.0.0.1:5000/";


    /**
     * Serves a html webpage with operations related to OAuth
     **/

    @GetMapping("/")
    public String oauth(final Model model) {
        String action = "Access token is unavailable!";
        model.addAttribute("auth_url", SERVER_URL + "login");
        model.addAttribute("output", "response");
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
        if (data.equals("profile=Get+Profile")) {
            action = "Getting public profile...";
            response = Rest_Template.getForObject(SERVER_URL + "profile", String.class);

        } else if (data.equals("refresh_token=Refresh+Token")) {
            action = "Refreshing token...";
            response = Rest_Template.getForObject(SERVER_URL + "refresh_token", String.class);
        } else {
            action = "Performing token introspection...";
            response = Rest_Template.getForObject(SERVER_URL + "token_Introspection", String.class);
        }

        model.addAttribute("output", response);
        model.addAttribute("action", action);
        return "oauthli";
    }

}
