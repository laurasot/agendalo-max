package com.laurasoto.ProyectoAgenda.modelos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
    public class TachamientoBoton{
        private Boolean estaActivo = true;
        private int horaAgendadaByCliente;
        private Date date;
        private Usuario usuario;
    }