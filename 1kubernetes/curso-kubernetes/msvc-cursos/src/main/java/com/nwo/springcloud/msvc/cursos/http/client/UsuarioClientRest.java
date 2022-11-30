package com.nwo.springcloud.msvc.cursos.http.client;

import com.nwo.springcloud.msvc.cursos.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios")
//@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UsuarioClientRest {

  @GetMapping("/{id}")
  Usuario detalle(@PathVariable("id") Long id);

  @PostMapping("/")
  Usuario crear(@RequestBody Usuario usuario);

  @GetMapping("/usuarios-curso")
  List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);

}
