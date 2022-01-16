package cr.ac.ucr.ecci.eseg.guiaucr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class AddPointDialog extends AppCompatDialogFragment {
    private AddPointDialogAction listener;
    private EditText nombreAplicacionText;
    private EditText descriptcionAplicacionText;
    private ImageButton iconoFacultad;
    private ImageButton iconoFotocopiadora;
    private ImageButton iconoParada;
    private ImageButton iconoSoda;
    private ImageButton iconoFavorito;
    double coordenadaX;
    double coordenadaY;
    byte[] foto;
    private String iconoEscogido;
    PuntoDeInteres nuevoPunto = null;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_point, null);
        iconoEscogido = "Personalizado";

        nombreAplicacionText = (EditText) view.findViewById(R.id.nombreCampo);
        descriptcionAplicacionText = (EditText) view.findViewById(R.id.descripcionCampo);
        iconoFacultad = (ImageButton) view.findViewById(R.id.iconoPersonalizadoFacultad);
        iconoFotocopiadora = (ImageButton) view.findViewById(R.id.iconoPersonalizadoFotocopiadora);
        iconoParada = (ImageButton) view.findViewById(R.id.iconoPersonalizadoParada);
        iconoSoda = (ImageButton) view.findViewById(R.id.iconoPersonalizadoSoda);
        iconoFavorito = (ImageButton) view.findViewById(R.id.iconoPersonalizadoFavorito);

        iconoFacultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconoEscogido = "Facultad";
            }
        });
        iconoFotocopiadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconoEscogido = "Fotocopiadora";
            }
        });
        iconoParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconoEscogido = "Parada";
            }
        });
        iconoSoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconoEscogido = "Soda";
            }
        });
        iconoFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconoEscogido = "Personalizado";
            }
        });

        builder.setView(view).setTitle("Crear nuevo punto").
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                //Do nothing xd.
            }
        }).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(nombreAplicacionText.getText().toString().trim() != "") {
                    //Guardar los datos en la base de datos
                    PuntoDeInteres nuevoPuntoDeInteres = new PuntoDeInteres(
                            nombreAplicacionText.getText().toString().trim(),
                            coordenadaX,
                            coordenadaY,
                            descriptcionAplicacionText.getText().toString().trim(),
                            iconoEscogido,
                            "No disponible",
                            "No disponible",
                            foto
                    );
                    PuntoDeInteres.insertar(getContext(), nuevoPuntoDeInteres);

                    MarcadorFavorito marcador_nuevo = new MarcadorFavorito(nuevoPuntoDeInteres.getNombre(),
                            nuevoPuntoDeInteres.getCoordenadaX(), nuevoPuntoDeInteres.getCoordenadaY(), 1,
                            nuevoPuntoDeInteres.getDescripcion(), "", nuevoPuntoDeInteres.getNombre());
                    long id = MarcadorFavorito.insertar(getContext(), marcador_nuevo);

                    listener.updateScreen();
                    dismiss();
                }
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddPointDialogAction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddPointAction");
        }
    }
    public interface AddPointDialogAction{
        public void updateScreen();
    }
}
