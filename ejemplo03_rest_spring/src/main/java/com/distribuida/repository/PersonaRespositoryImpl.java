package com.distribuida.repository;

import com.distribuida.repository.db.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PersonaRespositoryImpl implements IPersonaRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    public Persona insert(Persona p) {
        em.persist(p);
        return p;
    }

    @Override
    public Persona update(Persona p) {
        em.merge(p);
        return p;
    }

    @Override
    public void delete(Integer id) {
        em.remove(em.find(Persona.class, id));
    }

    @Override
    public Persona findById(Integer id) {
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> findAll() {
        TypedQuery<Persona>myQuery=em.createQuery("SELECT p FROM Persona p ORDER BY p.id ASC", Persona.class);
        return myQuery.getResultList();
    }
}
