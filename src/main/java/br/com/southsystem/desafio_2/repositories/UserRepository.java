package br.com.southsystem.desafio_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.desafio_2.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByLogin(String login);
}
