package com.EnterprisePlatform.exception;

public class MasterDataInactiveException extends MasterDataException{

    public MasterDataInactiveException(String type, String code){
        super("Master data inactive or expired: " + type+ "[ " + code + "]");
    }
}
