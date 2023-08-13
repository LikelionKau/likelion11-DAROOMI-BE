package com.likelion.daroomi.nuroomi.exception;

public class UserNotFoundException extends NullPointerException {

    public UserNotFoundException() {
        super("유저가 없습니다.");
    }

}
