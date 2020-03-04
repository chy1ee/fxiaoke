package com.chylee.fxiaoke.security.core.security;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BCryptPasswordEncoder implements PasswordEncoder {
    private Pattern BCRYPT_PATTERN;
    private final int strength;
    private final BCryptVersion version;
    private final SecureRandom random;

    public BCryptPasswordEncoder() {
        this(-1);
    }

    public BCryptPasswordEncoder(int strength) {
        this(strength, (SecureRandom)null);
    }

    public BCryptPasswordEncoder(BCryptVersion version) {
        this(version, (SecureRandom)null);
    }

    public BCryptPasswordEncoder(BCryptVersion version, SecureRandom random) {
        this(version, -1, random);
    }

    public BCryptPasswordEncoder(int strength, SecureRandom random) {
        this(BCryptVersion.$2A, strength, random);
    }

    public BCryptPasswordEncoder(BCryptVersion version, int strength) {
        this(version, strength, (SecureRandom)null);
    }

    public BCryptPasswordEncoder(BCryptVersion version, int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.version = version;
            this.strength = strength == -1 ? 10 : strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.random != null) {
            salt = BCrypt.gensalt(this.version.getVersion(), this.strength, this.random);
        } else {
            salt = BCrypt.gensalt(this.version.getVersion(), this.strength);
        }

        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                return false;
            } else {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            return false;
        }
    }

    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            Matcher matcher = this.BCRYPT_PATTERN.matcher(encodedPassword);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Encoded password does not look like BCrypt: " + encodedPassword);
            } else {
                int strength = Integer.parseInt(matcher.group(2));
                return strength < this.strength;
            }
        } else {
            return false;
        }
    }

    public static enum BCryptVersion {
        $2A("$2a"),
        $2Y("$2y"),
        $2B("$2b");

        private final String version;

        private BCryptVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return this.version;
        }
    }
}
