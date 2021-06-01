package edu.northwesternmall.demo.service.impl;

import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.dao.UserMapper;
import edu.northwesternmall.demo.model.pojo.User;
import edu.northwesternmall.demo.service.UserService;
import edu.northwesternmall.demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(String username, String password) throws MallException, NoSuchAlgorithmException {
        User result =userMapper.selectByName(username);
        if(result != null){
            throw new MallException(ExceptionEnum.NAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.getMD5Str(password));

        int count =userMapper.insertSelective(user);
        if(count == 0){
            throw new MallException(ExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String username, String password) throws NoSuchAlgorithmException, MallException {
        String md5Password = null;
        md5Password = MD5Utils.getMD5Str(password);
        User user =userMapper.selectLogin(username,md5Password);
        if(user == null){
            throw new MallException(ExceptionEnum.WRONG_PASSWORD);
        }

        return user;

    }

    @Override
    public void updateInformation(User user) throws MallException {
        int updateCount =userMapper.updateByPrimaryKeySelective(user);
        if(updateCount >1){
            throw new MallException(ExceptionEnum.UPDATE_FAIL);
        }
    }



    @Override
    public boolean checkAdminRole(User user){
        if(user.getRole().equals(2)){
            return true;
        }else{
            return false;
        }
    }

}
