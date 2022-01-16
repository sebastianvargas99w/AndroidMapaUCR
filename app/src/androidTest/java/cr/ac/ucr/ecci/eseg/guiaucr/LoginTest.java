package cr.ac.ucr.ecci.eseg.guiaucr;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    public static final String CORREO_USUARIO = "pruebadesprint@gmail.com";
    public static final String CONTRASENA_USUARIO = "123456";
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);
    EditText correoLogin;
    EditText contrasenaLogin;
    FirebaseAuth fAuth;
    @Test
    public void testIdentificacion() throws InterruptedException {
        fAuth = FirebaseAuth.getInstance();
        onView(withId(R.id.correoLogin)).perform(typeText(CORREO_USUARIO));
        onView(withId(R.id.contrasenaLogin)).perform(typeText(CONTRASENA_USUARIO), pressBack());
        onView(withId(R.id.buttonLogin)).perform(click());
        Thread.sleep(4000);
        FirebaseUser user = fAuth.getCurrentUser();
        assertNotNull(user);
    }

}