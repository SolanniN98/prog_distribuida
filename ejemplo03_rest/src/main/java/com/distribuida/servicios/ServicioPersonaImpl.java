package com.distribuida.servicios;

import com.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
@ApplicationScoped
public class ServicioPersonaImpl implements ServicioPersona {
    @Inject
    EntityManager em;

    @Override
    public Persona buscarPersona(Integer id) {
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> buscarPersonas() {
        return em.createQuery("select p from Persona p order by id asc", Persona.class).getResultList();
    }

    @Override
    public Persona insertar(Persona persona) {
        em.persist(persona);
        return persona;
    }

    @Override
    public Persona actualizar(Persona persona) {
        em.merge(persona);
        return persona;
    }

    @Override
    public Persona eliminar(Integer id) {
        em.remove(em.find(Persona.class, id));
        return null;
    }
}
