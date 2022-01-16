package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class LogoutTest {

    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);
    FirebaseAuth fAuth;

    /**
     * Prerrequisito: haber iniciado sesion
     * */
    @Test
    public void testLougout() throws InterruptedException {
        fAuth = FirebaseAuth.getInstance();
        onView(withId(R.id.drawer_menu_image)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.cerrarSesionButton)).perform(click());
        FirebaseUser user = fAuth.getCurrentUser();
        assert(user == null);
    }

}
