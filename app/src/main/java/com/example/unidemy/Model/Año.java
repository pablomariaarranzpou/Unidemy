package com.example.unidemy.Model;

import java.util.ArrayList;

public class Año {

    private ArrayList<Asignatura> lista_asignaturas;
    private String nombre;

    public Año(String nobmre, ArrayList<Asignatura> lista_asignaturas) {

        this.lista_asignaturas = new ArrayList<Asignatura>();
        this.nombre = nobmre;
    }
}
