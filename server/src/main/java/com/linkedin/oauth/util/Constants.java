package com.linkedin.oauth.util;

public final class Constants {

    private Constants() {
        //private utility class constructor to forbid instantiation
    }

    public static final String AUTHORIZE_URL = "https://www.linkedin.com/oauth/v2/authorization";

    public static final String REQUEST_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";

    public static final String TOKEN = "token";

    public static final String CLIENT_SECRET = "client_secret";

    public static final String CLIENT_ID = "client_id";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String CODE = "code";

    public static final String REDIRECT_URI = "redirect_uri";

    public static final String GRANT_TYPE = "grant_type";

    public static final String TOKEN_INTROSPECTION_URL = "https://www.linkedin.com/oauth/v2/introspectToken";

    public static final int RESPONSE_CODE = 200;

    public static final int PORT = 8000;

    public enum GrantType {
        CLIENT_CREDENTIALS("client_credentials"), AUTHORIZATION_CODE("authorization_code"), REFRESH_TOKEN("refresh_token");
        private String grantType;

        GrantType(final String grantType) {
            this.grantType = grantType;
        }

        public String getGrantType() {
            return grantType;
        }
    }
}
