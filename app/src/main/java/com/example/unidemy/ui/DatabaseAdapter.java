package com.example.unidemy.ui;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
    public static ccInterface listener_4;
    public static dcInterface listener_5;
    public static uniInterface listener_6;
    public static facInterface listener_7;
    public static ugInterface listener_8;
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

    public DatabaseAdapter(ccInterface listener){
        this.listener_4 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public DatabaseAdapter(dcInterface listener){
        this.listener_5 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }
    public DatabaseAdapter(uniInterface listener){
        this.listener_6 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public DatabaseAdapter(facInterface listener){
        this.listener_7 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public DatabaseAdapter(ugInterface listener){
        this.listener_8 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }


    public interface ugInterface{
        void setGradoCards(ArrayList<GradoCard> gc);
    }
    public interface vmInterface{
        void setCollection(ArrayList<CursoCard> ac);
        void setToast(String s);
    }

    public interface uniInterface{
        void setUniversidades(ArrayList<UniversidadCard> ac);
    }

    public interface usInterface{
        void setUserCourses(ArrayList<CursoCard> cc);

    }

    public interface vrInterface{
        void setVideoonCourse(ArrayList<VideoCard> cc);
    }

    public interface ccInterface{
        void setComentsOnCourse(ArrayList<ComentCard> cc);
    }

    public interface dcInterface{
        void setDocumentsOnCourse(ArrayList<DocumentCard> dc);
    }

    public interface facInterface{
        void setFacultades(ArrayList<FacultadCard> fc);
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


    public void getUserGrade(String userID){

        DatabaseAdapter.db.collection("Users").document(userID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String acc = (String) document.getString("user_university");
                                if(acc != null) {
                                    getCoursesUniversity(acc);
                                }
                            }
                        }

                    }

                });
    }

    public void getCoursesUniversity(String uniID){
        DatabaseAdapter.db.collection("Curso")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<CursoCard> retrieved_ac = new ArrayList<CursoCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("course_grade") == uniID){
                                    retrieved_ac.add(new CursoCard( document.getString("course_title"), document.getString("course_description"), document.getString("owner"),  document.getString("course_views"), document.getString("course_rating"), document.getString("course_id"), (ArrayList<String>) document.get("course_videos"), (ArrayList<String>) document.get("course_documents"), document.getString("course_portada")));
                                }}
                            listener.setCollection(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    public void getCollectionUniversidades(){
        Log.d(TAG,"Ver Universidades");
        DatabaseAdapter.db.collection("Universidades")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<UniversidadCard> retrieved_ac = new ArrayList<UniversidadCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new UniversidadCard( document.getString("uni_name"), document.getId()));
                            }
                            listener_6.setUniversidades(retrieved_ac);

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

    void getCourseDocuments(String CourseID){

        DatabaseAdapter.db.collection("Curso").document(CourseID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Docs del curso", document.getId() + " => " + document.get("course_documents"));
                                ArrayList<String> acc = (ArrayList<String>) document.get("course_documents");

                                if(acc != null){
                                    getCourseObjectDocuments(acc);
                                }
                            }
                        }

                    }

                });
    }

    void getCourseObjectDocuments(ArrayList<String> dc){
        DatabaseAdapter.db.collection("Documento")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<DocumentCard> retrieved_dc = new ArrayList<DocumentCard>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Documento_Codes", document.getId() + "  ->  " + dc);
                                if (!dc.isEmpty() && dc.contains(document.getId())) {
                                    Log.d("RV poner_docs", document.getId() + " => " + document.getData());
                                    retrieved_dc.add(new DocumentCard(document.getString("document_title"), document.getString("document_views"), document.getString("document_url")));
                                }
                            }
                            listener_5.setDocumentsOnCourse(retrieved_dc);
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
                                if(acc != null) {
                                    getUserObjectCourses(acc);
                                }
                            }
                        }

                    }

                });


    }

    void getUserFaculty(){

        DatabaseAdapter.db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, document.getId() + " => " + document.get("user_university"));
                                String acc = (String) document.get("user_university");
                                if(acc != null) {
                                    getUniversityFaculties(acc);
                                }
                            }
                        }

                    }

                });


    }

    public void getUserGrades(){

        DatabaseAdapter.db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, document.getId() + " => " + document.get("user_university"));
                                String acc = (String) document.get("user_university");
                                String acc_f = (String) document.get("user_faculty");
                                if(acc != null) {
                                    getUserGradesFaculty(acc, acc_f);
                                }
                            }
                        }

                    }

                });

    }

    public void getUserGradesFaculty(String uniId, String facultyID){
        DatabaseAdapter.db.collection("Universidades").document(uniId).collection("Facultades")
                .document(facultyID).collection("Grado")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<GradoCard> retrieved_ac = new ArrayList<GradoCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new GradoCard( document.getString("grado_name"), document.getId()));
                            }
                            listener_8.setGradoCards(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void getUniversityFaculties(String uniID){
        DatabaseAdapter.db.collection("Universidades").document(uniID).collection("Facultades")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<FacultadCard> retrieved_ac = new ArrayList<FacultadCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new FacultadCard( document.getString("facu_name"), document.getId()));
                            }
                            listener_7.setFacultades(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                    retrieved_ac.add(new CursoCard( document.getString("course_title"), document.getString("course_description"), document.getString("owner"),  document.getString("course_views"), document.getString("course_rating"), document.getString("course_id"), (ArrayList<String>) document.get("course_videos"), (ArrayList<String>) document.get("course_documents"), document.getString("course_portada")));
                            }}
                            listener_2.setUserCourses(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    void getCourseComents(String CourseID){

        DatabaseAdapter.db.collection("Curso").document(CourseID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Videos del Curso", document.getId() + " => " + document.get("course_coments"));
                                ArrayList<String> acc = (ArrayList<String>) document.get("course_coments");
                                getCourseObjectComents(acc);
                            }
                        }

                    }

                });
    }

    void getCourseObjectComents(ArrayList<String> uc){
        DatabaseAdapter.db.collection("Coment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ComentCard> retrieved_ac = new ArrayList<ComentCard>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("llega",document.getId()+"  ->  "+ uc);
                                if(uc.contains(document.getId())){
                                    Log.d(" crv comments", document.getId() + " => " + document.getData());
                                    retrieved_ac.add(new ComentCard(document.getString("coment_content"), document.getString("coment_name"), document.getDouble("coment_rating"),
                                            document.getTimestamp("timestamp"), document.getString("coment_id"), document.getDouble("coment_nota")));
                                }}
                            listener_4.setComentsOnCourse(retrieved_ac);

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



