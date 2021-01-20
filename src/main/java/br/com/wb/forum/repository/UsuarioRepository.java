package br.com.wb.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wb.forum.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
