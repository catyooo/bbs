package com.ibeetl.bbs.action;

import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.common.WebUtils;
import com.ibeetl.bbs.model.BbsSecurity;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSSecurityService;
import com.ibeetl.bbs.service.BbsUserService;
import com.ibeetl.bbs.util.HashKit;
import com.ibeetl.bbs.util.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


@Controller
public class LoginController {


    @Autowired
    SQLManager sql;

    @Autowired
    BbsUserService bbsUserService;

    @Autowired
    BBSSecurityService bbsSecurityService;

    static final String CODE_NAME = "verCode";


    /**
     * 登录方法改为ajax方式登录
     *
     * @param userName
     * @param password
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/bbs/user/login")
    public JSONObject login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("err", 1);
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            result.put("msg", "请输入正确的内容！");
        } else {
            password = HashKit.md5(password);
            BbsUser user = bbsUserService.getUserAccount(userName, password);
            if (user == null) {
                result.put("msg", "用户不存在或密码错误");
            } else {
                WebUtils.loginUser(request, response, user, true);
                result.put("msg", "/bbs/index/1.html");
                result.put("err", 0);
            }
        }
        return result;
    }

    @GetMapping("/bbs/user/register.html")
    public ModelAndView loginPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/register.html");
        return view;
    }

    /**
     * 登出方法改为ajax方式登出
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @PostMapping("/bbs/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        WebUtils.logoutUser(request, response);
    }

    /**
     * 注册改为 ajax 方式注册
     *
     * @param user
     * @param code
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/bbs/user/doRegister")
    public JSONObject register(BbsUser user, String code, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("err", 1);
        HttpSession session = request.getSession(true);
        String verCode = (String) session.getAttribute(CODE_NAME);
        if (!verCode.equalsIgnoreCase(code)) {
            result.put("msg", "你觉得验证码不正确，会给通过吗？");
        } else if (bbsUserService.hasUser(user.getUserName())) {
            result.put("msg", "巧了，有跟你同名同姓的！");
        } else {
            // 校验密保答案完整性
            if (StringUtils.isEmpty(user.getSecurityFirstAnswer())
                    || StringUtils.isEmpty(user.getSecuritySecondAnswer())
                    || StringUtils.isEmpty(user.getSecurityThirdAnswer())) {
                result.put("err", 1);
                result.put("msg", "你觉得没输入完整，会给通过吗？");
            // 新增保存
            } else {
                String password = HashKit.md5(user.getPassword());
                user.setPassword(password);
                user.setBalance(10);
                user.setLevel(1);
                user.setScore(10);
                user = bbsUserService.setUserAccount(user);
                WebUtils.loginUser(request, response, user, true);
                result.put("err", 0);
                result.put("msg", "/bbs/index");
            }
        }
        return result;
    }

    /**
     * 忘记密码
     *
     * @param security
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/bbs/user/doSecurity")
    public JSONObject security(BbsSecurity security, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();

        try {
            String sfa = security.getSecurity_first_answer();
            String ssa = security.getSecurity_second_answer();
            String sta = security.getSecurity_third_answer();
            String user = security.getUser();
            String np = security.getNewPass();
            String cnp = security.getCheckNewPass();

            // 校验数据完整性
            if (StringUtils.isEmpty(sfa) || StringUtils.isEmpty(ssa) || StringUtils.isEmpty(sta)
                    || StringUtils.isEmpty(user) ||StringUtils.isEmpty(np) || StringUtils.isEmpty(cnp)) {
                result.put("err", 1);
                result.put("msg", "你觉得没输入完整，会给通过吗？");
            // 确认密码是否一直
            } else if (!np.equals(cnp)){
                result.put("err", 1);
                result.put("msg", "两次输的密码不同！拿烟的手，微微颤抖？");
            } else {
                // 验证密保是否通过
                if (bbsSecurityService.checkSecurity(user, sfa,ssa, sta).size() == 0) {
                    result.put("err", 1);
                    result.put("msg", "颤抖！验证不通过，多吃六个核桃！建议cell作者找回！");
                // 验证通过更新密码
                } else {
                    // 更新
                    bbsSecurityService.updateUserPass(user, HashKit.md5(security.getNewPass()));
                    BbsUser userLogin = new BbsUser();
                    // 取id
                    userLogin.setId((Integer) bbsSecurityService.checkSecurity(user, sfa,ssa, sta).get(0).get("id"));
                    WebUtils.loginUser(request, response, userLogin, true);
                    result.put("err", 0);
                    result.put("msg", "/bbs/index");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            result.put("err", 1);
            result.put("msg", "验证时出错了！颤抖吧！");
        }

        return result;
    }

    @RequestMapping("/bbs/user/authImage")
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute(CODE_NAME);
        session.setAttribute(CODE_NAME, verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }


}
