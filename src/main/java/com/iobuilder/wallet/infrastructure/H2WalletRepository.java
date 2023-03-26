package com.iobuilder.wallet.infrastructure;

import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;

@ApplicationScoped
public class H2WalletRepository implements WalletRepository, PanacheRepositoryBase<Wallet, String> {

    @Override
    public Wallet create(Wallet wallet) {
        persistAndFlush(wallet);
        return wallet;
    }

    @Override
    public Wallet findById(String id) {
        return findById(id, LockModeType.PESSIMISTIC_READ);
    }

    @Override
    public int deposit(String walletId, Float amount) {
        return update("balance = balance + :amount WHERE id = :walletId",
                Parameters.with("amount", amount).and("walletId", walletId));
    }
}
