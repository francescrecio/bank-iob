package com.iobuilder.transfer.infrastructure;

import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferRepository;
import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class H2TransferRepository  implements TransferRepository, PanacheRepositoryBase<Transfer, String> {

    @Override
    public Transfer create(Transfer transfer) {
        persistAndFlush(transfer);
        return transfer;
    }

    @Override
    public List<Transfer> findByWalletId(String walletId) {
        return find("wallet_origin = :walletId or wallet_destination = :walletId order by date_at desc",
                Parameters.with("walletId", walletId)).list();
    }
}
