package com.EnterprisePlatform.exception;

public class MasterDataCorruptionException extends MasterDataException{

    public MasterDataCorruptionException (String type, String code){
        super("Multiple active records found for: " + type + "[ " + code+"]");
    }
}
