/**
 * Role.java
 * This enum defines the user roles in the system
 */
package com.achievement.constant;

public enum Role {
    STUDENT("学生"),
    TEACHER("教师"),
    ADMIN("管理员");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static Role fromName(String name) {
        for (Role role : Role.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + name);
    }
}