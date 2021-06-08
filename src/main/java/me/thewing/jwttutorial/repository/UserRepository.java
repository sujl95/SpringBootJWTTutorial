package me.thewing.jwttutorial.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import me.thewing.jwttutorial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUsername(String username);
}
