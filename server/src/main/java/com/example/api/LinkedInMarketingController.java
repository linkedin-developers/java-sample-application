package com.example.api;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.api.LinkedInOAuthController.token;
import static com.example.api.Constants.LI_FIND_AD_ACCOUNTS_FOR_USER_ENDPOINT;
import static com.example.api.Constants.LI_FIND_USER_ROLES_ENDPOINT;
import static com.example.api.Constants.USER_AGENT_LMS_VALUE;



/*
 * Getting Started with LinkedIn's Marketing APIs ,
 * Documentation: https://docs.microsoft.com/en-us/linkedin/marketing/getting-started
 * The additional scopes required to use these functions are:
 * 'rw_ads, rw_organization_admin'
 * You can invoke these functions independently with valid access token string as a parameter.
 * More Docs: https://docs.microsoft.com/en-us/linkedin/marketing/integrations/ads/account-structure/create-and-manage-account-users
 */

@RestController
public final class LinkedInMarketingController {

    private RestTemplate lmsTemplate = new RestTemplate();
    private Logger logger = Logger.getLogger(LinkedInMarketingController.class.getName());
    private HttpHeaders header = new HttpHeaders();

    /**
     * Find Ad Accounts by Authenticated User or Verifying Ad Accounts Access
     * @return All Ad Accounts that an authenticated user has access to can be retrieved with the following endpoint.
     */
    @RequestMapping("/findAdAccounts")
    public String Find_ad_account() {
        try {
            setUserAgentHeader();
            String response = lmsTemplate.exchange(LI_FIND_AD_ACCOUNTS_FOR_USER_ENDPOINT + token, HttpMethod.GET,
                new HttpEntity(header), String.class).getBody();
            logger.log(Level.INFO, "Find Ad Accounts for Authenticated User response is:{0}", response);
            return response;
        } catch (HttpStatusCodeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return e.getResponseBodyAsString();
        }

    }

    /**
     * Find Ad Account roles of Authenticated User
     * @return fetch all users associated with specified Ad Accounts
     */
    @RequestMapping("/getUserOrgAccess")
    public String Get_user_org_access() {
        try {
            setUserAgentHeader();
            String response = lmsTemplate.exchange(LI_FIND_USER_ROLES_ENDPOINT + token, HttpMethod.GET, new HttpEntity(header), String.class).getBody();
            logger.log(Level.INFO, "Find Org Roles for Authenticated User response is:{0}", response);
            return response;
        } catch (HttpStatusCodeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return e.getResponseBodyAsString();
        }

    }

    /**
     * Fetch Ad Account by ID
     * @return Individual Ad Account Details
     */
    // uncomment below to use
    /*
    @RequestMapping(value = "/fetchAdAccount")
    public String Fetch_ad_account(@RequestParam(name = "account", required = false) final String account, final HttpSession session) {
        if (account == null) {
            return "Please pass the account ID!";
        }
        try {
            token = (String) session.getAttribute("tokenString");
            String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountsV2/" + account + "?oauth2_access_token=" + token, String.class);
            return response;
        } catch (HttpStatusCodeException e) {
            return e.getResponseBodyAsString();
        }
    }*/

    private void setUserAgentHeader() {
        header.set(HttpHeaders.USER_AGENT, USER_AGENT_LMS_VALUE);
    }

}
