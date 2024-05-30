package com.distribuida.servicios;

import com.distribuida.servicios.StringService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StringServiceImpl implements StringService {
    @Override
    public String convert(String srt) {
        return srt.toUpperCase();
    }
}
