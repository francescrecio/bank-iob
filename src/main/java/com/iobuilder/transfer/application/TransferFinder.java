package com.iobuilder.transfer.application;

import com.iobuilder.transfer.domain.TransferRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransferFinder {

    @Inject
    TransferRepository transferRepository;

    public List<TransferDTO> findByWalletId(String walletId) {
        List<TransferDTO> transferDTOList = transferRepository
                .findByWalletId(walletId)
                .stream()
                .map(t -> TransferDTO.builder()
                        .id(t.getId())
                        .transferType(t.getTransferType())
                        .walletOrigin(t.getWalletOrigin())
                        .walletDestination(t.getWalletDestination())
                        .userOrigin(t.getUserOrigin())
                        .userDestination(t.getUserDestination())
                        .amount(t.getAmount())
                        .dateAt(t.getDateAt())
                        .build()).collect(Collectors.toList());

        return transferDTOList;
    }

}
