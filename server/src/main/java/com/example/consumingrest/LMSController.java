package com.example.consumingrest;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public final class LMSController {
  
  private RestTemplate lmsTemplate = new RestTemplate();
  public String token;
 
  /*
  * Find Ad Accounts by Authenticated User or Verifying Ad Accounts Access 
  */
  @RequestMapping("/Find_ad_account")
  public String Find_ad_account(final HttpSession session) {
    token = (String) session.getAttribute("tokenString");
    String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountUsersV2?q=authenticatedUser&oauth2_access_token=" + token, String.class);
    return response;

  }

  /* 
  * Find Ad Account roles of Authenticated User 
  */
  @RequestMapping("/Get_user_org_access")
  public String Get_user_org_access(final HttpSession session) {
    token = (String) session.getAttribute("tokenString");
    String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/organizationalEntityAcls?q=roleAssignee&oauth2_access_token=" + token, String.class);
    return response;

  }

  /* 
  * Fetch Ad Account by ID 
  */
  @RequestMapping(value = "/Fetch_ad_account")
  public String Fetch_ad_account(@RequestParam(name = "account", required = false) final String account, final HttpSession session) {
    if (account == null) {
      return "Please pass the account ID!";
    }
    token = (String) session.getAttribute("tokenString");
    String response = lmsTemplate.getForObject("https://api.linkedin.com/v2/adAccountsV2/" + account + "?oauth2_access_token=" + token, String.class);
    return response;

  }

}
