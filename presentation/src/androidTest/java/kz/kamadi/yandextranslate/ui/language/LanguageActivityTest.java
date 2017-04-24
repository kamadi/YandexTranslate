package kz.kamadi.yandextranslate.ui.language;

import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Language;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static kz.kamadi.yandextranslate.ui.language.LanguageActivity.SOURCE;
import static kz.kamadi.yandextranslate.ui.language.LanguageActivity.SOURCE_LANGUAGE;
import static kz.kamadi.yandextranslate.ui.language.LanguageActivity.TARGET_LANGUAGE;
import static kz.kamadi.yandextranslate.ui.language.LanguageActivity.TYPE;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LanguageActivityTest {

    @Rule
    public final ActivityTestRule<LanguageActivity> activityTestRule = new ActivityTestRule<>(
            LanguageActivity.class, true, false);

    private LanguageActivity languageActivity;
    private List<Language> languages = Arrays.asList(
            new Language("English", "en"),
            new Language("Русский", "ru"),
            new Language("Қазақша", "kk"));

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(TYPE, SOURCE);
        intent.putExtra(SOURCE_LANGUAGE, new Language("English", "en"));
        intent.putExtra(TARGET_LANGUAGE, new Language("Русский", "ru"));
        activityTestRule.launchActivity(intent);
        languageActivity = activityTestRule.getActivity();
    }

    @Test
    public void testTitle() {
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.source_language)));
    }

    @Test
    public void testLanguagesDisplay() {
        languageActivity.runOnUiThread(() -> {
            languageActivity.showLanguages(languages);
        });
        onView(withText("English")).check(matches(isDisplayed()));
        onView(withText("Русский")).check(matches(isDisplayed()));
        onView(withText("Қазақша")).check(matches(isDisplayed()));
    }
}
