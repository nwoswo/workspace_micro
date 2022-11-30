package com.nwo.springcloud.msvc.cursos.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CursoDto {

  private Long id;

  @NotEmpty
  private String nombre;

}
