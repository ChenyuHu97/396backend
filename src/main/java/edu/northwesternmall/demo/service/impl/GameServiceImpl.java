package edu.northwesternmall.demo.service.impl;

import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.dao.GameMapper;
import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.pojo.Game;
import edu.northwesternmall.demo.model.request.AddCategoryReq;
import edu.northwesternmall.demo.model.request.AddGameReq;
import edu.northwesternmall.demo.service.GameService;
import jdk.jfr.events.ExceptionThrownEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameMapper gameMapper;



    @Override
    public void add(AddGameReq addGameReq) throws MallException{
        Game game = new Game();
        BeanUtils.copyProperties(addGameReq,game);
        Game gameold = gameMapper.selectByName(addGameReq.getGameName());
        if(gameold != null){
            throw new MallException(ExceptionEnum.NAME_EXISTED);
        }

        int count =  gameMapper.insertSelective(game);
        if(count == 0){
            throw new MallException(ExceptionEnum.CREATE_FAILED);
        }


    }


    @Override
    public void update(Game game) throws MallException{
        if(game.getGameName() != null){
            Game oldGame = gameMapper.selectByName(game.getGameName());
            if(oldGame != null && !oldGame.getId().equals(game.getId())){
                throw new MallException(ExceptionEnum.NAME_EXISTED);
            }
        }

        int count = gameMapper.updateByPrimaryKey(game);
        if(count == 0){
            throw new MallException(ExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public List<Game> DisplayAllVideoGames(){
        List AllGames = new ArrayList();
        AllGames = gameMapper.DisplayList();
        return AllGames;

    }


    @Override
    public void delete(Integer id)throws MallException{
        Game gameOld = gameMapper.selectByPrimaryKey(id);
        if(gameOld == null){
            throw new MallException(ExceptionEnum.DELETE_FAIL);
        }
        int count = gameMapper.deleteByPrimaryKey(id);
        if(count == 0) {
            throw new MallException(ExceptionEnum.DELETE_FAIL);
        }
    }


}
