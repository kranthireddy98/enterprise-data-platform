package com.EnterprisePlatform.master.exception;

public class MasterDataInactiveException extends MasterDataException{

    public MasterDataInactiveException(String type, String code){
        super("Master data inactive or expired: " + type+ "[ " + code + "]");
    }
}
