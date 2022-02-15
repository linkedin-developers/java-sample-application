/*
* Getting Started with LinkedIn's Marketing APIs ,
* Documentation: https://docs.microsoft.com/en-us/linkedin/marketing/getting-started
* The additional scopes required to use these functions are:
* 'rw_ads, rw_organization_admin'
* You can invoke these functions independently with valid access token string as a parameter.
* More Docs: https://docs.microsoft.com/en-us/linkedin/marketing/integrations/ads/account-structure/create-and-manage-account-users
*/
package com.example.api;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import static com.example.api.LinkedInOAuthController.token;


@RestController
public final class LinkedInMarketingController {

    private RestTemplate lmsTemplate = new RestTemplate();

    /*
     * Find Ad Accounts by Authenticated User or Verifying Ad Accounts Access
     * @return All Ad Accounts that an authenticated user has access to can be retrieved with the following endpoint.
     */
    @RequestMapping("/findAdAccounts")
    public String Find_ad_account() {
        try {
            String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountUsersV2?q=authenticatedUser&oauth2_access_token=" + token, String.class);
            return response;
        } catch (HttpStatusCodeException e) {
            return e.getResponseBodyAsString();
        }

    }

    /*
     * Find Ad Account roles of Authenticated User
     * @return fetch all users associated with specified Ad Accounts
     */
    @RequestMapping("/getUserOrgAccess")
    public String Get_user_org_access() {
        try {
            String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/organizationAcls?q=roleAssignee&oauth2_access_token=" + token, String.class);
            return response;
        } catch (HttpStatusCodeException e) {
            return e.getResponseBodyAsString();
        }

    }

    /*
     * Fetch Ad Account by ID
     * @return Individual Ad Account Details
     */
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
    }

}
