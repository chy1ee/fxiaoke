package com.chylee.fxiaoke.security.core.security;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {
    boolean matches(HttpServletRequest var1);

    default MatchResult matcher(HttpServletRequest request) {
        boolean match = this.matches(request);
        return new MatchResult(match, Collections.emptyMap());
    }

    public static class MatchResult {
        private final boolean match;
        private final Map<String, String> variables;

        MatchResult(boolean match, Map<String, String> variables) {
            this.match = match;
            this.variables = variables;
        }

        public boolean isMatch() {
            return this.match;
        }

        public Map<String, String> getVariables() {
            return this.variables;
        }

        public static MatchResult match() {
            return new MatchResult(true, Collections.emptyMap());
        }

        public static MatchResult match(Map<String, String> variables) {
            return new MatchResult(true, variables);
        }

        public static MatchResult notMatch() {
            return new MatchResult(false, Collections.emptyMap());
        }
    }
}