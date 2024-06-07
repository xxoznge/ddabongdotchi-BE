package com.ddabong.ddabongdotchiBE.domain.card.entity;

import java.util.ArrayList;
import java.util.List;

import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;
import com.ddabong.ddabongdotchiBE.domain.global.BaseEntity;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cards")
public class Card extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private Long id;

	@Column(name = "card_title", nullable = false)
	private String title;

	@Column(name = "card_mood", nullable = false)
	private String mood;

	@Column(name = "card_content", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "card_type", nullable = false)
	private FortuneType type;

	@Column(name = "card_image_url")
	private String imageUrl;

	@Column(name = "comment_count", nullable = false)
	private long commentCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Builder.Default
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void increaseCount() {
		this.commentCount++;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
