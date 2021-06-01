package edu.northwesternmall.demo.controller;


import edu.northwesternmall.demo.common.ApiRestResponse;
import edu.northwesternmall.demo.common.Constant;
import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.pojo.User;
import edu.northwesternmall.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @CrossOrigin
    @GetMapping("/test")
    @ResponseBody
    public User personalPage(){
     return userService.getUser();
    }



    @CrossOrigin
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws MallException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty(username)){
            return ApiRestResponse.error(ExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ExceptionEnum.NEED_PASSWORD);
        }

        if(password.length() <8){
            return ApiRestResponse.error(ExceptionEnum.PASSWORD_TOSHORT);
        }

        userService.register(username,password);
        return ApiRestResponse.success();

    }

    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) throws MallException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty(username)){
            return ApiRestResponse.error(ExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ExceptionEnum.NEED_PASSWORD);
        }
        User user =userService.login(username,password);
        user.setPassword(null);
        httpSession.setAttribute(Constant.MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    @CrossOrigin
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession httpSession,@RequestParam("signature") String signature) throws MallException {
        User currentUser = (User)httpSession.getAttribute(Constant.MALL_USER);
        if(currentUser == null){
            return ApiRestResponse.error(ExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }


    @CrossOrigin
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession httpSession){
        httpSession.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }


    @CrossOrigin
    @PostMapping("/adminlogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) throws MallException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty(username)){
            return ApiRestResponse.error(ExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ExceptionEnum.NEED_PASSWORD);
        }
        User user =userService.login(username,password);
        if(userService.checkAdminRole(user)){
            user.setPassword(null);
            httpSession.setAttribute(Constant.MALL_USER,user);
            return ApiRestResponse.success(user);
        }else{
            return ApiRestResponse.error(ExceptionEnum.NOT_ADMIN);
        }
    }


}
