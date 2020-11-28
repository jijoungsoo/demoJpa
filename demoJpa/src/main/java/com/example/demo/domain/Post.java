package com.example.demo.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Post {
	@Id
	@GeneratedValue
	int id;
	
	
	@Column(length = 1000)
	String content;
}