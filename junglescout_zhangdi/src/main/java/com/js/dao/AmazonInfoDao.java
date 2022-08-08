package com.js.dao;

import com.js.model.AmazonInfoTaskEntityEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmazonInfoDao {

    private static final Logger LOG = LoggerFactory.getLogger(AmazonInfoDao.class);

    /**
     *  Amazon key info save
     *
     * @param taskEntity  scrape task Entity
     * @Return int impacted number of rows
     * */
    public static int insertInfo(AmazonInfoTaskEntityEntity taskEntity) throws SQLException {
        String sql = "insert into amazon_info.amazon_info(task_id,start_stamp,end_stamp,url,ASIN,product_star,product_rank,task_state) values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstat = DBConnection.getConnection().prepareStatement(sql);
        pstat.setString(1, taskEntity.getTaskId());
        pstat.setString(2, String.valueOf(taskEntity.getStartStamp()));
        pstat.setString(3, String.valueOf(taskEntity.getEndStamp()));
        pstat.setString(4, taskEntity.getUrl());
        pstat.setString(5, taskEntity.getASIN());
        pstat.setString(6, taskEntity.getProductStar());
        pstat.setString(7, taskEntity.getProductRank());
        pstat.setInt(8, taskEntity.getTaskState());
        // 执行
        int rows = pstat.executeUpdate();
        LOG.debug("insert info success! {} rows affected!", rows);
        return rows;

    }

    //todo：sql语句后续可以使用mybatis进行优化

}
