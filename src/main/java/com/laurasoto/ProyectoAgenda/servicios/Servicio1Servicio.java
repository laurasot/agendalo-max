package com.laurasoto.ProyectoAgenda.servicios;
import java.util.List;
import org.springframework.stereotype.Service;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;
import com.laurasoto.ProyectoAgenda.modelos.Servicio;
import com.laurasoto.ProyectoAgenda.repositorios.HorarioRepositorio;
import com.laurasoto.ProyectoAgenda.repositorios.ServicioRepositorio;

@Service
@SuppressWarnings("unused")
public class Servicio1Servicio extends BaseServicio<Servicio>{
	private final ServicioRepositorio servicioRepositorio;
	private final HorarioRepositorio horarioRepositorio;

	
	private Servicio1Servicio(ServicioRepositorio servicioRepositorio, HorarioRepositorio horarioRepositorio){
		super(servicioRepositorio);
		this.servicioRepositorio = servicioRepositorio;
		this.horarioRepositorio = horarioRepositorio;
	}
	
	public List<Servicio> obtieneServicioPorServicioOfrecido(String servicio){
		return servicioRepositorio.findByServicioOfrecidoContaining(servicio);
	}
	
	public List<Servicio> serviciosNoContieneEmpresa(Empresa empresa){
		return servicioRepositorio.findByEmpresaNotContaining(empresa);
	}

	/* public Servicio buscaServicioPorNombre(String servicio){
		return servicioRepositorio.findByServicioOfrecido(servicio);
	} */

}
