package com.distribuida.service;

import com.distribuida.repository.IPersonaRepository;
import com.distribuida.repository.db.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    IPersonaRepository iPersonaRepository;

    @Override
    public Persona insert(Persona p) {
        return iPersonaRepository.insert(p);
    }

    @Override
    public Persona update(Persona p) {
        return iPersonaRepository.update(p);
    }

    @Override
    public void delete(Integer id) {
        iPersonaRepository.delete(id);
    }

    @Override
    public Persona findById(Integer id) {
        return iPersonaRepository.findById(id);
    }

    @Override
    public List<Persona> findAll() {
        return iPersonaRepository.findAll();
    }
}
