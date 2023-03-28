package com.iobuilder.wallet.application;

import com.iobuilder.user.application.UserDTO;
import com.iobuilder.user.application.UserFinder;
import com.iobuilder.user.domain.User;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class WalletCreator {

    @Inject
    WalletRepository walletRepository;

    @Inject
    UserFinder userFinder;

    @Transactional
    public WalletDTO create(String userName) {
        UserDTO user = userFinder.find(userName);

        Wallet wallet = walletRepository.create(Wallet.builder()
                        .balance(0f)
                        .userId(user.getId())
                .build());

        return WalletDTO.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .balance(wallet.getBalance())
                .build();
    }

}
