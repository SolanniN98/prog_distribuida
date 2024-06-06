package com.distribuida;

import com.distribuida.repository.db.Persona;
import com.distribuida.service.IPersonaService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

import java.util.List;

@Configuration
@ComponentScan({"com.distribuida"})
public class Main {

    @Autowired
    private static IPersonaService servicio;

    static List<Persona> listarPersonas(Request req, Response rsp) {
        rsp.type("application/json");


//        ServicioPersona servicio=container.select(ServicioPersona.class).get();
        List<Persona> listadoPersonas = servicio.findAll();

        return listadoPersonas;
    }

    static Persona buscarPersona(Request req, Response rsp) {
        rsp.type("application/json");
        String _id = req.params(":id");

        var persona = servicio.findById(Integer.valueOf(_id));
        if (persona == null) {
            halt(404, "Persona no encontrada");
        }
        return persona;
    }


    static Persona insertarPersona(Request req, Response rsp) {
        rsp.type("application/json");

        String jsonPersona = req.body();
        Persona persona = new Gson().fromJson(jsonPersona, Persona.class);

        Persona insertPersona = servicio.insert(persona);

        if (insertPersona == null) {
            halt(400, "Error al insertar persona");
        }

        return insertPersona;
    }

    static Persona actualizarPersona(Request req, Response rsp) {
        rsp.type("application/json");
        String jsonPersona = req.body();
        Persona persona = new Gson().fromJson(jsonPersona, Persona.class);

        Persona actualizaPersona = servicio.update(persona);

        return actualizaPersona;
    }

    static String eliminarPersona(Request req, Response rsp) {
        rsp.type("application/json");
        String _id = req.params(":id");

        servicio.delete(Integer.valueOf(_id));

        return "Persona Eliminada";

    }

    public static void main(String[] args) {

        servicio.findAll().stream().map(Persona::getNombre).forEach(System.out::println);

        Gson gson = new Gson();

        //Puerto a asignar
        port(8080);

        get("/personas", Main::listarPersonas, gson::toJson);
        get("/personas/:id", Main::buscarPersona, gson::toJson);
        post("/personas", Main::insertarPersona, gson::toJson);
        put("/personas", Main::actualizarPersona, gson::toJson);
        delete("/personas/:id", Main::eliminarPersona);

    }


}