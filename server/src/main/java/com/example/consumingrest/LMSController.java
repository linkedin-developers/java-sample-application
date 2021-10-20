package com.example.consumingrest;

import com.linkedin.oauth.builder.ScopeBuilder;
import com.linkedin.oauth.pojo.AccessToken;
import com.linkedin.oauth.service.LinkedInOAuthService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import static com.linkedin.oauth.util.Constants.*;


@RestController
public class LMSController () {

    @Bean
    public RestTemplate lmsTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate lmsTemplate;

    //Define all inputs in the property file
    public Properties prop = new Properties();
    public String propFileName = "config.properties";
    public String token = ""; //session.getAttribute("tokenString");
    public String refresh_token = null;
    public LinkedInOAuthService service;
    public String authorizationUrl;

    /* Find Ad Accounts by Authenticated User or Verifying Ad Accounts Access */
    @RequestMapping("/Find_ad_account")
    public String Find_ad_account() {
        String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountUsersV2?q=authenticatedUser&oauth2_access_token=" + token, String.class);
        return response;

    }

    /* Find Ad Account roles of Authenticated User */
    @RequestMapping("/Get_user_org_access")
    public String Get_user_org_access() {
        String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/organizationalEntityAcls?q=roleAssignee&oauth2_access_token=" + token, String.class);
        return response;

    }

    /* Fetch Ad Account by ID */
    @RequestMapping(value = "/Fetch_ad_account")
    public String Fetch_ad_account(@RequestParam(name = "account", required = false) String account) {
        String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountsV2/" + account + "?oauth2_access_token=" + token, String.class);
        return response;

    }

    /* Get Ad Account User */
    @RequestMapping(value = "/Get_ad_account_user")
    public String Get_ad_account_user(@RequestParam(name = "account", required = false) String account, @RequestParam(name = "user", required = false) String user) {
        String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountUsersV2/account=" + account + "&user=" + user, String.class);
        return response;

    }

    /* Create Ad Account User */
    @RequestMapping(value = "/Create_ad_account_user")
    public ResponseEntity < String > Create_ad_account_user(@RequestParam(name = "account", required = false) String account, @RequestParam(name = "user", required = false) String user, @RequestParam(name = "role", required = false) String role) {
        MultiValueMap < String, String > parameters = new LinkedMultiValueMap < String, String > ();
        parameters.add("account", account);
        parameters.add("user", user);
        parameters.add("role", role);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity < MultiValueMap < String, String >> request = new HttpEntity < MultiValueMap < String, String >> (parameters, headers);
        ResponseEntity < String >
            response = lmsTemplate.exchange("https://api.linkedin.com/v2/adAccountUsersV2/account=" + account + "&user=" + user, HttpMethod.POST, request, String.class);
        return response;

    }

    /* Update Ad Account User */
    @RequestMapping(value = "/Update_ad_account_user")
    public ResponseEntity < String > Update_ad_account_user(@RequestParam(name = "account", required = false) String account, @RequestParam(name = "user", required = false) String user, @RequestParam(name = "role", required = false) String role) {
        MultiValueMap < String, String > parameters = new LinkedMultiValueMap < String, String > ();
        parameters.add("account", account);
        parameters.add("user", user);
        parameters.add("role", role);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity < MultiValueMap < String, String >> request = new HttpEntity < MultiValueMap < String, String >> (parameters, headers);
        ResponseEntity < String >
            response = lmsTemplate.exchange("https://api.linkedin.com/v2/adAccountUsersV2/account=" + account + "&user=" + user, HttpMethod.POST, request, String.class);
        return response;

    }

    /* Delete Ad Account User */
    @RequestMapping(value = "/Delete_ad_account_user")
    public ResponseEntity < String > Delete_ad_account_user(@RequestParam(name = "account", required = false) String account, @RequestParam(name = "user", required = false) String user, @RequestParam(name = "role", required = false) String role) {
        MultiValueMap < String, String > parameters = new LinkedMultiValueMap < String, String > ();
        parameters.add("account", account);
        parameters.add("user", user);
        parameters.add("role", role);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity < MultiValueMap < String, String >> request = new HttpEntity < MultiValueMap < String, String >> (parameters, headers);
        ResponseEntity < String >
            response = lmsTemplate.exchange("https://api.linkedin.com/v2/adAccountUsersV2/account=" + account + "&user=" + user, HttpMethod.DELETE, request, String.class);
        return response;

    }


}