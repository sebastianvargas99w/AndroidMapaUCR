package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FincaFocusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finca_focus);

        Button buttonFinca1 = (Button)findViewById(R.id.buttonFinca1);
        buttonFinca1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("finca",1);
                setResult(1,intent);
                finish();
            }
        });

        Button buttonFinca2 = (Button)findViewById(R.id.buttonFinca2);
        buttonFinca2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("finca",2);
                LocalBroadcastManager.getInstance(FincaFocusActivity.this).sendBroadcast(intent);
                setResult(2,intent);
                finish();
            }
        });

        Button buttonFinca3 = (Button)findViewById(R.id.buttonFinca3);
        buttonFinca3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("finca",3);
                setResult(3,intent);
                finish();
            }
        });
    }
}