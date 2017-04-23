package kz.kamadi.yandextranslate.ui.splash;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class SplashActivityTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<>(
            SplashActivity.class);

    private SplashActivity splashActivity;

    @Before
    public void setUp() {
        splashActivity = activityTestRule.getActivity();
    }

    @Test
    public void testError() {
        splashActivity.runOnUiThread(() -> {
            splashActivity.handleError(new Exception());
        });
        onView(withId(R.id.error_text_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testMainActivityStart() {
        Intents.init();
        splashActivity.runOnUiThread(() -> {
            splashActivity.onLanguagesCreated();
        });

        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }
}