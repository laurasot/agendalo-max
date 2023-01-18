package com.laurasoto.ProyectoAgenda.utiles;


import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurasoto.ProyectoAgenda.modelos.Region;

public class Funciones {
    
    public String regionesToJson(List<Region> regiones){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(regiones);
        } catch (Exception e) {
            System.out.println("============================");
            System.out.println(" Error generacion Json");
            System.out.println(" Objeto: Lista de Regiones");
            System.out.println(" Origen: Funciones.java (Linea 15)");
            System.out.println("============================");
        }
        return json;
    }
}
