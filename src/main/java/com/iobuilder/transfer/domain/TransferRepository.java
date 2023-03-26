package com.iobuilder.transfer.domain;

import java.util.List;

public interface TransferRepository {

    Transfer create(Transfer transfer);

    List<Transfer> findByWalletId(String walletId);
}
