package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferCreator;
import com.iobuilder.transfer.application.TransferDTO;
import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferType;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import com.iobuilder.wallet.domain.exception.WalletNotExistException;

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
        Wallet wallet = walletRepository.findById(walletId);

        if (wallet == null) {
            throw new WalletNotExistException("Wallet not exists");
        }

        walletRepository.deposit(walletId, amount);

        transferCreator.createDeposit(TransferDTO.builder()
                .transferType(TransferType.DEPOSIT.toString())
                .walletDestination(walletId)
                .userDestination(wallet.getUserId())
                .amount(amount)
                .build());
    }

}
