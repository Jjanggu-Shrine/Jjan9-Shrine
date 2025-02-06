package com.example.jjangushrine.domain.address.entity;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.address.dto.request.AddressUpdateReq;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDefault = false;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    @Builder
    public Address(
            Long id,
            User user,
            String recipientName,
            String addressName,
            String address,
            String addressDetail,
            String zipCode
    ) {
        this.id = id;
        this.user = user;
        this.recipientName = recipientName;
        this.addressName = addressName;
        this.address = address;
        this.addressDetail =addressDetail;
        this.zipCode = zipCode;
    }

    public void update(AddressUpdateReq updateReq) {
        Optional.ofNullable(updateReq.recipientName()).ifPresent(value -> this.recipientName = value);
        Optional.ofNullable(updateReq.addressName()).ifPresent(value -> this.addressName = value);
        Optional.ofNullable(updateReq.address()).ifPresent(value -> this.address = value);
        Optional.ofNullable(updateReq.addressDetail()).ifPresent(value -> this.addressDetail = value);
        Optional.ofNullable(updateReq.zipCode()).ifPresent(value -> this.zipCode = value);
    }

    public void setDefault() {
        this.isDefault = true;
    }

    public void softDelete() {
        if (!this.isDeleted) {
            this.isDeleted = true;
            this.isDefault = false;
            this.deletedAt = LocalDateTime.now();
        } else {
            throw new ConflictException(ErrorCode.DUPLICATE_ADDRESS_DELETE);
        }
    }
}
