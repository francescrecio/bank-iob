package com.iobuilder.wallet.domain;

import com.iobuilder.user.domain.User;

public interface WalletRepository {

    Wallet create(Wallet wallet);

    Wallet findById(String id);

    int deposit(String walletId, Float amount);

}
