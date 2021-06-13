package jp.terameteo.dayaction202105


import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  //   @Rule val activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("jp.terameteo.dayaction202105", appContext.packageName)
    }
    @Test
    fun isActivityViewed(){
        Espresso.onView(ViewMatchers.withId(R.id.achievement))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}