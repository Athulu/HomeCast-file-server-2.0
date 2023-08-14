package com.example.homecastfileserver.exceptions;

public class KremowkaException extends NumberFormatException{
    public KremowkaException(String janPawelDrugiMessage) {
        super(janPawelDrugiMessage);
    }

    @Override
    public String getMessage() {
        return "KremówkaException (extendsNumberFormatException) dla: " + super.getMessage();
    }
}
