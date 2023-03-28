package com.iobuilder.wallet.application;

import com.iobuilder.user.domain.User;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WalletUpdater {

    @Inject
    WalletRepository walletRepository;

    public Wallet update(Wallet wallet) {
        walletRepository.update(wallet);

        return wallet;
    }
}
