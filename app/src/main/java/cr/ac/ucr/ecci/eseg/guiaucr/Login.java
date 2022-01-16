package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText correoLogin;
    EditText contrasenaLogin;
    Button buttonLogin;
    TextView textViewLogin;
    TextView textViewUsarSinIniciar;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoLogin = (EditText) findViewById(R.id.correoLogin);
        contrasenaLogin = (EditText) findViewById(R.id.contrasenaLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewLogin  = (TextView) findViewById(R.id.textViewLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        textViewUsarSinIniciar = (TextView) findViewById(R.id.textViewUsarSinIniciar);
        fAuth = FirebaseAuth.getInstance();
        //Si el usuario ya se ha logeado antes.
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        textViewUsarSinIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewUsarSinIniciar.setTextColor(Color.BLUE);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLogin.setTextColor(Color.BLUE);
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               progressBar.setVisibility(View.VISIBLE);
               String correo = correoLogin.getText().toString().trim();
               String contrasena = contrasenaLogin.getText().toString().trim();
               if(TextUtils.isEmpty(correo)){
                   correoLogin.setError("El correo es requerido.");
                   return;
               }
               if(TextUtils.isEmpty(contrasena)){
                   contrasenaLogin.setError("La contraseña es requerida.");
                   return;
               }
               if(contrasena.length() < 6){
                   contrasenaLogin.setError("La contraseña debe tener mas de 6 caracteres");
                   return;
               }
               //Autentificacion
               fAuth.signInWithEmailAndPassword(correoLogin.getText().toString().trim(), contrasenaLogin.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getApplicationContext(), "Ha iniciado sesion correctamente ", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                           finish();
                       }else{
                           Toast.makeText(getApplicationContext(), "Error, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.INVISIBLE);
                       }
                   }
               });
            }
        });
    }
}