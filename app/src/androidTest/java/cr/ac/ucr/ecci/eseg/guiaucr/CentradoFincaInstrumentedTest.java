package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CentradoFincaInstrumentedTest {
    public static final String LAT_FINCA_1 = "9.937423";
    public static final String LON_FINCA_1 = "-84.050419";
    public static final String LAT_FINCA_2 = "9.943743";
    public static final String LON_FINCA_2 = "-84.044856";
    public static final String LAT_FINCA_3 = "9.938433";
    public static final String LON_FINCA_3 = "-84.043687";
    private Context mContext;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testIntentCentrado(){
        mContext = ApplicationProvider.getApplicationContext();
        ActivityScenario scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.centrar)).perform(click());
        intended(allOf(hasExtra("codigo",2)));
    }

}
