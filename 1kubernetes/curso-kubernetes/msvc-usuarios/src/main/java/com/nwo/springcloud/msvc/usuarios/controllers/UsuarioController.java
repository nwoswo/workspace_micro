package com.nwo.springcloud.msvc.usuarios.controllers;

import com.nwo.springcloud.msvc.usuarios.model.dto.UsuarioDto;
import com.nwo.springcloud.msvc.usuarios.model.entity.Usuario;
import com.nwo.springcloud.msvc.usuarios.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private Environment env;

  @GetMapping("/crash")
  public void crash(){
    ((ConfigurableApplicationContext)context).close();
  }

  @GetMapping("/")
  public ResponseEntity<?> listar(){
    Map<String, Object> body = new HashMap<>();
    body.put("users", service.listar());
    body.put("podinfo", env.getProperty("MY_POD_NAME"));
    body.put("podIp", env.getProperty("MY_POD_IP"));
    body.put("texto", env.getProperty("config.texto"));
    return ResponseEntity.ok(body);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable Long id){
    Optional<Usuario> uo = service.porId(id);
    return uo.isPresent()
      ? ResponseEntity.ok(uo.get())
      : ResponseEntity.notFound().build();

  }

  @PostMapping
  public ResponseEntity<?> crear(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result){

    if(!usuarioDto.getEmail().isEmpty() && service.existePorEmail(usuarioDto.getEmail()))
      return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));

    if(result.hasErrors())  return validar(result);

    return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(modelMapper.map(usuarioDto, Usuario.class))) ;
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result, @PathVariable Long id){



    if(result.hasErrors())  return validar(result);

    Optional<Usuario> uo = service.porId(id);
    modelMapper.typeMap(UsuarioDto.class, Usuario.class).addMappings(mapper -> mapper.skip(Usuario::setId));

    if(uo.isPresent()) {
      Usuario usuario_db = uo.get();

      if(!usuarioDto.getEmail().isEmpty() &&
        !usuarioDto.getEmail().equalsIgnoreCase(usuario_db.getEmail()) &&
        service.existePorEmail(usuarioDto.getEmail()))

        return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));

      usuario_db = modelMapper.map(usuarioDto, Usuario.class);
      usuario_db.setId(uo.get().getId());

      return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario_db));
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id){
    Optional<Usuario> ou = service.porId(id);
    if(ou.isPresent()) {
      service.eliminar(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/usuarios-curso")
  public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
    return ResponseEntity.ok(service.listarPorIds(ids));
  }


  private ResponseEntity<Map<String, String>> validar(BindingResult result) {
    Map<String, String> errores = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errores.put(err.getField(), String.format("El campo %s %s",err.getField(), err.getDefaultMessage() ));
    });

    return ResponseEntity.badRequest().body(errores);
  }
}
