package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsVerLog;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/23/0023
 * Time: 16:33
 */
public interface BBSVerLogService {

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

    /**
     * 保存版本信息
     * @param bbsVerLog
     */
    void saveVerLog(BbsVerLog bbsVerLog);
}
