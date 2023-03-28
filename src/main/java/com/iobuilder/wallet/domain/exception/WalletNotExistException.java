package com.iobuilder.wallet.domain.exception;

public class WalletNotExistException extends RuntimeException {

    public WalletNotExistException() {
        super();
    }

    public WalletNotExistException(String message) {
        super(message);
    }
}
