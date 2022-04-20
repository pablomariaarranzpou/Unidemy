package com.example.unidemy.ui;




import java.util.HashMap;
import java.util.UUID;

// From https://developer.android.com/training/data-storage/room/

public class CursoCard {

    private String nameID;
    private final String course_title;
    private final String course_views;
    private final String course_rating;
    private final String course_description;
    private final String owner;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    //private final AppDatabase db;


    public CursoCard(String course_title, String course_description, String owner, String course_views, String course_rating) {
        this.course_title = course_title;
        this.course_views = course_views;
        this.course_rating = course_rating;
        this.course_description = course_description;
        this.owner = owner;
        UUID uuid = UUID.randomUUID();
        this.nameID = uuid.toString();
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public String getCourse_title() {
        return course_title;
    }

    public String getCourse_views() {
        return course_views;
    }

    public String getCourse_rating() {
        return course_rating;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getOwner() {
        return owner;
    }

    public DatabaseAdapter getAdapter() {
        return adapter;
    }

    public CursoCard getCard() {
        // ask database and if true, return CursoCard
        HashMap<String, String> hm = adapter.getDocuments();
        if (hm != null) {
            CursoCard ac = new CursoCard(hm.get("course_title"), hm.get("course_description"), hm.get("course_description"), hm.get("course_views"), hm.get("course_rating"));
            return ac;
        } else {
            return null;
        }
    }
}
