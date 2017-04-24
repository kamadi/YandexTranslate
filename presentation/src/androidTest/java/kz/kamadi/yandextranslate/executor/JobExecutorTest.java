package kz.kamadi.yandextranslate.executor;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.ThreadPoolExecutor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class JobExecutorTest {

    private JobExecutor jobExecutor;
    private ThreadPoolExecutor threadPoolExecutor;
    private JobExecutor.JobThreadFactory jobThreadFactory;

    @Before
    public void setUp() throws Exception {
        this.jobExecutor = new JobExecutor();
        this.threadPoolExecutor = this.jobExecutor.threadPoolExecutor;
        this.jobThreadFactory = new JobExecutor.JobThreadFactory();
    }

    @After
    public void tearDown() throws Exception {
        this.threadPoolExecutor.shutdown();
    }

    @Test
    public void testExecute() {
        ThreadPoolExecutor mockThreadPoolExecutor = Mockito.mock(ThreadPoolExecutor.class);
        this.jobExecutor.threadPoolExecutor = mockThreadPoolExecutor;

        this.jobExecutor.execute(Mockito.mock(Runnable.class));

        verify(mockThreadPoolExecutor).execute(any(Runnable.class));
    }

    @Test
    public void testParams() {
        assertEquals(JobExecutor.INITIAL_POOL_SIZE, this.threadPoolExecutor.getCorePoolSize());
        assertEquals(JobExecutor.MAX_POOL_SIZE, this.threadPoolExecutor.getMaximumPoolSize());
        assertEquals(JobExecutor.KEEP_ALIVE_TIME,
                this.threadPoolExecutor.getKeepAliveTime(JobExecutor.KEEP_ALIVE_TIME_UNIT));
    }

    @Test
    public void testJobThreadFactory() {
        Runnable mockCommand = Mockito.mock(Runnable.class);
        Thread one = this.jobThreadFactory.newThread(mockCommand);
        Thread two = this.jobThreadFactory.newThread(mockCommand);

        one.run();
        two.run();

        verify(mockCommand, times(2)).run();
        assertNotSame(one, two);
        assertEquals(JobExecutor.JobThreadFactory.THREAD_NAME + "0", one.getName());
        assertEquals(JobExecutor.JobThreadFactory.THREAD_NAME + "1", two.getName());
        one.interrupt();
        two.interrupt();
    }

}
