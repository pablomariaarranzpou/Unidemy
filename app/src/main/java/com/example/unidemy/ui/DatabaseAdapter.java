package com.example.unidemy.ui;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAdapter extends Activity {

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;


    public static vmInterface listener;
    public static usInterface listener_2;
    public static vrInterface listener_3;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterface listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }
    public DatabaseAdapter(usInterface listener){
        this.listener_2 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public DatabaseAdapter(vrInterface listener){
        this.listener_3 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }


    public interface vmInterface{
        void setCollection(ArrayList<CursoCard> ac);
        void setToast(String s);
    }

    public interface usInterface{
        void setUserCourses(ArrayList<CursoCard> cc);

    }

    public interface vrInterface{
        void setVideoonCourse(ArrayList<VideoCard> cc);

    }


    public void initFirebase(){

        user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                listener.setToast("Authentication successful.");
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                listener.setToast("Authentication failed.");

                            }
                        }
                    });
        }
        else{
            listener.setToast("Authentication with current user.");

        }
    }


    public void getCollectionCursos(){
        Log.d(TAG,"Ver cursos");
        DatabaseAdapter.db.collection("Curso")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<CursoCard> retrieved_ac = new ArrayList<CursoCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new CursoCard( document.getString("course_title"), document.getString("course_description"), document.getString("owner"),  document.getString("course_views"), document.getString("course_rating"), document.getId(), (ArrayList<String>) document.get("course_videos")));
                            }
                            listener.setCollection(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    void getCourseVideos(String CourseID){

        DatabaseAdapter.db.collection("Curso").document(CourseID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Videos del Curso", document.getId() + " => " + document.get("course_videos"));
                                ArrayList<String> acc = (ArrayList<String>) document.get("course_videos");
                                getUserObjectVideos(acc);
                            }
                        }

                    }

                });
    }

    void getUserObjectVideos(ArrayList<String> vc){
        DatabaseAdapter.db.collection("Video")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<VideoCard> retrieved_uc = new ArrayList<VideoCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("VIDEOS_Codes",document.getId()+"  ->  "+ vc);
                                Log.d("Prueba", "|"+document.getId()+"|  in  " + "|" + vc.get(0) + "|" + " = " + vc.contains(document.getId()));
                                if(vc.contains(document.getId())){
                                    Log.d("RV VIDEO_DATA", document.getId() + " => " + document.getData());
                                    retrieved_uc.add(new VideoCard(document.getString("video_title"), document.getString("video_views"), document.getString("video_url")));

                                }}
                            listener_3.setVideoonCourse(retrieved_uc);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    void getUserCourses(String userId){

        DatabaseAdapter.db.collection("Users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, document.getId() + " => " + document.get("userCourses"));
                                ArrayList<String> acc = (ArrayList<String>) document.get("userCourses");
                                getUserObjectCourses(acc);
                            }
                        }

                    }

                });


    }


    void getUserObjectCourses(ArrayList<String> uc){
        DatabaseAdapter.db.collection("Curso")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<CursoCard> retrieved_ac = new ArrayList<CursoCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("llega",document.getId()+"  ->  "+ uc);
                                if(uc.contains(document.getId())){
                                    Log.d(" c a rv usuario", document.getId() + " => " + document.getData());
                                    retrieved_ac.add(new CursoCard( document.getString("course_title"), document.getString("course_description"), document.getString("owner"),  document.getString("course_views"), document.getString("course_rating"), document.getString("course_id"), (ArrayList<String>) document.get("course_videos")));
                            }}
                            listener_2.setUserCourses(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




    public HashMap<String, String> getDocuments () {
        db.collection("Curso")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();
    }


}



