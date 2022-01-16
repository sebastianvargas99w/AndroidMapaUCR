package cr.ac.ucr.ecci.eseg.guiaucr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MarcadorFavoritoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcadores_favoritos_details);

        MarcadorFavorito item = getIntent().getExtras().getParcelable("favorito");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_favoritos_placeholder, new MarcadorFavoritoDetailsFragment(item));
        ft.commit();

    }

}
