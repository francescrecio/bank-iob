package com.iobuilder.wallet.application;

import com.iobuilder.transfer.application.TransferDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    private String id;

    private String userId;

    private Float balance;

    private List<TransferDTO> transfers;
}
