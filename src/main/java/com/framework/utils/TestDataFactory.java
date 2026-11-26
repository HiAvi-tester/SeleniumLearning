package com.framework.utils;

import com.framework.model.TestUser;

import java.util.UUID;

/** Generates unique data so parallel tests never collide on the same account. */
public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static TestUser newUser() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new TestUser("QA User " + id, "qa.auto." + id + "@example.com", "Pass@" + id);
    }
}
