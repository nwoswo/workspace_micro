package com.nwo.springcloud.msvc.cursos.services;

import com.nwo.springcloud.msvc.cursos.http.client.UsuarioClientRest;
import com.nwo.springcloud.msvc.cursos.model.Usuario;
import com.nwo.springcloud.msvc.cursos.model.entity.Curso;
import com.nwo.springcloud.msvc.cursos.model.entity.CursoUsuario;
import com.nwo.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements  CursoService {

  @Autowired
  private CursoRepository repository;

  @Autowired
  private UsuarioClientRest usuarioClientRest;

  @Override
  public List<Curso> listar() {
    return (List<Curso>) repository.findAll();
  }

  @Override
  public Optional<Curso> porId(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Curso guardar(Curso curso) {
    return repository.save(curso);
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void elminarCursoUsuarioPorId(Long id) {
    repository.eliminarCursoUsuarioPorId(id);
  }

  @Override
  @Transactional
  public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

    Optional<Curso> oc = repository.findById(cursoId);

    if(oc.isPresent()){
      Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

      Curso curso = oc.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());

      curso.addCursoUsuario(cursoUsuario);
      repository.save(curso);
      return Optional.of(usuarioMsvc);
    }

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
    Optional<Curso> oc = repository.findById(cursoId);

    if(oc.isPresent()){
      Usuario usuarioMsvc = usuarioClientRest.crear(usuario);

      Curso curso = oc.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());

      curso.addCursoUsuario(cursoUsuario);
      repository.save(curso);
      return Optional.of(usuarioMsvc);
    }

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
    Optional<Curso> oc = repository.findById(cursoId);

    if(oc.isPresent()){
      Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

      Curso curso = oc.get();
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());

      curso.removeCursoUsuario(cursoUsuario);
      repository.save(curso);
      return Optional.of(usuarioMsvc);
    }

    return Optional.empty();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> porIdConUsuarios(Long id) {
    Optional<Curso> oc = repository.findById(id);
    if(oc.isPresent()){
      Curso curso = oc.get();
      if(!curso.getCursoUsuarios().isEmpty()){
        List<Long> ids = curso.getCursoUsuarios().stream()
          .map(CursoUsuario::getUsuarioId).collect(Collectors.toList());

        List<Usuario> usuarios = usuarioClientRest.obtenerAlumnosPorCurso(ids);
        curso.setUsuarios(usuarios);

      }
      return Optional.of(curso);
    }
    return Optional.empty();
  }
}
