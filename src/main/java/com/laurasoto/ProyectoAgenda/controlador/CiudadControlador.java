/* package com.laurasoto.ProyectoAgenda.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laurasoto.ProyectoAgenda.servicios.CiudadServicio;

@Controller
public class CiudadControlador {
    private final CiudadServicio ciudadServicio;

    public CiudadControlador(CiudadServicio ciudadServicio){
        this.ciudadServicio = ciudadServicio;
    }

    @PostMapping("/busca/ciudad/")
	public String buscaCiudad(@RequestParam("ciudad") String ciudad){
		return "redirect:/search/" + ciudad;
	}
} */
