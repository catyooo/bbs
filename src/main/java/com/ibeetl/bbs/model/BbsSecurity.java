package com.ibeetl.bbs.model;

import org.beetl.sql.core.TailBean;

/**
 * 密保问题bean
 *
 * Created by IntelliJ IDEA.
 * User: zhangchao
 * Date: 2017/11/6/0006
 * Time: 10:15
 */
public class BbsSecurity extends TailBean {


    // 密保问题一答案
    private String security_first_answer;

    // 密保问题二答案
    private String security_second_answer;

    // 密保问题三答案
    private String security_third_answer;


    // 用户名
    private String user;

    // 新密码
    private String newPass;

    // 确认新密码
    private String checkNewPass;



    public String getSecurity_first_answer() {
        return security_first_answer;
    }

    public void setSecurity_first_answer(String security_first_answer) {
        this.security_first_answer = security_first_answer;
    }

    public String getSecurity_second_answer() {
        return security_second_answer;
    }

    public void setSecurity_second_answer(String security_second_answer) {
        this.security_second_answer = security_second_answer;
    }

    public String getSecurity_third_answer() {
        return security_third_answer;
    }

    public void setSecurity_third_answer(String security_third_answer) {
        this.security_third_answer = security_third_answer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getCheckNewPass() {
        return checkNewPass;
    }

    public void setCheckNewPass(String checkNewPass) {
        this.checkNewPass = checkNewPass;
    }
}
