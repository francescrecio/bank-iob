package com.iobuilder.transfer.application;

import com.iobuilder.transfer.domain.Transfer;
import com.iobuilder.transfer.domain.TransferType;
import com.iobuilder.transfer.domain.exception.InsufficientBalanceException;
import com.iobuilder.wallet.application.WalletUpdater;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import static org.mockito.Mockito.when;

@QuarkusTest
public class TransferCreatorTest {

    @InjectMock
    WalletRepository walletRepository;

    @InjectMock
    WalletUpdater walletUpdater;

    @InjectSpy
    TransferCreator transferCreator;

    @Test
    void testCreateTransfer_givenTransfer_whenIsCorrect_thenReturnTransfer() {
        TransferDTO transferDTO = TransferDTO.builder()
                .transferType(TransferType.TRANSFER.toString())
                .amount(20f)
                .walletOrigin("1111")
                .walletDestination("2222")
                .userOrigin("1111")
                .userDestination("1111")
                .build();

        Transfer transfer = Transfer.builder()
                .id("2222")
                .transferType(TransferType.TRANSFER.toString())
                .amount(20f)
                .walletOrigin("1111")
                .walletDestination("2222")
                .userOrigin("1111")
                .userDestination("1111")
                .build();

        Wallet walletOrigin = Wallet.builder().id("1111").balance(10000f).userId("1111").build();
        Wallet walletDestination = Wallet.builder().id("2222").balance(10000f).userId("1111").build();

        when(walletRepository.findById(Mockito.any())).thenReturn(walletOrigin);
        when(walletRepository.findById(Mockito.any())).thenReturn(walletDestination);

        var transferCreatorSpy = spy(transferCreator);
        doReturn(TransferDTO.builder().build()).when(transferCreator).create(any(TransferDTO.class));

        when(walletUpdater.update(Mockito.any())).thenReturn(walletOrigin);
        when(walletUpdater.update(Mockito.any())).thenReturn(walletDestination);

        var responseWalletTransferenceDTO = transferCreatorSpy.create(transferDTO);
        assertThat(transferDTO, is(responseWalletTransferenceDTO));
    }

    @Test
    void testCreateTransfer_givenTransfer_whenInsufficientBalance_thenReturnException() {
        TransferDTO transferDTO = TransferDTO.builder()
                .transferType(TransferType.TRANSFER.toString())
                .amount(20f)
                .walletOrigin("1111")
                .walletDestination("2222")
                .userOrigin("1111")
                .userDestination("1111")
                .build();

        Transfer transfer = Transfer.builder()
                .id("2222")
                .transferType(TransferType.TRANSFER.toString())
                .amount(20f)
                .walletOrigin("1111")
                .walletDestination("2222")
                .userOrigin("1111")
                .userDestination("1111")
                .build();

        Wallet walletOrigin = Wallet.builder().id("1111").balance(1f).userId("1111").build();
        Wallet walletDestination = Wallet.builder().id("2222").balance(1f).userId("1111").build();

        when(walletRepository.findById(Mockito.any())).thenReturn(walletOrigin);
        when(walletRepository.findById(Mockito.any())).thenReturn(walletDestination);

        try {
            doReturn(TransferDTO.builder().build()).when(transferCreator).create(any(TransferDTO.class));
        } catch (InsufficientBalanceException insufficientBalanceException) {
            assertThat(insufficientBalanceException.getMessage(), is("Insufficient balance"));
        }
    }
}
