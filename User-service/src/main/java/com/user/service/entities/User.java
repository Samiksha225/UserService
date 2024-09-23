package com.user.service.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name="micro_user")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
	private int userId;

@Column(name="name",length=20)
	private String name;

@Column(name="email")
	private String email;

@Column(name="about",length=50)
	private String about;

@Transient
private List<Rating>ratings = new ArrayList<>();

}
