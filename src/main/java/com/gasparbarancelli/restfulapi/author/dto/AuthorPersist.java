package com.gasparbarancelli.restfulapi.author.dto;

import javax.validation.constraints.NotNull;

public class AuthorPersist {

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String linkedIn;

    private String faceBook;

    private String twitter;

    private String summary;

    @Deprecated
    public AuthorPersist() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getFaceBook() {
        return faceBook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getSummary() {
        return summary;
    }

}
