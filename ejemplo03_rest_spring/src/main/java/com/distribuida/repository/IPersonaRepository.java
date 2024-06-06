package com.distribuida.repository;

import com.distribuida.repository.db.Persona;

import java.util.List;

public interface IPersonaRepository {

    public Persona insert(Persona p);
    public Persona update(Persona p);

    public void delete(Integer id);

    public Persona findById(Integer id);

    public List<Persona> findAll();
}
