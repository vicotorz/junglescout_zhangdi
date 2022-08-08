package com.js.task;

import static com.js.util.StringFilterUtil.getTextFromHtml;

import com.js.constant.CommonConstant;
import com.js.constant.RegxConstant;

import com.js.constant.TaskState;
import com.js.dao.AmazonInfoDao;
import com.js.model.AmazonInfoTaskEntityEntity;
import com.js.util.GlobalExecutorUtils;
import com.js.util.HttpUtil;
import com.js.util.RegexStrUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FetchTask {

    private static final Logger LOG = LoggerFactory.getLogger(FetchTask.class);

    public CompletionStage<?> processMsgAsync(AmazonInfoTaskEntityEntity taskEntity) {

            return CompletableFuture.supplyAsync(() -> {
                int taskResult;
                try {
                    taskResult = fetchAmazonDetail(taskEntity);
                } catch (Exception e) {
                    LOG.error("FetchTask processMsgAsync occurs Exception:{}", e);
                    return e;
                }
                return taskResult;
            }, GlobalExecutorUtils.getIOThreadPool());

    }

    private int fetchAmazonDetail(AmazonInfoTaskEntityEntity amazonInfoTaskEntity) {
        String result = HttpUtil.doGetString(amazonInfoTaskEntity.getUrl(), null);
        String fliterStr = getTextFromHtml(result);

        ArrayList<String> patternList = new ArrayList<>();
        patternList.add(RegxConstant.PRO_STAR_ORIGIN);
        patternList.add(RegxConstant.PRO_RANK_ORIGIN);

        ArrayList<String> resultList = RegexStrUtil.getRegexStr(fliterStr, patternList);

        String ASIN = amazonInfoTaskEntity.getASIN();
        String product_star = RegexStrUtil.getRegexStrDetail(resultList.get(0), RegxConstant.PRO_STAR_DETAIL);
        String product_rank = RegexStrUtil.getRegexStrDetail(resultList.get(1), RegxConstant.PRO_RANK_DETAIL)
                .replaceAll(RegxConstant.PRO_RANK_REPLACE_PATTERN_PREFIX, "")
                .replaceAll(RegxConstant.PRO_RANK_REPLACE_PATTERN_SUFFIX, "");

        long endStamp = System.currentTimeMillis();
        amazonInfoTaskEntity.setEndStamp(endStamp);
        amazonInfoTaskEntity.setProductStar(product_star);
        amazonInfoTaskEntity.setProductRank(product_rank);
        amazonInfoTaskEntity.setTaskState(TaskState.FINISHED.getStateCode());
        try {
            int ret = AmazonInfoDao.insertInfo(amazonInfoTaskEntity);
            if (ret >= 1 && LOG.isDebugEnabled()) {
                LOG.info("{} has saved!", ASIN);
            }
        } catch (SQLException e) {
            LOG.error("FetchTask fetchAmazonDetail occurs Exception:{}", e);
            return TaskState.FAILED.getStateCode();
        }

        return TaskState.FINISHED.getStateCode();
    }

}
