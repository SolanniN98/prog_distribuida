package com.distribuida;

import com.distribuida.servicios.StringService;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Principal {
    public static void main(String[] args) {
         SeContainer container = SeContainerInitializer.newInstance().initialize();

         /*Instance<StringService> obj= container.select(StringService.class);
         obj.get();*/

        StringService service= container.select(StringService.class).get();

        String ret= service.convert("hola mundo!");

        System.out.println(ret);

    }

}
