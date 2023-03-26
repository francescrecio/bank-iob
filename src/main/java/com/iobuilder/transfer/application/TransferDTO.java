package com.iobuilder.transfer.application;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private String id;

    private String userOrigin;

    private String userDestination;

    private String walletOrigin;

    private String walletDestination;

    private String transferType;

    private Float amount;

    private LocalDateTime dateAt;
}
