package com.ddabong.ddabongdotchiBE.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.global.BaseEntity;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_username", nullable = false, unique = true)
	private String username;

	@Column(name = "user_password", nullable = false)
	private String password;

	@Column(name = "user_nickname", nullable = false)
	private String nickname;

	@Column(name = "user_description", nullable = false)
	private String description;

	@Column(name = "user_image_url")
	private String imageUrl;

	@Column(name = "user_roletype", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(name = "user_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@Builder.Default
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Card> cards = new ArrayList<>();

	public void deactivate() {
		this.userStatus = UserStatus.INACTIVE;
	}

	public void updatePassword(String password) {
		this.password = password == null ? this.password : password;
	}

	public void update(String nickname, String description, String imageUrl) {
		this.nickname = nickname == null ? this.nickname : nickname;
		this.description = description == null ? this.description : description;
		this.imageUrl = imageUrl == null ? this.imageUrl : imageUrl;
	}
}


