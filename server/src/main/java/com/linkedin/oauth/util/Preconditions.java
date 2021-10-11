package com.linkedin.oauth.util;

/**
 * Utils for checking preconditions and invariants
 */
public abstract class Preconditions {

    private static final String DEFAULT_MESSAGE = "Received an invalid parameter";

    /**
     * Checks that an object is not null.
     *
     * @param object any object
     * @param errorMsg error message
     *
     * @throws IllegalArgumentException if the object is null
     */
    public static void checkNotNull(final Object object, final String errorMsg) {
        check(object != null, errorMsg);
    }

    /**
     * Checks that a string is not null or empty
     *
     * @param string any string
     * @param errorMsg error message
     *
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static void checkEmptyString(final String string, final String errorMsg) {
        check(hasText(string), errorMsg);
    }

    public static boolean hasText(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        final int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static void check(final boolean requirements, final String error) {
        if (!requirements) {
            throw new IllegalArgumentException(hasText(error) ? error : DEFAULT_MESSAGE);
        }
    }

}
