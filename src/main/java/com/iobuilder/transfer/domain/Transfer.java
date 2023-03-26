package com.iobuilder.transfer.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "transfer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "user_origin")
    private String userOrigin;

    @Column(name = "user_destination")
    private String userDestination;

    @Column(name = "wallet_origin")
    private String walletOrigin;

    @Column(name = "wallet_destination")
    private String walletDestination;

    @Column(name = "transfer_type")
    private String transferType;

    @Column(name = "amount")
    private Float amount;

    @CreationTimestamp
    @Column(name = "date_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateAt;
}
