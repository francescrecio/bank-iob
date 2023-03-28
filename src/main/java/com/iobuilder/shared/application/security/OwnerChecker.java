package com.iobuilder.shared.application.security;

import com.iobuilder.wallet.application.WalletFinder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OwnerChecker {

    @Inject
    WalletFinder walletFinder;

    public void checkIsTheOwner(String walletId, String userName) {
        walletFinder.findByUsername(walletId, userName);
    }

}
