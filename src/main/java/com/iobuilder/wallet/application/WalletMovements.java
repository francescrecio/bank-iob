package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferDTO;
import com.iobuilder.transfer.application.TransferFinder;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class WalletMovements {

    @Inject
    WalletRepository walletRepository;

    @Inject
    TransferFinder transferFinder;

    @Transactional
    public WalletDTO movements(String walletId) {
        Wallet wallet = walletRepository.findById(walletId);

        List<TransferDTO> transferDTOList = transferFinder.findByWalletId(walletId);

       return WalletDTO.builder()
               .balance(wallet.getBalance())
               .id(wallet.getId())
               .userId(wallet.getUserId())
               .transfers(transferDTOList)
               .build();
    }
}
