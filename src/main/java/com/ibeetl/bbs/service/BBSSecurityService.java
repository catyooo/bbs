package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsSecurity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/6/0006
 * Time: 16:22
 */
public interface BBSSecurityService {

    List<BbsSecurity> checkSecurity(String user, String sfa, String ssa, String sta);

    void updateUserPass(String user, String np);
}
