package com.iobuilder.wallet.domain;

public interface WalletRepository {

    Wallet create(Wallet wallet);

    Wallet update(Wallet wallet);

    Wallet findById(String id);

    Wallet findByWalletIdUsername(String walletId, String username);

    int deposit(String walletId, Float amount);

    int withdraw(String walletId, Float amount);

}
