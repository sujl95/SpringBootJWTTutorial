package me.thewing.jwttutorial.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import me.thewing.jwttutorial.dto.UserDto;
import me.thewing.jwttutorial.entity.Authority;
import me.thewing.jwttutorial.entity.User;
import me.thewing.jwttutorial.repository.UserRepository;
import me.thewing.jwttutorial.util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User signup(UserDto userDto) {
		if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}

		//빌더 패턴의 장점
		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();

		User user = User.builder()
				.username(userDto.getUsername())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.nickname(userDto.getNickname())
				.authorities(Collections.singleton(authority))
				.activated(true)
				.build();

		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(String username) {
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}
}