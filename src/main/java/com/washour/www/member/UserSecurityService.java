package com.washour.www.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	
	// 로그인 서비스 
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
	
		// 로그인을 하기 위해 가입된 username을 조회하는 메서드
		Optional<Member> _member = this.memberRepository.findByUsername(username);
		
		if(_member.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		Member member = _member.get();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		// username이 admin 이면 ADMIN 권한 부여
		 if ("admin".equals(username)) {
		 authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		 } else {
		// 그 외의 모두 MEMBER 권한 부여
		 authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
		 }
		// 아이디, 비밀번호, 권한을 매개변수로 User에 담아 반환
		 return new User(member.getUsername(), member.getPassword(), authorities);
	}
}
