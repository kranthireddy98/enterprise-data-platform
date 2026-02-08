package com.EnterprisePlatform.repository.support;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionCallback<T> {
    T doInTransaction(Connection con) throws Exception;
}
