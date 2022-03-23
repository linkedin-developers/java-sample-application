package com.linkedin.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.oauth.builder.AuthorizationUrlBuilder;
import com.linkedin.oauth.builder.ScopeBuilder;
import com.linkedin.oauth.pojo.AccessToken;
import com.linkedin.oauth.util.Preconditions;


import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static com.linkedin.oauth.util.Constants.*;
import static com.example.api.Constants.USER_AGENT_OAUTH_VALUE;


/**
 * LinkedIn 3-Legged OAuth Service
 */
@SuppressWarnings({"AvoidStarImport"})
public final class LinkedInOAuthService {

    private final String redirectUri;
    private final String apiKey;
    private final String apiSecret;
    private final String scope;

    private LinkedInOAuthService(final LinkedInOAuthServiceBuilder oauthServiceBuilder) {
        this.redirectUri = oauthServiceBuilder.redirectUri;
        this.apiKey = oauthServiceBuilder.apiKey;
        this.apiSecret = oauthServiceBuilder.apiSecret;
        this.scope = oauthServiceBuilder.scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getScope() {
        return scope;
    }

    /**
     *
     * @return an instance of {@link AuthorizationUrlBuilder}
     */
    public AuthorizationUrlBuilder createAuthorizationUrlBuilder() {
        return new AuthorizationUrlBuilder(this);
    }

    /**
     *
     * @param code authorization code
     * @return response of LinkedIn's 3-legged token flow captured in a POJO {@link AccessToken}
     * @throws IOException
     */
    public HttpEntity getAccessToken3Legged(final String code) throws IOException {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add(GRANT_TYPE, GrantType.AUTHORIZATION_CODE.getGrantType());
        parameters.add(CODE, code);
        parameters.add(REDIRECT_URI, this.redirectUri);
        parameters.add(CLIENT_ID, this.apiKey);
        parameters.add(CLIENT_SECRET, this.apiSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        return request;

    }

    /**
     *
     * @param refreshToken the refresh token obtained from the authorization code exchange
     * @return response of LinkedIn's refresh token flow captured in a POJO {@link AccessToken}
     * @throws IOException
     */
    public HttpEntity getAccessTokenFromRefreshToken(final String refreshToken) throws IOException {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add(GRANT_TYPE, GrantType.REFRESH_TOKEN.getGrantType());
        parameters.add(REFRESH_TOKEN, refreshToken);
        parameters.add(CLIENT_ID, this.apiKey);
        parameters.add(CLIENT_SECRET, this.apiSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        return request;
    }

    /**
     * Get access token by LinkedIn's OAuth2.0 Client Credentials flow
     * @return JSON String response
     * @throws IOException
     */
    public HttpEntity getAccessToken2Legged() throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add(GRANT_TYPE, GrantType.CLIENT_CREDENTIALS.getGrantType());
        parameters.add(CLIENT_ID, this.apiKey);
        parameters.add(CLIENT_SECRET, this.apiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        return request;
    }

    /**
     * Introspect token using LinkedIn's Auth tokenIntrospect API
     * @param token String representation of the access token
     * @return JSON String response
     */
    public HttpEntity introspectToken(final String token) throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add(CLIENT_ID, this.apiKey);
        parameters.add(CLIENT_SECRET, this.apiSecret);
        parameters.add(TOKEN, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        return request;
    }

    /**
     * Method to convert JSON String OAuth Token to POJO
     * @param accessToken
     * @return
     * @throws IOException
     */
    public AccessToken convertJsonTokenToPojo(final String accessToken) throws IOException {
        return new ObjectMapper().readValue(accessToken, AccessToken.class);
    }

    /**
     * Builder class for LinkedIn's OAuth Service
     */
    public static final class LinkedInOAuthServiceBuilder {
        private String redirectUri;
        private String apiKey;
        private String apiSecret;
        private String scope;

        public LinkedInOAuthServiceBuilder apiKey(final String apiKey) {
            Preconditions.checkEmptyString(apiKey, "Invalid Api key");
            this.apiKey = apiKey;
            return this;
        }

        public LinkedInOAuthServiceBuilder apiSecret(final String apiSecret) {
            Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
            this.apiSecret = apiSecret;
            return this;
        }

        public LinkedInOAuthServiceBuilder callback(final String callback) {
            this.redirectUri = callback;
            return this;
        }

        private LinkedInOAuthServiceBuilder setScope(final String scope) {
            Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
            this.scope = scope;
            return this;
        }

        public LinkedInOAuthServiceBuilder defaultScope(final ScopeBuilder scopeBuilder) {
            return setScope(scopeBuilder.build());
        }

        public LinkedInOAuthServiceBuilder defaultScope(final String scope) {
            return setScope(scope);
        }

        public LinkedInOAuthService build() {

            LinkedInOAuthService baseService = new LinkedInOAuthService(this);
            return baseService;
        }
    }

}
