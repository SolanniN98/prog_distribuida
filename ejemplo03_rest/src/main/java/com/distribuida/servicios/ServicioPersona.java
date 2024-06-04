package com.distribuida.servicios;

import com.distribuida.db.Persona;

import java.util.List;

public interface ServicioPersona {

     Persona buscarPersona(Integer id);
     List<Persona> buscarPersonas();
     Persona insertar(Persona persona);
     Persona actualizar(Persona persona);
     void eliminar(Integer id);
}
