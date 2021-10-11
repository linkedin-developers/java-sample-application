package com.linkedin.oauth.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder for LinkedIn OAuth 2.0 scopes.
 */

public final class ScopeBuilder {

    private final Set<String> scopes = new HashSet<>();

    /**
     * public constructor
     * @param scope that needs to be requested
     */
    public ScopeBuilder(final String scope) {
        withScope(scope);
    }

    /**
     * public constructor
     * @param scopes array of scopes to be requested
     */
    public ScopeBuilder(final String... scopes) {
        withScopes(scopes);
    }

    /**
     * public constructor
     * @param scopes collection of scopes to be requested
     */
    public ScopeBuilder(final Collection<String> scopes) {
        withScopes(scopes);
    }

    /**
     * Setter for a single scope
     */
    public ScopeBuilder withScope(final String scope) {
        scopes.add(scope);
        return this;
    }

    /**
     * Setter for an array of scopes
     */
    public ScopeBuilder withScopes(final String... scopes) {
        this.scopes.addAll(Arrays.asList(scopes));
        return this;
    }

    /**
     * Setter for setting a collection of scopes
     */
    public ScopeBuilder withScopes(final Collection<String> scopes) {
        this.scopes.addAll(scopes);
        return this;
    }

    /**
     * builds all the scopes set into a single String for requesting
     */
    public String build() {
        final StringBuilder scopeBuilder = new StringBuilder();
        for (String scope : scopes) {
            scopeBuilder.append(' ').append(scope);
        }
        return scopeBuilder.substring(1);
    }
}
