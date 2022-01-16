package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MarcadorFavoritoListAdapter extends BaseAdapter {
    Context context;
    List<MarcadorFavorito> list;

    public MarcadorFavoritoListAdapter(Context context, List<MarcadorFavorito> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MarcadorFavorito getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.marcador_favorito_item_list, null, true);

        MarcadorFavorito persona = list.get(i);

        ImageView imagen = (ImageView) rowView.findViewById(R.id.favorito_imagen_item);
        TextView nombre = (TextView) rowView.findViewById(R.id.favorito_nombre_item);

        nombre.setText(persona.getNombre());
        imagen.setImageResource(R.drawable.ic_baseline_location_on_24);

        return rowView;
    }
}
