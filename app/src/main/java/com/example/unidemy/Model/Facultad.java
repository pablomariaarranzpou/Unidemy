package com.example.unidemy.Model;

import java.util.ArrayList;

public class Facultad {

    private ArrayList<Grado> grados;
    private String nombre;

    public Facultad(String nombre, ArrayList grados) {
        this.nombre = nombre;
        this.grados = grados;
    }
}
