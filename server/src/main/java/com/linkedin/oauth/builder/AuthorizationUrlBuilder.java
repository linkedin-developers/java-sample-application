package com.linkedin.oauth.builder;

import com.linkedin.oauth.service.LinkedInOAuthService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.linkedin.oauth.util.Constants.AUTHORIZE_URL;

/***
 * Builder class for LinkedIn OAuth 2.0 authorization URL.
 */

public final class AuthorizationUrlBuilder {

    private String state;
    private Map<String, String> additionalParams;
    private final LinkedInOAuthService oauth20Service;

    /**
     * public constructor
     * @param oauth20Service {@link LinkedInOAuthService}
     */
    public AuthorizationUrlBuilder(final LinkedInOAuthService oauth20Service) {
        this.oauth20Service = oauth20Service;
    }

    /**
     * Setter for state
     */
    public AuthorizationUrlBuilder state(final String state) {
        this.state = state;
        return this;
    }

    /**
     * Setter for additional params
     */
    public AuthorizationUrlBuilder additionalParams(final Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
        return this;
    }

    /**
     * Builds the authorization URL
     */
    public String build() throws UnsupportedEncodingException {

        String authoriztaionUrl = AUTHORIZE_URL
        + "?response_type=code&client_id="
        + oauth20Service.getApiKey()
        + "&redirect_uri="
        + oauth20Service.getRedirectUri()
        + "&state="
        + state
        + "&scope="
        + URLEncoder.encode(oauth20Service.getScope(), String.valueOf(StandardCharsets.UTF_8));
        return authoriztaionUrl;
    }
}
