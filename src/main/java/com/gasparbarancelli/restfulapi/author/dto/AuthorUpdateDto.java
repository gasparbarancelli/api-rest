package com.gasparbarancelli.restfulapi.author.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;

public class AuthorUpdateDto {

    @NotNull
    private final JsonNullable<String> name = JsonNullable.undefined();
    @NotNull
    private final JsonNullable<String> email = JsonNullable.undefined();
    private final JsonNullable<String> linkedIn = JsonNullable.undefined();
    private final JsonNullable<String> faceBook = JsonNullable.undefined();
    private final JsonNullable<String> twitter = JsonNullable.undefined();
    private final JsonNullable<String> summary = JsonNullable.undefined();

    @Deprecated
    public AuthorUpdateDto() {
    }

    public JsonNullable<String> getName() {
        return name;
    }

    public JsonNullable<String> getEmail() {
        return email;
    }

    public JsonNullable<String> getLinkedIn() {
        return linkedIn;
    }

    public JsonNullable<String> getFaceBook() {
        return faceBook;
    }

    public JsonNullable<String> getTwitter() {
        return twitter;
    }

    public JsonNullable<String> getSummary() {
        return summary;
    }
}
