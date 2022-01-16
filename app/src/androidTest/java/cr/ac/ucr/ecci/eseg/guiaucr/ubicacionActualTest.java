package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.mapboxsdk.maps.MapView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



import static junit.framework.TestCase.assertEquals;




/** Pre-requisito
 * Estar logueado en el sistema.
 * La ubicación actual debe ser: latitud: 9.9361659786", longitud: -84.0504527092
 */
@RunWith(AndroidJUnit4.class)
public class ubicacionActualTest{
    public static final String LAT_UBICACION_ACTUAL = "9.9361659786";
    public static final String LON_UBICACION_ACTUAL = "-84.0504527092";
    private Context mContext;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    private int mapView;

    @Test
    public void testUbicacionActual() {

        mContext = ApplicationProvider.getApplicationContext();
        ActivityScenario scenario = activityScenarioRule.getScenario();

        onView(withId(R.id.localizacion)).perform(click());
/*
        //Falta comparacion resultados con datos de prueba
        assertEquals(R.attr.mapbox_cameraTargetLat,LAT_UBICACION_ACTUAL);
        assertEquals(R.attr.mapbox_cameraTargetLng,LON_UBICACION_ACTUAL);
        */
    }
}
