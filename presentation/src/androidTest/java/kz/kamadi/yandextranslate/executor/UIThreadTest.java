package kz.kamadi.yandextranslate.executor;

import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static junit.framework.Assert.assertEquals;

public class UIThreadTest  {
    @Test
    public void testExecute() {

        assertEquals(new UIThread().getScheduler(), AndroidSchedulers.mainThread());
    }
}
