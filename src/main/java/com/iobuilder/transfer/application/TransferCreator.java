package com.iobuilder.transfer.application;

import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferRepository;
import com.iobuilder.transfer.domain.exception.InsufficientBalanceException;
import com.iobuilder.wallet.application.WalletUpdater;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class TransferCreator {

    @Inject
    TransferRepository transferRepository;

    @Inject
    WalletRepository walletRepository;

    @Inject
    WalletUpdater walletUpdater;

    @Transactional
    public TransferDTO create(TransferDTO transferDTO) {
        Transfer transfer = Transfer.builder()
                .transferType(transferDTO.getTransferType())
                .walletOrigin(transferDTO.getWalletOrigin())
                .walletDestination(transferDTO.getWalletDestination())
                .userOrigin(transferDTO.getUserOrigin())
                .userDestination(transferDTO.getUserDestination())
                .amount(transferDTO.getAmount())
                .build();

        /**
         * We make a find to lock the rows and avoid parallel actions in the wallets
         */
        Wallet walletOrigin = walletRepository.findById(transfer.getWalletOrigin());
        Wallet walletDestination = walletRepository.findById(transfer.getWalletDestination());

        if (transferDTO.getAmount() > walletOrigin.getBalance()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        transferRepository.create(transfer);

        walletOrigin.setBalance(walletOrigin.getBalance() - transferDTO.getAmount());
        walletDestination.setBalance(walletDestination.getBalance() + transferDTO.getAmount());

        walletUpdater.update(walletOrigin);
        walletUpdater.update(walletDestination);

        transferDTO.setId(transfer.getId());
        transferDTO.setDateAt(transfer.getDateAt());

        return transferDTO;
    }

    public TransferDTO createDeposit(TransferDTO transferDTO) {
        Transfer transfer = Transfer.builder()
                .transferType(transferDTO.getTransferType())
                .walletOrigin(transferDTO.getWalletOrigin())
                .walletDestination(transferDTO.getWalletDestination())
                .userOrigin(transferDTO.getUserOrigin())
                .userDestination(transferDTO.getUserDestination())
                .amount(transferDTO.getAmount())
                .build();

        transferRepository.create(transfer);

        transferDTO.setDateAt(transfer.getDateAt());

        return transferDTO;
    }
}
