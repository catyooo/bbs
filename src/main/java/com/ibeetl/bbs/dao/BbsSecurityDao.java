package com.ibeetl.bbs.dao;

import com.ibeetl.bbs.model.BbsSecurity;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/6/0006
 * Time: 16:03
 */
public interface BbsSecurityDao extends BaseMapper<BbsSecurity> {


    @SqlStatement(params="user,sfa,ssa,sta", returnType=Integer.class)
    List<BbsSecurity> checkSecurity(String user, String sfa, String ssa,String sta);

    @SqlStatement(params="user,np")
    void updateUserPass(String user, String np);

}
