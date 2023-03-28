package com.iobuilder.wallet.application;

import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import com.iobuilder.wallet.domain.exception.WalletNotOwnedUserException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WalletFinder {

    @Inject
    WalletRepository walletRepository;

    public void findByUsername(String walletId, String username) {
        Wallet wallet = walletRepository.findByWalletIdUsername(walletId, username);
        if (wallet == null) {
            throw new WalletNotOwnedUserException();
        }
    }

}
