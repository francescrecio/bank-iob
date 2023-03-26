package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferCreator;
import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferEnum;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class WalletDeposit {

    @Inject
    WalletRepository walletRepository;

    @Inject
    TransferCreator transferCreator;

    @Transactional
    public void deposit(String walletId, Float amount) {
        walletRepository.deposit(walletId, amount);

        Wallet wallet = walletRepository.findById(walletId);

        //FIXME LOAD THE USERDESTINATION
        transferCreator.create(Transfer.builder()
                .transferType(TransferEnum.DEPOSIT.toString())
                .walletDestination(walletId)
                .userDestination(wallet.getUserId())
                .amount(amount)
                .build());
    }

}
