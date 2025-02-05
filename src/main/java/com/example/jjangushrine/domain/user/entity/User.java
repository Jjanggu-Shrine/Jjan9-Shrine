package com.example.jjangushrine.domain.user.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.jjangushrine.domain.user.dto.request.UserUpdateReq;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
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

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private final UserRole userRole = UserRole.USER;

	@Column(nullable = false)
	@ColumnDefault("false")
	private Boolean isDeleted = false;

	@Column
	private LocalDateTime deletedAt;

	@Builder
	public User(
			Long id,
			String email,
			String password,
			String nickName,
			String phoneNumber
	) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
	}

	public void update(UserUpdateReq updateReq) {
		Optional.ofNullable(updateReq.password()).ifPresent(value -> this.password = value);
		Optional.ofNullable(updateReq.nickName()).ifPresent(value -> this.nickName = value);
		Optional.ofNullable(updateReq.phoneNumber()).ifPresent(value -> this.phoneNumber = value);
	}

	public void softDelete() {
		if (!this.isDeleted) {
			this.isDeleted = true;
			this.deletedAt = LocalDateTime.now();
		} else {
			throw new ConflictException(ErrorCode.DUPLICATE_USER_DELETE);
		}
	}
}
