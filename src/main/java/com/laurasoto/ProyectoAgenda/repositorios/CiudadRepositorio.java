package com.laurasoto.ProyectoAgenda.repositorios;

import java.util.List;



import com.laurasoto.ProyectoAgenda.modelos.Ciudad;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;


public interface CiudadRepositorio extends BaseRepositorio<Ciudad> {
    
    List<Ciudad> findByEmpresasNotContaining(Empresa empresa);

    Ciudad findByNombre(String nombreCiudad);
}
