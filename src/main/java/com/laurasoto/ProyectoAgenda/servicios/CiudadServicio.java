package com.laurasoto.ProyectoAgenda.servicios;
import java.util.List;
import org.springframework.stereotype.Service;
import com.laurasoto.ProyectoAgenda.modelos.Ciudad;
import com.laurasoto.ProyectoAgenda.modelos.Empresa;
import com.laurasoto.ProyectoAgenda.repositorios.CiudadRepositorio;

@Service
public class CiudadServicio extends BaseServicio<Ciudad> {
    private final CiudadRepositorio ciudadRepositorio;

    private CiudadServicio(CiudadRepositorio ciudadRepositorio){
        super(ciudadRepositorio);
        this.ciudadRepositorio = ciudadRepositorio;

    }
    public Ciudad getCiudadPorNombre(String ciudad){
        return ciudadRepositorio.findByNombre(ciudad);
    }
    public List<Ciudad> ciudadesMostrar(Empresa empresa){
        return ciudadRepositorio.findAll();
    }

    public List<Ciudad> ciudadesNoContieneEmpresa(Empresa empresa){
        return ciudadRepositorio.findByEmpresasNotContaining(empresa);
    }

}
