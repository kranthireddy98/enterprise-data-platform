package com.EnterprisePlatform.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionCallback <T>{

    T execute(Connection con) throws Exception;
}
