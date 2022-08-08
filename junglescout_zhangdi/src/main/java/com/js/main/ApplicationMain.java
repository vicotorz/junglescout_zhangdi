package com.js.main;

import com.js.constant.CommonConstant;
import com.js.constant.RegxConstant;
import com.js.dao.DBConnection;
import com.js.model.AmazonInfoTaskEntityEntity;
import com.js.task.FetchTask;
import com.js.util.GlobalExecutorUtils;
import java.sql.SQLException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scrape Info from Amazon
 * <p>
 * original description:https://github.com/junglescout-cn/zhangdi_vicotorz
 * </p>
 */
public class ApplicationMain {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws SQLException {
        try {

            if (args == null || args.length <= 0) {
                return;
            }
            String[] ASINS = args[0].split(CommonConstant.SPLIT_MARK);
            for (String ASIN : ASINS) {
                if (!ASIN.matches(RegxConstant.ASIN_REGEX_DETAIL)) {
                    LOG.error("ASIN:{} is illegal", ASIN);
                    continue;
                }
                String url = CommonConstant.AMAZON_WEBSITE + ASIN + CommonConstant.AMAZON_PRO_LABEL;
                long startStamp = System.currentTimeMillis();
                String taskId = startStamp + "_" + UUID.randomUUID();

                AmazonInfoTaskEntityEntity taskEntity = new AmazonInfoTaskEntityEntity();
                taskEntity.setTaskId(taskId);
                taskEntity.setStartStamp(startStamp);
                taskEntity.setUrl(url);
                taskEntity.setASIN(ASIN);

                FetchTask fetchTask = new FetchTask();
                try {
                    fetchTask.processMsgAsync(taskEntity);
                } catch (Exception e) {
                    LOG.error("ApplicationMain FetchTask occurs unexpected error!{}", e);
                }
            }
            GlobalExecutorUtils.getIOThreadPool().shutdown();
            while (true) {
                if (GlobalExecutorUtils.getIOThreadPool().isTerminated()) {
                    LOG.info("All tasks have finished!");
                    break;
                }
                Thread.sleep(30 * 1000);
            }
        } catch (Exception e) {
            LOG.error("ApplicationMain occurs unexpected error!{}", e);
        } finally {
            LOG.info("Finally!");
            DBConnection.getConnection().close();
        }
    }

}
