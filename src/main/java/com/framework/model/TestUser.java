package com.framework.model;

/** Immutable test account created through the API before a UI test. */
public record TestUser(String name, String email, String password) {
}
