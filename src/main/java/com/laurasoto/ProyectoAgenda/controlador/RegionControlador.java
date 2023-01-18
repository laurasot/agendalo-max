package com.laurasoto.ProyectoAgenda.controlador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.laurasoto.ProyectoAgenda.servicios.RegionServicio;

@Controller
public class RegionControlador {
    @SuppressWarnings("unused")
    private final RegionServicio regionServicio;

    public RegionControlador(RegionServicio regionServicio){
        this.regionServicio = regionServicio;
    }

    @PostMapping("/busca/region/")
	public String buscaCiudad(@RequestParam("region") String region){
		return "redirect:/search/" + region;
	}

    @GetMapping("/parti")
    public String particula(){
        return"particula";
    }
}
