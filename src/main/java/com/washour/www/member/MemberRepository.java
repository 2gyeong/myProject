package com.washour.www.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// username 으로 검색
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findById(Long id);

}
