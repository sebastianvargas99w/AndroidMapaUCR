package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PuntoDeInteresDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView foto;
    TextView nombre;
    TextView descripcion;
    TextView telefono;
    TextView pagina;
    EditText comentarioEdit;
    Button botonComentario;
    ImageButton agregarFoto;
    RecyclerView RvComentario;
    ComentarioAdapter comentarioAdapter;
    PuntoDeInteres puntoDeInteres;
    MarcadorFavorito marcadorFavorito;
    List<Comentario> listaComentario;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String nombrePunto = intent.getExtras().getString("nombrePunto");
        puntoDeInteres = PuntoDeInteres.leerPuntoDeInteresPorNombre(getApplicationContext(), nombrePunto);
        marcadorFavorito = MarcadorFavorito.leerMarcadorPorNombre(getApplicationContext(), nombrePunto);
        setContentView(R.layout.activity_punto_de_interes_details);
        foto = (ImageView) findViewById(R.id.fotoDetalle);
        nombre = (TextView) findViewById(R.id.nombre);
        descripcion = (TextView) findViewById(R.id.descripcion);
        telefono = (TextView) findViewById(R.id.telefono);
        pagina = (TextView) findViewById(R.id.pagina);
        comentarioEdit = (EditText) findViewById(R.id.textInputComentario);
        botonComentario = (Button) findViewById(R.id.buttonComentario);
        RvComentario = (RecyclerView) findViewById(R.id.rv_comment);
        agregarFoto = (ImageButton) findViewById(R.id.agregarFoto);

        //se inicializa una instancia de firebase para agregar los comentarios
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(puntoDeInteres != null) {
            nombre.setText(puntoDeInteres.getNombre());
            descripcion.setText(puntoDeInteres.getDescripcion());
            telefono.setText(puntoDeInteres.getTelefono());
            if(!puntoDeInteres.getTipo().equals(PuntoDeInteres.PERSONALIZADO)) {
                agregarFoto.setVisibility(View.GONE);
            }
            if(!puntoDeInteres.getTelefono().equals("No disponible")) {
                telefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + puntoDeInteres.getTelefono()));
                        startActivity(intent);
                    }
                });
            }
            pagina.setText(puntoDeInteres.getPagina());
            if(!puntoDeInteres.getPagina().equals("No disponible")) {
                pagina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(puntoDeInteres.getPagina()));
                        startActivity(intent);
                    }
                });
            }
            byte[] bytesFoto = puntoDeInteres.getFoto();
            if (bytesFoto != null) {
                foto.setImageBitmap(BitmapFactory.decodeByteArray(puntoDeInteres.getFoto(), 0, puntoDeInteres.getFoto().length));
            }
            botonComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    botonComentario.setVisibility(View.INVISIBLE);
                    DatabaseReference commentReference = firebaseDatabase.getReference("Comentario").child(puntoDeInteres.getNombre()).push();
                    String comment_content = comentarioEdit.getText().toString();
                    Comentario comentario = new Comentario(comment_content);
                    commentReference.setValue(comentario).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("Comentario añadido");
                            comentarioEdit.getText().clear();
                            botonComentario.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("No se pudo añadir el comentario : "+e.getMessage());
                        }
                    });
                }
            });

            iniRvComentario();
        }

        if(marcadorFavorito != null) {
            nombre.setText(marcadorFavorito.getNombre());
            agregarFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarFoto();
                }
            });
        }
    }

    private void iniRvComentario() {

        RvComentario.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference comentarioRef = firebaseDatabase.getReference("Comentario").child(puntoDeInteres.getNombre());
        comentarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaComentario = new ArrayList<>();
                for (DataSnapshot snap:snapshot.getChildren()){
                    Comentario comentario = snap.getValue(Comentario.class);
                    listaComentario.add(comentario);
                }
                comentarioAdapter = new ComentarioAdapter(getApplicationContext(),listaComentario);
                RvComentario.setAdapter(comentarioAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void agregarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            assert data != null;
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }
}