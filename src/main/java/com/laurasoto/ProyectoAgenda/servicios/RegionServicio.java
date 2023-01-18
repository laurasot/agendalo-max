package com.laurasoto.ProyectoAgenda.servicios;
import java.util.List;
import org.springframework.stereotype.Service;
import com.laurasoto.ProyectoAgenda.modelos.Region;
import com.laurasoto.ProyectoAgenda.repositorios.RegionRepositorio;

@Service
public class RegionServicio extends BaseServicio<Region> {
    private final RegionRepositorio regionRepositorio;
    
    private RegionServicio(RegionRepositorio regionRepositorio){
        super(regionRepositorio);
        this.regionRepositorio = regionRepositorio;
    }

    public List<Region> regionesTodas(){
        return regionRepositorio.findAll();
    }
}
