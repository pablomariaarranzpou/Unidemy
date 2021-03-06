package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.example.unidemy.adapters.CardTestHolder;
import com.example.unidemy.adapters.CardTestViewModel;
import com.example.unidemy.adapters.CardTestViewModelFactory;
import com.example.unidemy.adapters.CardVideoAdapter;
import com.example.unidemy.adapters.DocumentCardAdapter;
import com.example.unidemy.adapters.DocumentRecyclerView_ViewModel;
import com.example.unidemy.adapters.DocumentRecyclerView_ViewModelFactory;
import com.example.unidemy.adapters.NumberFormater;
import com.example.unidemy.adapters.VideoRecyclerView_ViewModel;
import com.example.unidemy.adapters.VideoRecyclerView_ViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.CardTest;
import model.CursoCard;
import model.DocumentCard;
import model.VideoCard;

public class ViewCourse extends AppCompatActivity implements CardTestHolder.CardTestAdapter.OnTestListener, CardVideoAdapter.OnVideoListener, DocumentCardAdapter.OnDocumentListener {


    private TextView ind_course_views_txt, ind_course_title_txt, ind_owner_txt, ind_course_rating_txt, ind_course_description;
    private ImageView play_button;
    private Button ind_btn_pagar, ind_btn_opinar;
    private Context parentContext;
    private FirebaseAuth mAuth;
    private CursoCard cc;
    private AppCompatActivity mActivity;
    private String userId;
    private FirebaseFirestore firestore;
    private VideoRecyclerView_ViewModel viewmodelm;
    private DocumentRecyclerView_ViewModel cviewmodelm;
    private CardTestViewModel tsviewmodel;
    private RecyclerView mmRecyclerView;
    private RecyclerView dcRecyclerView;
    private RecyclerView tsRecyclerView;
    private ArrayList<String> videos, documents;
    private String id, portada_txt;
    private TextView txt_titulodocumentos;
    private boolean pagado;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mAuth = FirebaseAuth.getInstance();
        parentContext = getBaseContext();
        firestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        setContentView(R.layout.activity_view_course);
        mmRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_videos);
        mmRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCourse.this,
                LinearLayoutManager.HORIZONTAL, false));

        dcRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_documentos);
        dcRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCourse.this,
                LinearLayoutManager.HORIZONTAL, false));

        tsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_tests);
        tsRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCourse.this,
                LinearLayoutManager.HORIZONTAL, false));

        ind_course_views_txt = (TextView) findViewById(R.id.ind_course_views);
        ind_course_title_txt = (TextView) findViewById(R.id.ind_course_title);
        ind_owner_txt = (TextView) findViewById(R.id.ind_owner_txt);
        ind_course_rating_txt = (TextView) findViewById(R.id.ind_course_rating);
        ind_course_description = (TextView) findViewById(R.id.ind_course_description);
        ind_btn_pagar = (Button) findViewById(R.id.ind_btn_pagar);
        ind_btn_opinar = (Button) findViewById((R.id.ind_btn_opinar));
        play_button = (ImageView) findViewById(R.id.course_image);
        txt_titulodocumentos = findViewById(R.id.textView15);




        if (getIntent().hasExtra("selectedCourse")) {


            cc = (CursoCard) getIntent().getParcelableExtra("selectedCourse");
            ind_course_title_txt.setText(cc.getCourse_title());
            ind_owner_txt.setText(cc.getOwner());


            Task<DocumentSnapshot> documentReference = firestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    ArrayList<String> acc = (ArrayList<String>) document.get("userCourses");
                                    checkIfPaid(acc);
                                }
                            }

                        }

                    });


            ind_course_rating_txt.setText(cc.getCourse_rating());
            ind_course_description.setText(cc.getCourse_description());
            videos = cc.getCourse_videos();
            documents = cc.getCourse_documents();
            id = cc.getCourse_id();

            Task<DocumentSnapshot> documentReferenceCourse = firestore.collection("Curso").document(id).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            int views = Integer.parseInt(doc.getString("course_views"));
                            views++;
                            Log.d("Update V", String.valueOf(views));
                            Task<Void> documentReferenceCourse2 = firestore.collection("Curso").document(id).update("course_views", String.valueOf(views))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("UPDATE V", task.toString());
                                }

                            });
                            Log.d("Update V", String.valueOf(views));
                            ind_course_views_txt.setText(String.valueOf(NumberFormater.prettyCount(views)));
                        }
                    });

            if(cc.getCourse_porta() != null){
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/unidemy-a5397.appspot.com/o/images%2Fportada_curso_1.jpg?alt=media&token=2e0feea9-26c2-4dc5-a7ac-0990a3d5068e").into(play_button);
            }

            if (getIntent().hasExtra("selectedPortada")){

                portada_txt = getIntent().getExtras().getString("selectedPortada");
                Picasso.get().load(portada_txt).into(play_button);
            }else{
                Log.d("NOTHING", "Nothing in intent");
            }

        }


        ind_btn_opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourse.this, RecyclerViewComents.class);
                intent.putExtra("course_id", id);
                startActivity(intent);
            }
        });

        ind_btn_pagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pagado = true;
                DocumentReference documentReference = firestore.collection("Users").document(userId);
                documentReference.update("userCourses", FieldValue.arrayUnion(cc.getCourse_id())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ns", "DocumentSnapshot successfully updated!");
                        Toast.makeText(ViewCourse.this,
                                "Curso " + cc.getCourse_title() + " pagado con ??xito!",
                                Toast.LENGTH_SHORT).show();
                        ind_btn_pagar.setVisibility(View.GONE);
                    }

                });
            }
        });
        setLiveDataObservers();
    }

    private void checkIfPaid(ArrayList<String> acc) {

        if(acc.contains(this.id)){
            ind_btn_pagar.setVisibility(View.GONE);
            pagado = true;
        }else{
            pagado = false;
        }

    }

    public void setLiveDataObservers() {
            //Subscribe the activity to the observable
    tsviewmodel = new ViewModelProvider(this, new CardTestViewModelFactory(this.getApplication(), this.getCourseId())).get(CardTestViewModel.class);
    CardTestHolder.CardTestAdapter newAdaptertest = new CardTestHolder.CardTestAdapter(new ArrayList<CardTest>(), parentContext, (CardTestHolder.CardTestAdapter.OnTestListener) mActivity);
    final Observer<ArrayList<CardTest>> observertest = new Observer<ArrayList<CardTest>>() {
        @Override
        public void onChanged(ArrayList<CardTest> ac) {
            CardTestHolder.CardTestAdapter newAdaptertest = new CardTestHolder.CardTestAdapter(ac, parentContext, (CardTestHolder.CardTestAdapter.OnTestListener) mActivity);
            tsRecyclerView.swapAdapter(newAdaptertest, false);
            newAdaptertest.notifyDataSetChanged();
        }
            };

        viewmodelm = new ViewModelProvider(this, new VideoRecyclerView_ViewModelFactory(this.getApplication(), this.getCourseId())).get(VideoRecyclerView_ViewModel.class);
        CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, new ArrayList<VideoCard>(), (CardVideoAdapter.OnVideoListener) mActivity);
        final Observer<ArrayList<VideoCard>> observer = new Observer<ArrayList<VideoCard>>() {
            @Override
            public void onChanged(ArrayList<VideoCard> ac) {
                CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, ac, (CardVideoAdapter.OnVideoListener) mActivity);
                mmRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        cviewmodelm = new ViewModelProvider(this, new DocumentRecyclerView_ViewModelFactory(this.getApplication(), this.getCourseId())).get(DocumentRecyclerView_ViewModel.class);
        DocumentCardAdapter dcnewAdapter = new DocumentCardAdapter(new ArrayList<DocumentCard>(), parentContext, (DocumentCardAdapter.OnDocumentListener) mActivity);
        final Observer<ArrayList<DocumentCard>> observer_two = new Observer<ArrayList<DocumentCard>>() {
            @Override
            public void onChanged(ArrayList<DocumentCard> dc) {
                DocumentCardAdapter dcnewAdapter = new DocumentCardAdapter( dc, parentContext, (DocumentCardAdapter.OnDocumentListener) mActivity);
                dcRecyclerView.swapAdapter(dcnewAdapter, false);
                dcnewAdapter.notifyDataSetChanged();
            }
        };

            final Observer<String> observerToast = new Observer<String>() {
                @Override
                public void onChanged(String t) {
                    //Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
                }
            };
            ;
            viewmodelm.getVideoCards().observe(this, observer);
            cviewmodelm.getDocumentCards().observe(this, observer_two);
            viewmodelm.getToast().observe(this, observerToast);
            tsviewmodel.getTestCards().observe(this, observertest);

        }

    public String getCourseId(){
        return this.id;
    }


    @Override
    public void onVideoClick (int position) {
        if(pagado) {
            Intent intent = new Intent(this, ViewVideo.class);
            intent.putExtra("selectedVideo", viewmodelm.getVideoCard(position));
            startActivity(intent);
        }else{
            Toast.makeText(ViewCourse.this,
                    "Debes pagar el curso '" + cc.getCourse_title() + "' para ver el contenido",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDocumentClick(int position) {
        if(pagado){
            Intent intent = new Intent(this, ViewDocument.class);
            intent.putExtra("selectedDocument", cviewmodelm.getDocumentCard(position));
            startActivity(intent);
        }else{
            Toast.makeText(ViewCourse.this,
                    "Debes pagar el curso '" + cc.getCourse_title() + "' para ver el contenido",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent inten = new Intent(ViewCourse.this, RecyclerViewActivity.class);
        startActivity(inten);
        finish();
    }

    @Override
    public void onTestClickClick(int position) {
        if(pagado){
            Intent intent = new Intent(this, ViewTest.class);
            intent.putExtra("selectedTest", tsviewmodel.getTestCard(position));
            startActivity(intent);
        }else{
            Toast.makeText(ViewCourse.this,
                    "Debes pagar el curso '" + cc.getCourse_title() + "' para ver el contenido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Task<DocumentSnapshot> documentReferenceCourse2 = firestore.collection("Curso").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        String a = doc.getString("course_rating");
                        Task<DocumentSnapshot> documentReferenceCourse2 = firestore.collection("Curso").document(id).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override

                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    }
                                });

                        ind_course_rating_txt.setText(a);
                    }
                });

    }
}





