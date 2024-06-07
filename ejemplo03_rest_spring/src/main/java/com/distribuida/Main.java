package com.distribuida;

import com.distribuida.config.Jpaconfig;
import com.distribuida.repository.db.Persona;
import com.distribuida.service.IPersonaService;
import com.distribuida.service.PersonaServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

import static spark.Spark.*;
@Controller
public class Main {

    static AnnotationConfigApplicationContext context;
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Jpaconfig.class);
        var servicio = context.getBean(IPersonaService.class);
        servicio.findAll().stream().map(Persona::getNombre).forEach(System.out::println);
        // Puerto a asignar
        port(8080);

        // Configuración de rutas con Spark
        get("/personas", "application/json", Main::listarPersonas, new JsonTransformer());
        get("/personas/:id", "application/json", Main::buscarPersona, new JsonTransformer());
        post("/personas", "application/json", Main::insertarPersona, new JsonTransformer());
        put("/personas/:id", "application/json", Main::actualizarPersona, new JsonTransformer());
        delete("/personas/:id", "application/json", Main::eliminarPersona, new JsonTransformer());
    }

    // Método estático para listar personas
    static List<Persona> listarPersonas(Request req, Response rsp) {
        var servicio = context.getBean(IPersonaService.class);
        rsp.type("application/json");
        List<Persona> listadoPersonas = servicio.findAll();
        return listadoPersonas;
    }

    // Método estático para buscar persona por id
    static Persona buscarPersona(Request req, Response rsp) {
        var servicio = context.getBean(IPersonaService.class);
        rsp.type("application/json");
        String _id = req.params(":id");
        var persona = servicio.findById(Integer.valueOf(_id));
        if (persona == null) {
            rsp.status(404);

        }
        return persona;
    }

    // Método estático para insertar persona
    static Persona insertarPersona(Request req, Response rsp) {
        var servicio = context.getBean(IPersonaService.class);
        rsp.type("application/json");
        Persona persona = new Gson().fromJson(req.body(), Persona.class);
        Persona insertPersona = servicio.insert(persona);
        if (insertPersona == null) {
            rsp.status(400);

        }
        return insertPersona;
    }

    // Método estático para actualizar persona
    static Persona actualizarPersona(Request req, Response rsp) {
        var servicio = context.getBean(IPersonaService.class);
        rsp.type("application/json");
        String _id = req.params(":id");
        Persona persona = new Gson().fromJson(req.body(), Persona.class);
        persona.setId(Integer.valueOf(_id)); // Asegurar que el ID en la persona coincida con el ID de la URL
        Persona actualizaPersona = servicio.update(persona);
        return actualizaPersona;
    }

    // Método estático para eliminar persona
    static String eliminarPersona(Request req, Response rsp) {
        var servicio = context.getBean(IPersonaService.class);
        rsp.type("application/json");
        String _id = req.params(":id");
        servicio.delete(Integer.valueOf(_id));
        return "Persona Eliminada";
    }

    // Clase para convertir objetos a JSON usando Gson
     static class JsonTransformer implements spark.ResponseTransformer {
        private Gson gson = new Gson();
        @Override
        public String render(Object model) throws Exception {
            return gson.toJson(model);
        }
    }
}