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

    @Bean
    public final RestTemplate lmsTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate lmsTemplate;
    static final String SERVER_URL = "http://127.0.0.1:5000/";


    /**
     * Serves a html webpage with operations related to OAuth
     **/

    @GetMapping("/lms")
    public String oauth(@RequestParam(name = "name", required = false, defaultValue = "World") final String name, final Model model) {
        String action = "Please Click login with linkedIn Button to generate access token!";
        model.addAttribute("auth_url", SERVER_URL + "login");
        model.addAttribute("output", "response");
        model.addAttribute("action", action);
        return "lms";
    }

    /**
     * Handles the post requests of Html page, calls the API endpoints of server URL.
     * To return a response and updates it on UI
     **/

    @PostMapping(path = "/lms", produces = {
        "application/json",
        "application/xml"
    }, consumes = {
        "application/x-www-form-urlencoded"
    })
    public String postBody(@RequestBody final String post_array, final Model model) {
        String response = "";

        switch (post_array) {
            case "Check_token=Check+Token":
                response = lmsTemplate.getForObject(SERVER_URL + "refresh_token", String.class);
                break;
            case "Find_ad_account_by_authenticated_user=Find+Ad+Accounts":
                response = lmsTemplate.getForObject(SERVER_URL + "Find_ad_account_by_authenticated_user", String.class);
                break;
            case "Get_user_org_access=Find+Ad+Account+roles":
                response = lmsTemplate.getForObject(SERVER_URL + "Get_user_org_access", String.class);
                break;
            case "Fetch_ad_account=Fetch+Ad+Account+by+ID":
                response = lmsTemplate.getForObject(SERVER_URL + "Fetch_ad_account", String.class);
                break;
            case "Get_ad_account_user=Get+Ad+Account+User":
                response = lmsTemplate.getForObject(SERVER_URL + "Get_ad_account_user", String.class);
                break;
            case "Find_ad_account_user_by_account=Find+Ad+Account+Users":
                response = lmsTemplate.getForObject(SERVER_URL + "Find_ad_account_user_by_account", String.class);
                break;

                //Default case statement
            default:
                response = "No API calls made!";
        }

        model.addAttribute("output", response);
        model.addAttribute("action", post_array);
        return "lms";
    }

}
