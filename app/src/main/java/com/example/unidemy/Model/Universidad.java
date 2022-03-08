package com.example.unidemy.Model;

import java.util.ArrayList;

public class Universidad {


    private String nombre;
    private ArrayList<Facultad> listaFacultades;

    public Universidad(String nombre,ArrayList<Facultad> listaFacultades) {
        this.nombre = nombre;
        this.listaFacultades = listaFacultades;
    }

    }

