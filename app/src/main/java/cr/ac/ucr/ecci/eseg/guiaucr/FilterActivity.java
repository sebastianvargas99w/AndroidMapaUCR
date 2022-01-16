package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        loadConfiguration();

        Button aplicar = (Button) findViewById(R.id.aplicar);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private String getPreferenceValue(String filtro)
    {
        SharedPreferences sp = getSharedPreferences("filtros",0);
        String str = sp.getString(filtro,"on");
        return str;
    }

    private void writeToPreference(String filtro, String valor)
    {
        SharedPreferences.Editor editor = getSharedPreferences("filtros",0).edit();
        editor.putString(filtro, valor);
        editor.commit();
    }

    private void saveChanges() {
        Switch filtro_facultad = (Switch) findViewById(R.id.facultad_switch);
        Switch filtro_parada = (Switch) findViewById(R.id.parada_switch);
        Switch filtro_soda = (Switch) findViewById(R.id.soda_switch);
        Switch filtro_fotocopiadora = (Switch) findViewById(R.id.fotocopiadora_switch);
        writeToPreference("facultad", (filtro_facultad.isChecked()?"on":"off"));
        writeToPreference("parada", (filtro_parada.isChecked()?"on":"off"));
        writeToPreference("soda", (filtro_soda.isChecked()?"on":"off"));
        writeToPreference("fotocopiadora", (filtro_fotocopiadora.isChecked()?"on":"off"));
    }

    private void loadConfiguration() {
        Switch filtro_facultad = (Switch) findViewById(R.id.facultad_switch);
        Switch filtro_parada = (Switch) findViewById(R.id.parada_switch);
        Switch filtro_soda = (Switch) findViewById(R.id.soda_switch);
        Switch filtro_fotocopiadora = (Switch) findViewById(R.id.fotocopiadora_switch);
        filtro_facultad.setChecked(getPreferenceValue("facultad").equals("on"));
        filtro_parada.setChecked(getPreferenceValue("parada").equals("on"));
        filtro_soda.setChecked(getPreferenceValue("soda").equals("on"));
        filtro_fotocopiadora.setChecked(getPreferenceValue("fotocopiadora").equals("on"));
    }
}