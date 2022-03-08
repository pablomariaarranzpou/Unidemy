package com.example.unidemy.Model;

import java.util.ArrayList;

public class Grado {

    private String nombre;
    private ArrayList<Año> lista_años;

    public Grado(String nombre, ArrayList<Año> lista_años) {
        this.nombre = nombre;
        this.lista_años = lista_años;
    }
}
