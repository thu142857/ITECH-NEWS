package com.itechnews.service;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Status status = Status.SUCCESS;
    private Object message;
    private Object data;

    public enum Status {
        SUCCESS, FAILED
    }
}