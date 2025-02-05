package com.example.jjangushrine.domain.seller.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.jjangushrine.domain.seller.dto.request.SellerUpdateReq;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import org.hibernate.annotations.ColumnDefault;
import com.example.jjangushrine.domain.user.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sellers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String representativeName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final UserRole userRole = UserRole.SELLER;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    @Builder
    public Seller(
            Long id,
            String email,
            String password,
            String representativeName,
            String phoneNumber
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.representativeName = representativeName;
        this.phoneNumber = phoneNumber;
    }

    public void update(SellerUpdateReq updateReq) {
        Optional.ofNullable(updateReq.password()).ifPresent(
                value -> this.password = value);
        Optional.ofNullable(updateReq.representativeName()).ifPresent(
                value -> this.representativeName = value);
        Optional.ofNullable(updateReq.phoneNumber()).ifPresent(
                value -> this.phoneNumber = value);
    }

    public void softDelete() {
        if (!this.isDeleted) {
            this.isDeleted = true;
            this.deletedAt = LocalDateTime.now();
        } else {
            throw new ConflictException(ErrorCode.DUPLICATE_SELLER_DELETE);
        }
    }
}
