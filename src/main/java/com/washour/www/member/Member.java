package com.washour.www.member;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
@Table(name="member")
public class Member {
		@Id
	    @Column(name="member_id")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
		
		@Column(unique = true)
	    private String username;

	    @Column(unique = true)
	    private String email;

	    private String password;

	    private String address;


	
}
