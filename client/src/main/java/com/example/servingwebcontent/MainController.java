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

/*
* Main controller called by springboot to map URL's
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
    public String oauth(@RequestParam(name = "name", required = false, defaultValue = "World") final String name, final Model model) {
        String action = "Please Click login with linkedIn Button to generate access token!";
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

        if (data.equals("profile=Get+Profile")) {

            response = Rest_Template.getForObject(SERVER_URL + "profile", String.class);

        } else if (data.equals("refresh_token=Refresh+Token")) {

            response = Rest_Template.getForObject(SERVER_URL + "refresh_token", String.class);
        } else {
            //token_Introspection=Token+Introspection
            response = Rest_Template.getForObject(SERVER_URL + "token_Introspection", String.class);
        }

        model.addAttribute("output", response);
        model.addAttribute("action", data);
        return "oauthli";
    }

}
