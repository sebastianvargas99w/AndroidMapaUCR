package cr.ac.ucr.ecci.eseg.guiaucr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class CustomListAdapter extends ArrayAdapter<PuntoDeInteres> implements Filterable {

    private final Activity context;
    private final List<PuntoDeInteres> puntoDeInteres;
    private List<PuntoDeInteres> puntoDeInteresFiltrados;
    private final Integer[] imgid;


    public CustomListAdapter(Activity context, List<PuntoDeInteres> puntoDeInteres, Integer[] imgid) {
        super(context, R.layout.custom_suggestion_list_item, puntoDeInteres);
        this.context = context;
        this.puntoDeInteres = puntoDeInteres;
        this.puntoDeInteresFiltrados = puntoDeInteres;
        this.imgid = imgid;
    }

    @Override
    public PuntoDeInteres getItem(int position) {
        return puntoDeInteresFiltrados.get(position);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_suggestion_list_item, parent, false);

        TextView nombre = (TextView) rowView.findViewById(R.id.name_punto_interes);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.icon_tipo_punto_interes);

        if (position < puntoDeInteresFiltrados.size()) {
            PuntoDeInteres punto = puntoDeInteresFiltrados.get(position);

            nombre.setText(punto.getNombre());

            String tipo = punto.getTipo();

            // Se pone el ícono dependiendo del tipo del punto de interés
            if (tipo.equals("Facultad")) {
                imagen.setImageResource(imgid[0]);
            } else if (tipo.equals("Fotocopiadora")) {
                imagen.setImageResource(imgid[1]);
            } else if (tipo.equals("Parada")) {
                imagen.setImageResource(imgid[2]);
            } else if (tipo.equals("Soda")) {
                imagen.setImageResource(imgid[3]);
            } else {
                // sin ícono
            }
        }

        return rowView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = puntoDeInteres.size();
                    filterResults.values = puntoDeInteres;

                } else {
                    final List<PuntoDeInteres> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (PuntoDeInteres punto:puntoDeInteres) {
                        if (punto.getNombre().toLowerCase().contains(searchStr)) {
                            resultsModel.add(punto);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0) {
                    notifyDataSetInvalidated();
                } else {
                    puntoDeInteresFiltrados = (List<PuntoDeInteres>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        
        return filter;
    }
}
