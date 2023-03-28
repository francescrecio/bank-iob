package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferCreator;
import com.iobuilder.transfer.application.TransferDTO;
import com.iobuilder.transfer.domain.TransferType;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class WalletWithdraw {

    @Inject
    WalletRepository walletRepository;

    @Inject
    TransferCreator transferCreator;

    @Transactional
    public void withdraw(String walletId, Float amount) {
        walletRepository.withdraw(walletId, amount);

        Wallet wallet = walletRepository.findById(walletId);
        
        transferCreator.create(TransferDTO.builder()
                .transferType(TransferType.WITHDRAWAL.toString())
                .walletDestination(walletId)
                .userDestination(wallet.getUserId())
                .amount(amount)
                .build());
    }

}
