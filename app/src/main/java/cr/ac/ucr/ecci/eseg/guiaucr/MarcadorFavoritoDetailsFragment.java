package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MarcadorFavoritoDetailsFragment extends Fragment {
    private Context context;

    private static final String ARG_FAVORITO = "favorito";

    private MarcadorFavorito favorito;

    public MarcadorFavoritoDetailsFragment() {}

    public MarcadorFavoritoDetailsFragment(MarcadorFavorito marcadorFavorito) {
        this.favorito = marcadorFavorito;
    }

    public static MarcadorFavoritoDetailsFragment newInstance(MarcadorFavorito marcadorFavorito) {
        MarcadorFavoritoDetailsFragment fragment = new MarcadorFavoritoDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FAVORITO, marcadorFavorito);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            favorito = getArguments().getParcelable(ARG_FAVORITO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.marcador_favorito_details_fragment, container, false);

        if(favorito != null) {
            ImageView imagen = (ImageView) v.findViewById(R.id.favorito_detalle_imagen);
            imagen.setImageResource(R.drawable.ic_baseline_location_on_24); //Icono en el detalle del marcador
            TextView textID = (TextView) v.findViewById(R.id.favorito_detalle_nombre);
            textID.setText(favorito.getNombre());
            TextView textNombre = (TextView) v.findViewById(R.id.favorito_detalle_descripcion);
            textNombre.setText(favorito.getDescripcion());

            Button irAMarcadorEnMapa = (Button)v.findViewById(R.id.boton_ir_a_marcador);
            irAMarcadorEnMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    getActivity().setResult(getActivity().RESULT_OK, intent);
                    getActivity().finish();
                }

            });

        }
        return v;
    }

}
