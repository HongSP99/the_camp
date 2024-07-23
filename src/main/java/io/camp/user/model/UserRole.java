package io.camp.user.model;

public enum UserRole {
    USER("ROLE_USER","일반 사용자"), ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;

    UserRole(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
