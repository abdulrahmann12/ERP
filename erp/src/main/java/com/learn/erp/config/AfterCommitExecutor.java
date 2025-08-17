package com.learn.erp.config;

import org.springframework.transaction.support.TransactionSynchronization;

public abstract class AfterCommitExecutor implements TransactionSynchronization {
    @Override
    public void suspend() {}
    @Override
    public void resume() {}
    @Override
    public void flush() {}
    @Override
    public void beforeCommit(boolean readOnly) {}
    @Override
    public void beforeCompletion() {}
    @Override
    public void afterCompletion(int status) {}

    @Override
    public abstract void afterCommit();
}
