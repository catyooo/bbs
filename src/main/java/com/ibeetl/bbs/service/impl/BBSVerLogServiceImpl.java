package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.dao.BbsVerLogDao;
import com.ibeetl.bbs.model.BbsVerLog;
import com.ibeetl.bbs.service.BBSVerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/23/0023
 * Time: 16:33
 */
@Service
@Transactional
public class BBSVerLogServiceImpl implements BBSVerLogService{

    @Autowired
    private BbsVerLogDao bbsVerLogDao;


    @Override
    public List<BbsVerLog> getNewsVerLog() {
        return bbsVerLogDao.getNewsVerLog();
    }

    @Override
    public List<BbsVerLog> getTheLastOneVL() {
        return bbsVerLogDao.getTheLastOneVL();
    }

    @Override
    @CacheEvict(cacheNames="TOPIC", allEntries=true)
    public void saveVerLog(BbsVerLog bbsVerLog) {
        bbsVerLog.setCreateTime(new Date());
        bbsVerLogDao.insert(bbsVerLog, true);
    }
}
