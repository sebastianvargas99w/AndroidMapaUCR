package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.maps.MapboxMap;

public class CustomInfoWindowAdapter implements MapboxMap.InfoWindowAdapter {

    private final View window;
    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
        window = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.titulo);
        if(!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.descripcion);
        if(!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker, window);
        return window;
    }
}
