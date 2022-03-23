package com.example.api;

/**
 * Constants file
 */
public class Constants {
    public static final String LI_FIND_AD_ACCOUNTS_FOR_USER_ENDPOINT = "https://api.linkedin.com/v2/adAccountUsersV2?q=authenticatedUser&oauth2_access_token=";
    public static final String LI_FIND_USER_ROLES_ENDPOINT = "https://api.linkedin.com/v2/organizationAcls?q=roleAssignee&oauth2_access_token=";
    public static final String LI_ME_ENDPOINT = "https://api.linkedin.com/v2/me?oauth2_access_token=";
    public static final String TOKEN_INTROSPECTION_ERROR_MESSAGE = "Error introspecting token, service is not initiated";
    public static final String SAMPLE_APP_BASE = "java-sample-application";
    public static final String SAMPLE_APP_VERSION = "version 1.0";
    enum AppName {
        OAuth,
        Marketing;
    }
    public static final String USER_AGENT_OAUTH_VALUE = String.format("%s (%s, %s)", SAMPLE_APP_BASE, SAMPLE_APP_VERSION, AppName.OAuth.name());
    public static final String USER_AGENT_LMS_VALUE = String.format("%s (%s, %s)", SAMPLE_APP_BASE, SAMPLE_APP_VERSION, AppName.Marketing.name());

}
