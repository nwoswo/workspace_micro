package com.nwo.springcloud.msvc.usuarios.repositories;

import com.nwo.springcloud.msvc.usuarios.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

  Optional<Usuario> findByEmail(String email);
  boolean existsByEmail(String email);
}
