package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferCreator;
import com.iobuilder.transfer.application.TransferDTO;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import com.iobuilder.wallet.domain.exception.WalletNotExistException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
public class WalletDepositTest {

    @InjectMock
    WalletRepository walletRepository;

    @InjectMock
    TransferCreator transferCreator;

    @Inject
    WalletDeposit walletDeposit;

    @Test
    void testDepositWallet_givenWalletAndAmount_whenCanDeposit_thenNoException() {
        Wallet walletExpected = Wallet.builder().id("2222").userId("1111").build();
        TransferDTO transferDTO = TransferDTO.builder().amount(20f).build();

        when(walletRepository.findById(Mockito.any())).thenReturn(walletExpected);
        when(transferCreator.createDeposit(transferDTO)).thenReturn(transferDTO);

        walletDeposit.deposit("2222", 20f);
    }

    @Test
    void testDepositWallet_givenWalletAndAmount_whenWalletNotExists_thenException() {
        when(walletRepository.findById(Mockito.any())).thenReturn(null);

        try {
            walletDeposit.deposit("2222", 20f);
        } catch (WalletNotExistException walletNotExistException) {
            assertThat(walletNotExistException.getMessage(), is("Wallet not exists"));
        }
    }

}
