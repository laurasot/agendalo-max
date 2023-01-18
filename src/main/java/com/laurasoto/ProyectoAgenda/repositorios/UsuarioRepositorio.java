package com.laurasoto.ProyectoAgenda.repositorios;


import com.laurasoto.ProyectoAgenda.modelos.Usuario;

public interface UsuarioRepositorio extends BaseRepositorio<Usuario>{
	Usuario findByEmail(String email);
}
