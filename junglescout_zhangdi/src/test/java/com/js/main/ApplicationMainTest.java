package com.js.main;

import com.js.dao.AmazonInfoDao;
import com.js.model.AmazonInfoTaskEntityEntity;
import com.js.task.FetchTask;
import com.js.util.GlobalExecutorUtils;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AmazonInfoDao.class})
@PowerMockIgnore({"javax.net.ssl.*", "javax.security.*"})
public class ApplicationMainTest {


    @Before
    public void setup() throws SQLException {
        PowerMockito.mockStatic(AmazonInfoDao.class);
        PowerMockito.when(AmazonInfoDao.insertInfo(Mockito.any(AmazonInfoTaskEntityEntity.class))).thenReturn(1);
    }

    @After
    public void teardown() throws SQLException {
        GlobalExecutorUtils.getIOThreadPool().shutdown();
    }

    @Test
    public void formalResultTest() throws ExecutionException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String url = "https://www.amazon.com/dp/B002QYW8LW?th=1";
        long startStamp = System.currentTimeMillis();
        String taskId = startStamp + "_" + UUID.randomUUID();

        AmazonInfoTaskEntityEntity taskEntity = new AmazonInfoTaskEntityEntity();
        taskEntity.setTaskId(taskId);
        taskEntity.setStartStamp(startStamp);
        taskEntity.setUrl(url);
        taskEntity.setASIN("B002QYW8LW");

        FetchTask fetchTask = new FetchTask();

        CompletionStage future = fetchTask.processMsgAsync(taskEntity).whenComplete((resp, exp) -> {
            countDownLatch.countDown();
        });
        try {

            countDownLatch.await();
            Assert.assertEquals("B002QYW8LW", taskEntity.getASIN());
            Assert.assertEquals("4.8", taskEntity.getProductStar());
            Assert.assertEquals("Baby", taskEntity.getProductRank());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
