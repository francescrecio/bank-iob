package com.iobuilder.wallet.infrastructure;

import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;
import java.util.List;

@ApplicationScoped
public class H2WalletRepository implements WalletRepository, PanacheRepositoryBase<Wallet, String> {

    @Override
    public Wallet create(Wallet wallet) {
        persistAndFlush(wallet);
        return wallet;
    }

    @Override
    public Wallet update(Wallet wallet) {
        persistAndFlush(wallet);
        return wallet;
    }

    @Override
    public Wallet findById(String id) {
        return findById(id, LockModeType.PESSIMISTIC_READ);
    }

    @Override
    public Wallet findByWalletIdUsername(String walletId, String username) {
        List<Wallet> walletList = getEntityManager().createQuery(
                        "Select w from wallet w inner join customer c on c.id = w.userId where c.username = :username and w.id = :walletId")
                .setParameter("username", username)
                .setParameter("walletId", walletId)
                .getResultList();

        return walletList.size() == 0 ? null : walletList.get(0);
    }

    @Override
    public int deposit(String walletId, Float amount) {
        return update("balance = balance + :amount WHERE id = :walletId",
                Parameters.with("amount", amount).and("walletId", walletId));
    }

    @Override
    public int withdraw(String walletId, Float amount) {
        return update("balance = balance - :amount WHERE id = :walletId",
                Parameters.with("amount", amount).and("walletId", walletId));
    }
}
