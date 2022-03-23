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
        if (str.trim().isEmpty()) {
            return false;
        }
        return true; 
    }

    private static void check(final boolean requirement, final String error) {
        if (!requirement) {
            throw new IllegalArgumentException(hasText(error) ? error : DEFAULT_MESSAGE);
        }
    }

}
