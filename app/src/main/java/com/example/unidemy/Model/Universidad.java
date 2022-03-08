package com.example.unidemy.Model;

import java.util.ArrayList;

public class Universidad {


    private String nombre;
    private ArrayList<Facultad> listaFacultades;

    public Universidad(String nombre) {
        this.nombre = nombre;
    }

    private void addFacultad(Facultad facultad){
        if(!listaFacultades.contains(facultad)){
            listaFacultades.add(facultad);
        }
    }
}
