package com.nwo.springcloud.msvc.cursos.model.entity;

import com.nwo.springcloud.msvc.cursos.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="cursos")
@Getter @Setter
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "curso_id")
  private List<CursoUsuario> cursoUsuarios;

  @Transient
  private List<Usuario> usuarios;

  public Curso() {
    cursoUsuarios = new ArrayList<>();
    usuarios = new ArrayList<>();
  }

  public void addCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.add(cursoUsuario);
  }

  public void removeCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.remove(cursoUsuario);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Curso curso = (Curso) o;
    return Objects.equals(id, curso.id);
  }


}
