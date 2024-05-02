package com.ddabong.ddabongdotchiBE.domain.auth.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN

	@CreationTimestamp
	private Timestamp createDate;

	public List<String> getRoleList() {
		if(!this.role.isEmpty()) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
}
