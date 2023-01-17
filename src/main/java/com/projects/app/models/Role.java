package com.projects.app.models;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	private String name;
}
