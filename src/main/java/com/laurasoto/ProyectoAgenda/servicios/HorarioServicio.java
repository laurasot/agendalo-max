package com.laurasoto.ProyectoAgenda.servicios;
import org.springframework.stereotype.Service;
import com.laurasoto.ProyectoAgenda.modelos.Horario;
import com.laurasoto.ProyectoAgenda.repositorios.HorarioRepositorio;

@Service
public class HorarioServicio extends BaseServicio<Horario>{
    private final HorarioRepositorio horarioRepositorio;

    private HorarioServicio(HorarioRepositorio horarioRepositorio){
		super(horarioRepositorio);
		this.horarioRepositorio = horarioRepositorio;
	}
    
	public Horario findByHoraDisponible(Long horaDisponible){
		return horarioRepositorio.findByHoraDisponible(horaDisponible);
	}

	public Horario findById(Long id){
		return horarioRepositorio.findById(id).orElse(null);
	}
}
