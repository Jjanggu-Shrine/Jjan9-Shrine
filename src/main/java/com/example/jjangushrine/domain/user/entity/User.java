package com.example.jjangushrine.domain.user.entity;

import java.time.LocalDateTime;

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
import org.hibernate.annotations.ColumnDefault;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.user.enums.UserRole;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String addressDetail;

	@Column(nullable = false)
	private String zipCode;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private final UserRole userRole = UserRole.USER;

	@Column
	@ColumnDefault("false")
	private Boolean isDeleted;

	@Column
	private LocalDateTime deletedAt;

	@Builder
	public User(
			Long id,
			String email,
			String password,
			String nickName,
			String phoneNumber,
			String address,
			String addressDetail,
			String zipCode
	) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.addressDetail = addressDetail;
		this.zipCode = zipCode;
	}

	public void softDelete() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}
}
