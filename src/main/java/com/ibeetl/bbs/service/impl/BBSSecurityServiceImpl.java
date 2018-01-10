package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.dao.BbsSecurityDao;
import com.ibeetl.bbs.model.BbsSecurity;
import com.ibeetl.bbs.service.BBSSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/6/0006
 * Time: 16:22
 */
@Service
public class BBSSecurityServiceImpl implements BBSSecurityService {

    @Autowired
    private BbsSecurityDao bbsSecurityDao;

    @Override
    public List<BbsSecurity> checkSecurity(String user, String sfa, String ssa, String sta) {
        return bbsSecurityDao.checkSecurity(user, sfa, ssa, sta);
    }

    @Override
    public void updateUserPass(String user, String np) {
        bbsSecurityDao.updateUserPass(user, np);
    }


}
