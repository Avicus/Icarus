package net.avicus.icarus.module.conditions;

public enum Response {

    ALLOW,
    DENY,
    ABSTAIN;

    public static Response valueOf(boolean value) {
        return value ? ALLOW : DENY;
    }

}
