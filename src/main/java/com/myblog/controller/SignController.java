package com.myblog.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.myblog.entity.UserEntity;
import com.myblog.service.UserService;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * @author:zyk
 * @Description:
 * @Date:Create in 11:00 2017/11/22
 * @Modified By:
 **/
@Controller
public class SignController {
	
	 @Autowired
	 private UserService userService;

    
    @RequestMapping( value = "/signIn")
    public String signIn(Model model) {
        return "content/sign_in";
    }
    
    @RequestMapping( value = "/signUp")
    public String signUp(Model model) {
        return "content/sign_up";
    }
    
    /**
     * 生成qq授权
     * @param request
     * @return
     * @throws QQConnectException
     */
    @RequestMapping( value = "/authorieUrl")
    public String authorieUrl(HttpServletRequest request) throws QQConnectException{
    	String authorieUrl = new Oauth().getAuthorizeURL(request);
    	return "redirect:" + authorieUrl;
    }
    
    /**
	 *	1、生成授权连接  需要配置回调地址
	 *	https://graphttps://graph.qq.com/oauth2.0/show?which=Login&display=pc&response_type=code&client_id=101442461&redirect_uri=http://127.0.0.1:8088/blog/index&state=123456
	 *	2、根据授权码获取assessToken
	 *	https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=101442461&client_secret=bee3013a028a9ce371efcd0eb69ed0dc
	 *	&code=AAB635026C406CD69FFA6590C62FA8A3&redirect_uri=http://127.0.0.1:8088/blog/index
	 *	3、使用assessToken换取openid
	 * 	https://graph.qq.com/oauth2.0/me?access_token=3BE65D68948294C814BDF7AEDC1677DF
	 *	4、拿个人信息
	 *	https://graph.qq.com/user/get_user_info?access_token=3BE65D68948294C814BDF7AEDC1677DF&oauth_consumer_key=101442461&openid=18DC63B1C663DB11F77BF94836B63876
	 *	http://www.wh1t3zz.com/
     * 回调url
     * @param request
     * @return
     * @throws QQConnectException
     */
    @RequestMapping( value = "/index")
    public String qqLoginCallback(HttpServletRequest request,Model model) throws QQConnectException{
    	//获取授权码
    	AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
    	//accessToken
    	String accessToken = accessTokenObj.getAccessToken();
    	if(StringUtils.isEmpty(accessToken)){
    		return "qq授权失败";
    	}
    	//获取openid
    	OpenID openidObj = new OpenID(accessToken);
//    	if(openidObj == null){
//    		return "qq授权失败";
//    	}
    	//数据库查找openid 是否关联， 如果没有关联  跳转关联页面  如果有直接登录
    	String userOpenId = openidObj.getUserOpenID();
    	UserEntity userOpen =userService.userLoginOpendId(userOpenId);
    	/*if(userOpen == null){//没有关联  进行关联(可以通过手机号绑定关联系统)
    		return "index/index2";
    	}*/ 
    	UserInfo qzoneUserInfo = new UserInfo(accessToken, userOpenId);
        UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
        if (userInfoBean.getRet() == 0) {
            System.out.println(userInfoBean.getNickname());
            System.out.println(userInfoBean.getGender());
            System.out.println("黄钻等级： " + userInfoBean.getLevel());
            System.out.println("会员 : " + userInfoBean.isVip());
            System.out.println("黄钻会员： " + userInfoBean.isYellowYearVip());
            System.out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL30());
            System.out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL50());
            System.out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100());
        }
    	model.addAttribute("name",userInfoBean.getNickname());
    	model.addAttribute("gender",userInfoBean.getGender());
    	model.addAttribute("avatar",userInfoBean.getAvatar().getAvatarURL100());
    	return "index/index";
    }
}
