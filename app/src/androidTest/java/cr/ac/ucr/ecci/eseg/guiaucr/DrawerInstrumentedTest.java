package cr.ac.ucr.ecci.eseg.guiaucr;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DrawerInstrumentedTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);


    @Test
    public void AboutTest(){
        onView(withId(R.id.drawer_menu_image)).perform(click());
        onView(withId(R.id.aboutButton)).perform(click());
        intended(hasComponent(AboutActivity.class.getName()));
    }
}
