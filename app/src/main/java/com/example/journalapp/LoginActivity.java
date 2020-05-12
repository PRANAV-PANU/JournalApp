
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.journalapp.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton,createAcctButton;

    private ProgressBar progressBar;
    private AutoCompleteTextView emailAddress;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth= FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.login_progress);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_email_sign_in);
        createAcctButton = findViewById(R.id.create_button_login);


        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailPasswordUser(emailAddress.getText().toString().trim()
                ,password.getText().toString().trim());
            }
        });
    }

    private void loginEmailPasswordUser(String email,String pwd) {
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){
            firebaseAuth.signInWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            String currentUserId = user.getUid();

                            collectionReference.whereEqualTo("userId",currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                            @Nullable FirebaseFirestoreException e) {

                                            if (e != null){}
                                            progressBar.setVisibility(View.INVISIBLE);
                                            assert queryDocumentSnapshots != null;
                                            if (!queryDocumentSnapshots.isEmpty()){
                                                for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                                                    JournalApi journalApi = JournalApi.getInstance();
                                                    journalApi.setUsername(snapshot.getString("username"));
                                                    journalApi.setUserId(snapshot.getString("userId"));

                                                    startActivity(new Intent(LoginActivity.this,
                                                            PostJournalActivity.class));
                                                }

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, "Please Enter email and password", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
