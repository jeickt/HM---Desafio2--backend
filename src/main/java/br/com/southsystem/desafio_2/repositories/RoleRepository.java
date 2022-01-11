package br.com.southsystem.desafio_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.desafio_2.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
