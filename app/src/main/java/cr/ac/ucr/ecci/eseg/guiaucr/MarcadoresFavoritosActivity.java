package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MarcadoresFavoritosActivity extends AppCompatActivity {

    private ListView mListView;
    private MarcadorFavoritoListAdapter adapter;
    MarcadorFavorito marcadorFavorito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcadores_favoritos);

        adapter = new MarcadorFavoritoListAdapter(this, MarcadorFavorito.leer(getApplicationContext()));

        mListView = (ListView) findViewById(R.id.favoritos_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                marcadorFavorito = (MarcadorFavorito) adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), MarcadorFavoritoDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("favorito", marcadorFavorito);

                startActivityForResult(intent, marcadorFavorito.getId());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setResult(marcadorFavorito.getId(), data);
        finish();
    }
}
