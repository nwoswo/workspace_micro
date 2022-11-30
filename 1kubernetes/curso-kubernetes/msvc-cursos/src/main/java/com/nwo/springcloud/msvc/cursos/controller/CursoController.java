package com.nwo.springcloud.msvc.cursos.controller;

import com.nwo.springcloud.msvc.cursos.model.Usuario;
import com.nwo.springcloud.msvc.cursos.model.dto.CursoDto;
import com.nwo.springcloud.msvc.cursos.model.entity.Curso;
import com.nwo.springcloud.msvc.cursos.services.CursoService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

  @Autowired
  private CursoService service;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<List<Curso>> listar(){
    return ResponseEntity.ok(service.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable Long id){
    Optional<Curso> oc = service.porIdConUsuarios(id);
    return oc.isPresent() ? ResponseEntity.ok(oc.get()) : ResponseEntity.notFound().build();
  }

  @PostMapping("/")
  public ResponseEntity<?> crear(@Valid @RequestBody CursoDto cursoDto, BindingResult result){
    if(result.hasErrors())  return validar(result);

    Curso cursoDb = service.guardar(modelMapper.map(cursoDto, Curso.class));
    return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@Valid @RequestBody CursoDto cursoDto,BindingResult result, @PathVariable Long id){
    if(result.hasErrors())  return validar(result);

    modelMapper.typeMap(CursoDto.class, Curso.class).addMappings(mapper -> mapper.skip(Curso::setId));

    Optional<Curso> oc = service.porId(id);
    if(oc.isPresent()){
      Curso cursoDB = oc.get();
      cursoDB = modelMapper.map(cursoDto, Curso.class);
      cursoDB.setId(oc.get().getId());

      return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDB));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id){
    Optional<Curso> oc = service.porId(id);
    if (oc.isPresent())  {
      service.eliminar(oc.get().getId()); return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/asignar-usuario/{cursoId}")
  public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> ou;

    try {
      ou = service.asignarUsuario(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("mensaje", String.format("Error de comunicacion : %s", e.getMessage())));
    }

    if(ou.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(ou.get());
    }

    return ResponseEntity.notFound().build();

  }

  @PostMapping("/crear-usuario/{cursoId}")
  public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> ou;

    try {
      ou = service.crearUsuario(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("mensaje", String.format("Problema de comunicacion: %s", e.getMessage())));
    }

    if(ou.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(ou.get());
    }

    return ResponseEntity.notFound().build();

  }


  @DeleteMapping("/eliminar-usuario/{cursoId}")
  public ResponseEntity<?> aeliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> ou;

    try {
      ou = service.eliminarUsuario(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("mensaje", String.format("No Existe el usuario por el Id : %s", e.getMessage())));
    }

    if(ou.isPresent()){
      return ResponseEntity.status(HttpStatus.OK).body(ou.get());
    }

    return ResponseEntity.notFound().build();

  }

  @DeleteMapping("/eliminar-curso-usuario/{id}")
  public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
    service.elminarCursoUsuarioPorId(id);
    return ResponseEntity.noContent().build();
  }



  private ResponseEntity<Map<String, String>> validar(BindingResult result) {
    Map<String, String> errores = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errores.put(err.getField(), String.format("El campo %s %s",err.getField(), err.getDefaultMessage() ));
    });

    return ResponseEntity.badRequest().body(errores);
  }



}
