package edu.northwesternmall.demo.controller;


import edu.northwesternmall.demo.common.ApiRestResponse;
import edu.northwesternmall.demo.common.Constant;
import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.pojo.Game;
import edu.northwesternmall.demo.model.pojo.User;
import edu.northwesternmall.demo.model.request.AddGameReq;
import edu.northwesternmall.demo.model.request.UpdateGameReq;
import edu.northwesternmall.demo.service.GameService;
import edu.northwesternmall.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;
    @CrossOrigin
    @ApiOperation("display games")
    @GetMapping("game/list")
    @ResponseBody
    public ApiRestResponse DisplayAllGames(){
        List<Game> myList = new ArrayList<>();
        myList = gameService.DisplayAllVideoGames();
        return ApiRestResponse.success(myList);
    }


    @CrossOrigin
    @ApiOperation("Create game")
    @PostMapping("game/create")
    @ResponseBody
    public ApiRestResponse addGame(@Valid @RequestBody AddGameReq addGameReq,HttpSession httpSession) throws MallException {


        Game game = new Game();
        BeanUtils.copyProperties(addGameReq,game);
        gameService.add(addGameReq);
        return ApiRestResponse.success();
    }


    @CrossOrigin
    @ApiOperation("update game")
    @PostMapping("game/update")
    @ResponseBody
    public ApiRestResponse updateGame(@Valid @RequestBody UpdateGameReq updateGameReq,HttpSession httpSession) throws MallException {



        Game game = new Game();
        BeanUtils.copyProperties(updateGameReq,game);
        gameService.update(game);
        return ApiRestResponse.success();
    }



    @CrossOrigin
    @ApiOperation("delte game")
    @PostMapping("game/delete")
    @ResponseBody
    public ApiRestResponse deleteGame(@RequestParam Integer id, HttpSession httpSession) throws MallException{

        gameService.delete(id);
        return ApiRestResponse.success();
    }





}
