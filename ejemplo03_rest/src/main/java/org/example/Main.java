package org.example;

import com.distribuida.db.Persona;
import com.distribuida.servicios.ServicioPersona;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static SeContainer container;

    static List<Persona> listarPersonas (Request req, Response rsp) {
        rsp.type("application/json");

        ServicioPersona servicio=container.select(ServicioPersona.class).get();
        List<Persona> listadoPersonas = servicio.buscarPersonas();

       rsp.header("Cache-Control", "no-cache");

        return listadoPersonas;
    }

    static Persona buscarPersona (Request req, Response rsp) {
        rsp.type("application/json");
        String _id=req.params(":id");

        var servicio=container.select(ServicioPersona.class).get();

        var persona=servicio.buscarPersona(Integer.valueOf(_id));

        if(persona==null){
            halt(404, "Persona no encontrada");
        }
        return persona;
    }


    static Persona insertarPersona(Request req, Response rsp) {
        rsp.type("application/json");

        String jsonPersona = req.body();
        Persona persona = new Gson().fromJson(jsonPersona, Persona.class);

        ServicioPersona servicio = container.select(ServicioPersona.class).get();
        Persona insertPersona = servicio.insertar(persona);

        if (insertPersona == null) {
            halt(400, "Error al insertar persona");
        }

        return insertPersona;
    }

    static Persona actualizarPersona(Request req, Response rsp) {
        rsp.type("application/json");
        String jsonPersona = req.body();
        Persona persona = new Gson().fromJson(jsonPersona, Persona.class);

        ServicioPersona servicio = container.select(ServicioPersona.class).get();
        Persona actualizaPersona = servicio.actualizar(persona);

        return actualizaPersona;
    }

    static String eliminarPersona(Request req, Response rsp) {
        rsp.type("application/json");
        String _id=req.params(":id");

        var servicio=container.select(ServicioPersona.class).get();
        servicio.eliminar(Integer.valueOf(_id));

        return "Persona Eliminada";

    }

    public static void main(String[] args) {
        //Iniciar contenedor
        container = SeContainerInitializer.newInstance().initialize();

        ServicioPersona sp=container.select(ServicioPersona.class).get();

        sp.buscarPersonas().stream().map(Persona::getNombre).forEach(System.out::println);

        Gson gson=new Gson();

        //Puerto a asignar
        port(8080);

        get("/personas", Main::listarPersonas,gson::toJson);
        get("/personas/:id", Main::buscarPersona,gson::toJson);
        post("/personas", Main::insertarPersona,gson::toJson);
        put("/personas", Main::actualizarPersona,gson::toJson);
        delete("/personas/:id", Main::eliminarPersona);

    }
}