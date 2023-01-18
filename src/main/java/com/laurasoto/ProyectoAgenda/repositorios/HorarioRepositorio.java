package com.laurasoto.ProyectoAgenda.repositorios;

import com.laurasoto.ProyectoAgenda.modelos.Horario;

public interface HorarioRepositorio extends BaseRepositorio<Horario> {
    Horario findByHoraDisponible(Long horaDisponible);
}
