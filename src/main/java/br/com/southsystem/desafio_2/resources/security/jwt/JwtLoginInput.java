package br.com.southsystem.desafio_2.resources.security.jwt;

import lombok.Data;

@Data
class JwtLoginInput {

	private String username;
    private String password;
    
}