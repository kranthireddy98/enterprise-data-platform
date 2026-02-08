package com.EnterprisePlatform.master.exception;

public class MasterDataNotFoundException extends MasterDataException{

    public MasterDataNotFoundException(String type, String code){
        super("Master data not fond: " + type + " [" + code + "]");
    }

}
