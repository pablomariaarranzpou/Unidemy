package com.example.unidemy.ui;

import android.content.Context;

import java.util.ArrayList;

public class User{
    private String name;
    private ArrayList<CursoCard> my_courses;
    private Context con;
    private String university;
    private String faculty;
    private String degree;

    public User(String name, Context con){
            this.name = name;
            this.con = con;
            my_courses = new ArrayList<CursoCard>();
            }

    public String getName(){
            return name;
            }

    public Context getContext(){
            return con;
            }

    public ArrayList<CursoCard> getMyCourses(){
            return my_courses;
            }

    public void addToMyCourses(CursoCard item){
            my_courses.add(item);
            }

    public void removeFromMyCourses(CursoCard item){
            my_courses.remove(item);
            }


    public CursoCard getItemFromMyCourses(int i){
            return my_courses.get(i);
            }

        }