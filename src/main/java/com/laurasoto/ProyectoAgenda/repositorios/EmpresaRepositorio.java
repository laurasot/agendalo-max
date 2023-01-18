package com.laurasoto.ProyectoAgenda.repositorios;





import com.laurasoto.ProyectoAgenda.modelos.Ciudad;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;

import java.util.List;


public interface EmpresaRepositorio extends BaseRepositorio<Empresa>{
	//List<Empresa> findByServiciosContaining(Servicio servicio);
	Empresa findByNombre(String nombre);

	List<Empresa> findByCiudad(Ciudad ciudad);
}
