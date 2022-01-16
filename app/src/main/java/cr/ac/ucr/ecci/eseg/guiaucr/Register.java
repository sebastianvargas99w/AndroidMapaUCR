package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText correoRegistro;
    EditText contrasenaRegistro;
    EditText confirmarContraseña;
    Button buttonRegistro;
    TextView textViewRegistro;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        correoRegistro = (EditText) findViewById(R.id.correoRegistro);
        contrasenaRegistro = (EditText) findViewById(R.id.contrasenaRegistro);
        confirmarContraseña = (EditText) findViewById(R.id.contrasenaRegistroConfirmar);
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        textViewRegistro = (TextView) findViewById(R.id.textViewRegistro);

        fAuth = FirebaseAuth.getInstance();

        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewRegistro.setTextColor(Color.BLUE);
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = correoRegistro.getText().toString().trim();
                String contrasena = contrasenaRegistro.getText().toString().trim();
                if(TextUtils.isEmpty(correo)){
                    correoRegistro.setError("El correo es requerido.");
                    return;
                }
                if (!contrasenaRegistro.getText().toString().trim().equals(confirmarContraseña.getText().toString().trim())){
                    confirmarContraseña.setError("Las contraseñas no coinciden");
                    return;
                }
                if(TextUtils.isEmpty(contrasena)){
                    contrasenaRegistro.setError("La contraseña es requerido.");
                    return;
                }
                if(contrasena.length() < 6){
                    contrasenaRegistro.setError("La contraseña debe tener mas de 6 caracteres");
                    return;
                }

                //Register user
                fAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "El usuario fue creado exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}