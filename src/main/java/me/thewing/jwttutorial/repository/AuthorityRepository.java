package me.thewing.jwttutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.thewing.jwttutorial.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
