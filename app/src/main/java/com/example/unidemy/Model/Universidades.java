package com.example.unidemy.Model;

import java.util.ArrayList;

public class Universidades {


    ArrayList<Universidad> lista_universidades;
    ArrayList<Facultad> lista_facultades;
    ArrayList<Grado> lista_grados;
    ArrayList<Año> lista_años;
    ArrayList<Asignatura> lista_asignaturas;

    public Universidades(ArrayList<Universidad> lista_universidades) {
        this.lista_universidades = new ArrayList<Universidad>();
        this.lista_facultades = new ArrayList<Facultad>();
        this.lista_grados = new ArrayList<Grado>();
        this.lista_años = new ArrayList<Año>();
        this.lista_asignaturas = new ArrayList<Asignatura>();

        lista_asignaturas.add(new Asignatura("Empresa"));
        lista_asignaturas.add(new Asignatura("PIS"));
        lista_asignaturas.add(new Asignatura("SO1"));
        lista_años.add(new Año("Segundo", lista_asignaturas));
        lista_grados.add(new Grado("Ingenieria Informatica", lista_años));
        lista_facultades.add( new Facultad("Facultad Mates e Info", lista_grados));
        lista_universidades.add(new Universidad("Universitat de Barcelona", lista_facultades));


    }
}
