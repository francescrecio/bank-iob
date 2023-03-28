package com.iobuilder.wallet.application;

import com.iobuilder.user.application.UserDTO;
import com.iobuilder.user.application.UserFinder;
import com.iobuilder.wallet.domain.Wallet;
import com.iobuilder.wallet.domain.WalletRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
public class WalletCreatorTest {

    @InjectMock
    WalletRepository walletRepository;

    @InjectMock
    UserFinder userFinder;

    @Inject
    WalletCreator walletCreator;

    @Test
    void testCreateWallet_givenUsername_whenIsCorrect_thenReturnWallet() {
        Wallet walletExpected = Wallet.builder().userId("1111").build();
        UserDTO user = UserDTO.builder().id("1111").username("fran").build();

        when(walletRepository.create(Mockito.any())).thenReturn(walletExpected);
        when(userFinder.find(Mockito.any())).thenReturn(user);

        WalletDTO walletCreated = walletCreator.create("fran");

        assertThat(walletExpected.getUserId(), is(walletCreated.getUserId()));
    }

}
