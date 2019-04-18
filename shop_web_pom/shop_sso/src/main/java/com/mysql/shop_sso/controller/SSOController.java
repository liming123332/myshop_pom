package com.mysql.shop_sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.myshop.entity.Email;
import com.myshop.entity.User;
import com.myshop.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/toLogin")
    public String toLogin(String returnUrl,Model model){
        System.out.println(returnUrl);
        model.addAttribute("returnUrl",returnUrl);
        return "login";
    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/register")
    public String register(User user){
        userService.register(user);
        Email email=new Email();
        email.setTo(user.getEmail());
        email.setSubject("卢涛科技激活");
        email.setCreatetime(new Date());
        String uuid=UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("email_token"+user.getUsername(),uuid);
        redisTemplate.expire("email_token",5,TimeUnit.MINUTES);
        email.setContent("<a href='http://localhost:8084/sso/active?username="+user.getUsername()+"&email_token="+uuid+"'>激活</a>");
        rabbitTemplate.convertAndSend("email_queue", email);
        return "login";
    }

    @RequestMapping("/active")
    public String active(String username,String email_token){
        System.out.println(username);
        String redisUUid = (String) redisTemplate.opsForValue().get("email_token"+username);
        if(redisUUid.equals(email_token)){
            userService.activeByUsername(username);
            redisTemplate.delete("email_token"+username);
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password,String returnUrl, Model model, HttpServletResponse response){
        User user = userService.login(username, password);
        if(null==user){
            model.addAttribute("error", "0");
            System.out.println(0);
            //登录失败
            return "login";
        }else if(0==user.getStatus()){
            //未激活
            model.addAttribute("error","1");
            System.out.println(1);
            String mail = user.getEmail();
            int index = mail.indexOf("@");
            String tomail = "http://mail." + mail.substring(index + 1);

            model.addAttribute("tomail", tomail);
            return "login";
        }
        String tokenUUID= UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(tokenUUID,user);
        redisTemplate.expire(tokenUUID,5, TimeUnit.DAYS);
        Cookie cookie=new Cookie("login_token",tokenUUID);
        cookie.setMaxAge(60*60*24*5);
        cookie.setPath("/");
        response.addCookie(cookie);
        if(null==returnUrl||"".equals(returnUrl)) {
            returnUrl = "http://localhost:8081/";
        }
        return "redirect:"+returnUrl;
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public String isLogin(@CookieValue(value = "login_token",required = false)String login_token){
        User user =null;
        if(null!=login_token){
            user=(User) redisTemplate.opsForValue().get(login_token);
        }
        if(null==user){
            return "isLogin(null)";
        }
        return "isLogin('"+ JSON.toJSONString(user) +"')";
    }

    @RequestMapping("/loginout")
    public String loginout(@CookieValue("login_token")String login_token,HttpServletResponse response){
        redisTemplate.delete(login_token);
        Cookie cookie=new Cookie(login_token, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/sso/toLogin";
    }

}
