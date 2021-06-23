package bigbigdw.joaradw;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import bigbigdw.joaradw.book_viewer.ViewerSetting;

public class ViewerSettingTest {
    // 해당 activity에서 실행하고자 함을 나타낸다.
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(ViewerSetting.class);

    @Test
    public void myTest() {
        // edit_text를 찾아서 거기에 crocus라는 내용을 적는다.
        Espresso.onView(ViewMatchers.withId(R.id.edit_text)).perform(ViewActions.typeText("crocus"), ViewActions.closeSoftKeyboard());
        // text_view를 찾아서 현재 textView에 담긴 내용이 crocus인지 확인한다. (assert이용)
        Espresso.onView(ViewMatchers.withId(R.id.text_view)).check(ViewAssertions.matches(ViewMatchers.withText("crocus")));
        // 버튼을 클릭하게 만들어 Toast가 나타나도록한다.(토스트는 앞선 MainActivity에 버튼 행동 정의)
        Espresso.onView(ViewMatchers.withId(R.id.btn)).perform(ViewActions.click());
    }
}
