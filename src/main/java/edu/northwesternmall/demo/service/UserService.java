package edu.northwesternmall.demo.service;

import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.pojo.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    User getUser();
    void register(String username,String password) throws MallException, NoSuchAlgorithmException;

    User login(String username, String password) throws NoSuchAlgorithmException, MallException;

    void updateInformation(User user) throws MallException;

    boolean checkAdminRole(User user);
}
