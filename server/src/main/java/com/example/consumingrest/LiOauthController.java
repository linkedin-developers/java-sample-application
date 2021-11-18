package com.example.consumingrest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.linkedin.oauth.builder.ScopeBuilder;
import com.linkedin.oauth.pojo.AccessToken;
import com.linkedin.oauth.service.LinkedInOAuthService;
import org.springframework.web.servlet.view.RedirectView;

import static com.linkedin.oauth.util.Constants.*;


@RestController
public final class LiOauthController {

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    //Define all inputs in the property file
    public Properties prop = new Properties();
    public String propFileName = "config.properties";
    public String token = null;
    public String refresh_token = null;
    public LinkedInOAuthService service;

    /*
     * Make a Login request with LinkedIN Oauth API
     */

    @RequestMapping(value = "/login")
    public RedirectView oauth(@RequestParam(name = "code", required = false) final String code, final HttpSession session) throws Exception {

        InputStream inputStream = LiOauthController.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        // Construct the LinkedInOAuthService instance for use
        service = new LinkedInOAuthService.LinkedInOAuthServiceBuilder()
            .apiKey(prop.getProperty("clientId"))
            .apiSecret(prop.getProperty("clientSecret"))
            .defaultScope(new ScopeBuilder(prop.getProperty("scope").split(",")).build()) // replace with desired scope
            .callback(prop.getProperty("redirectUri"))
            .build();

        session.setAttribute("service", service);
        final String secretState = "secret" + new Random().nextInt(999_999);
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
            .state(secretState)
            .build();

        RedirectView redirectView = new RedirectView();
        if (session.getAttribute("accessToken") != null) { 
            redirectView.setUrl(prop.getProperty("client_url"));
        } else if (code != null) {
            final AccessToken[] accessToken = {
                new AccessToken()
            };
            HttpEntity request = service.getAccessToken3Legged(code);
            String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
            accessToken[0] = service.convertJsonTokenToPojo(response);
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("tokenString", accessToken[0].getAccessToken());
            prop.setProperty("token", accessToken[0].getAccessToken());
            token = accessToken[0].getAccessToken();
            refresh_token = accessToken[0].getRefreshToken();
            redirectView.setUrl(prop.getProperty("client_url"));
        } else {
            redirectView.setUrl(authorizationUrl);
        }
    return redirectView;
    }
    

    /*
    * Create 2 legged auth access token
    */
    @RequestMapping(value = "/two_legged_auth")
    public RedirectView two_legged_auth(final HttpSession session) throws Exception {
        RedirectView redirectView = new RedirectView();
        final AccessToken[] accessToken = {
            new AccessToken()
        };
        try {
            HttpEntity request = service.getAccessToken2Legged();
            String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
            accessToken[0] = service.convertJsonTokenToPojo(response);
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("tokenString", accessToken[0].getAccessToken());
            prop.setProperty("token", accessToken[0].getAccessToken());
            token = accessToken[0].getAccessToken();
            refresh_token = accessToken[0].getRefreshToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectView.setUrl(prop.getProperty("client_url"));
        return redirectView;
    }

    /*
     * Make a Token Introspection request with LinkedIN API
     */

    @RequestMapping(value = "/token_introspection")
    public String token_introspection() throws Exception {

        try {
            HttpEntity request = service.introspectToken(token);
            String response = restTemplate.postForObject(TOKEN_INTROSPECTION_URL, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Introspecting access token!";
        }
    }


    /*
     * Make a Refresh Token request with LinkedIN API
     */

    @RequestMapping(value = "/refresh_token")
    public String refresh_token() throws IOException {
        if (refresh_token != null) {
            HttpEntity request = service.getAccessTokenFromRefreshToken(refresh_token);
            String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
            return response;
        } else {
            return "Refresh token does not exist!";
        }
    }

    /*
     * Make a Public profile request with LinkedIN API
     */

    @RequestMapping(value = "/profile")
    public String profile() throws Exception {
        try {
            return restTemplate.getForObject("https://api.linkedin.com/v2/me?oauth2_access_token=" + token, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting public profile!";
        }
    }
}
