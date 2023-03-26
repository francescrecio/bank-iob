package com.iobuilder.transfer.application;

import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferRepository;
import com.iobuilder.user.domain.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TransferCreator {

    @Inject
    TransferRepository transferRepository;

    public Transfer create(Transfer transfer) {
        return transferRepository.create(transfer);
    }
}
