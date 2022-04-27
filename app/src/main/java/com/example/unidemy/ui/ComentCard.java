package com.example.unidemy.ui;


import com.google.firebase.*;
import com.google.firestore.v1.DocumentTransform;

import java.time.Instant;
import java.util.Date;

public class ComentCard {

    private String content, uid, uimg, uname;
    private Object timestamp;



    public ComentCard(String content, String uid, String uimg, String uname) {
        this.content = content;
     
    }

    public ComentCard(String content, String uid, String uimg, String uname, Object timestamp) {
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}