package com.example.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.linkedin.oauth.builder.ScopeBuilder;
import com.linkedin.oauth.pojo.AccessToken;
import com.linkedin.oauth.service.LinkedInOAuthService;
import org.springframework.web.servlet.view.RedirectView;

import static com.example.api.Constants.TOKEN_INTROSPECTION_ERROR_MESSAGE;
import static com.example.api.Constants.LI_ME_ENDPOINT;
import static com.example.api.Constants.USER_AGENT_OAUTH_VALUE;
import static com.linkedin.oauth.util.Constants.TOKEN_INTROSPECTION_URL;
import static com.linkedin.oauth.util.Constants.REQUEST_TOKEN_URL;

/*
 * Getting Started with LinkedIn's OAuth APIs ,
 * Documentation: https://docs.microsoft.com/en-us/linkedin/shared/authentication/authentication?context=linkedin/context
 */

@RestController
public final class LinkedInOAuthController {

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;


    //Define all inputs in the property file
    private Properties prop = new Properties();
    private String propFileName = "config.properties";
    public static String token = null;
    public String refresh_token = null;
    public LinkedInOAuthService service;

    private Logger logger = Logger.getLogger(LinkedInOAuthController.class.getName());

    /**
     * Make a Login request with LinkedIN Oauth API
     *
     * @param code optional Authorization code
     * @return Redirects to the client UI after successful token creation
     */
    @RequestMapping(value = "/login")
    public RedirectView oauth(@RequestParam(name = "code", required = false) final String code) throws Exception {

        loadProperty();

        // Construct the LinkedInOAuthService instance for use
        service = new LinkedInOAuthService.LinkedInOAuthServiceBuilder()
                .apiKey(prop.getProperty("clientId"))
                .apiSecret(prop.getProperty("clientSecret"))
                .defaultScope(new ScopeBuilder(prop.getProperty("scope").split(",")).build()) // replace with desired scope
                .callback(prop.getProperty("redirectUri"))
                .build();

        final String secretState = "secret" + new Random().nextInt(999_999);
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();

        RedirectView redirectView = new RedirectView();

        if (code != null && !code.isEmpty()) {

            logger.log(Level.INFO, "Authorization code not empty, trying to generate a 3-legged OAuth token.");

            final AccessToken[] accessToken = {
                    new AccessToken()
            };
            HttpEntity request = service.getAccessToken3Legged(code);
            String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
            accessToken[0] = service.convertJsonTokenToPojo(response);

            prop.setProperty("token", accessToken[0].getAccessToken());
            token = accessToken[0].getAccessToken();
            refresh_token = accessToken[0].getRefreshToken();

            logger.log(Level.INFO, "Generated Access token and Refresh Token.");

            redirectView.setUrl(prop.getProperty("client_url"));
        } else {
            redirectView.setUrl(authorizationUrl);
        }
        return redirectView;
    }


    /**
     * Create 2 legged auth access token
     *
     * @return Redirects to the client UI after successful token creation
     */
    @RequestMapping(value = "/twoLeggedAuth")
    public RedirectView two_legged_auth() throws Exception {
        loadProperty();

        RedirectView redirectView = new RedirectView();
        // Construct the LinkedInOAuthService instance for use
        service = new LinkedInOAuthService.LinkedInOAuthServiceBuilder()
                .apiKey(prop.getProperty("clientId"))
                .apiSecret(prop.getProperty("clientSecret"))
                .defaultScope(new ScopeBuilder(prop.getProperty("scope").split(",")).build())
                .callback(prop.getProperty("redirectUri"))
                .build();

        final AccessToken[] accessToken = {
                new AccessToken()
        };

        HttpEntity request = service.getAccessToken2Legged();
        String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
        accessToken[0] = service.convertJsonTokenToPojo(response);
        prop.setProperty("token", accessToken[0].getAccessToken());
        token = accessToken[0].getAccessToken();

        logger.log(Level.INFO, "Generated Access token.");

        redirectView.setUrl(prop.getProperty("client_url"));
        return redirectView;
    }

    /**
     * Make a Token Introspection request with LinkedIN API
     *
     * @return check the Time to Live (TTL) and status (active/expired) for all token
     */

    @RequestMapping(value = "/tokenIntrospection")
    public String token_introspection() throws Exception {
        if (service != null) {
            HttpEntity request = service.introspectToken(token);
            String response = restTemplate.postForObject(TOKEN_INTROSPECTION_URL, request, String.class);
            logger.log(Level.INFO, "Token introspected. Details are {0}", response);

            return response;
        } else {
            return TOKEN_INTROSPECTION_ERROR_MESSAGE;
        }
    }


    /**
     * Make a Refresh Token request with LinkedIN API
     *
     * @return get a new access token when your current access token expire
     */

    @RequestMapping(value = "/refreshToken")
    public String refresh_token() throws IOException {
        HttpEntity request = service.getAccessTokenFromRefreshToken(refresh_token);
        String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
        logger.log(Level.INFO, "Used Refresh Token to generate a new access token successfully.");
        return response;
    }

    /**
     * Make a Public profile request with LinkedIN API
     *
     * @return Public profile of user
     */

    @RequestMapping(value = "/profile")
    public String profile() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        return restTemplate.exchange(LI_ME_ENDPOINT + token, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
    }

    private void loadProperty() throws IOException {
        InputStream inputStream = LinkedInOAuthController.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }
}
