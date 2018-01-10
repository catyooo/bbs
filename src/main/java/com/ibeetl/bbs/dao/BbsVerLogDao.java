package com.ibeetl.bbs.dao;

import com.ibeetl.bbs.model.BbsVerLog;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/23/0023
 * Time: 16:21
 */
public interface BbsVerLogDao extends BaseMapper<BbsVerLog> {

    /**
     * 获取最新版本信息
     * @return
     */
    List<BbsVerLog> getNewsVerLog();

    /**
     * 获取上一版本的更新日志
     * @return
     */
    List<BbsVerLog> getTheLastOneVL();

}
