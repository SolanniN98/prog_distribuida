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
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(persona);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        return persona;
    }

    @Override
    public Persona actualizar(Persona persona) {

        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(persona);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        return persona;
    }

    @Override
    public void eliminar(Integer id) {
        var transaction = em.getTransaction();

        try {
            transaction.begin();
            em.remove(buscarPersona(id));
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
    }
}
