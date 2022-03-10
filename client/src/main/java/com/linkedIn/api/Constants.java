package com.linkedIn.api;

/**
 * Constants for use
 */
public class Constants {
    public static final String TOKEN_INTROSPECTION_ENDPOINT = "tokenIntrospection";
    public static final String THREE_LEGGED_TOKEN_GEN_ENDPOINT = "login";
    public static final String TWO_LEGGED_TOKEN_GEN_ENDPOINT = "twoLeggedAuth";
    public static final String USE_REFRESH_TOKEN_ENDPOINT = "refreshToken";
    public static final String FIND_AD_ACCOUNTS_ENDPOINT = "findAdAccounts";
    public static final String GET_USER_ORG_ACCESS_ENDPOINT = "getUserOrgAccess";
    public static final String PROFILE_ENDPOINT = "profile";
    public static final String OAUTH_PAGE = "index";
    public static final String LMS_PAGE = "marketingtemplate";
    public static final String GENERIC_ERROR_MESSAGE = "Error retrieving the data";
    public static final String TWO_LEGGED_TOKEN_GEN_SUCCESS_MESSAGE = "2-Legged OAuth token successfully generated via client credentials.";
    public static final String FIND_AD_ACCOUNTS_MESSAGE = "Find Ad Accounts by Authenticated User:- ";
    public static final String FIND_USER_ROLES_MESSAGE = "Find Ad Account roles of Authenticated User:- ";
    public static final String TOKEN_EXISTS_MESSAGE = "Access Token is ready to use!";
    public static final String ACTION_2_LEGGED_TOKEN_GEN = "Generating 2-legged auth access token...";
    public static final String ACTION_GET_PROFILE = "Getting public profile...";
    public static final String ACTION_USE_REFRESH_TOKEN = "Refreshing token...";
    public static final String ACTION_TOKEN_INTROSPECTION = "Performing token introspection...";
    public static final String CASE_TWO_LEGGED_TOKEN_GEN = "two_legged_auth=2+Legged+OAuth";
    public static final String CASE_GET_PROFILE = "profile=Get+Profile";
    public static final String CASE_USE_REFRESH_TOKEN = "refresh_token=Use+Refresh+Token";
    public static final String CASE_TOKEN_INTROSPECTION = "token_introspection=Token+Introspection";
    public static final String CASE_FIND_AD_ACCOUNTS = "Find_ad_account=Find+Ad+Accounts";
    public static final String CASE_GET_USER_ORG_ROLES = "Get_user_org_access=Find+Org+Access";
    public static final String DEFAULT_MESSAGE = "No API calls made!";
}
