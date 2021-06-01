package edu.northwesternmall.demo.service;

import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.pojo.Game;
import edu.northwesternmall.demo.model.request.AddGameReq;

import java.util.List;

public interface GameService {
    void add(AddGameReq addGameReq) throws MallException;

    //    @Override
    //    public void update(Category category) throws MallException {
    //        if(category.getName() != null){
    //            Category oldCategory =categoryMapper.selectByName(category.getName());
    //            if(oldCategory != null && !oldCategory.getId().equals(category.getId())){
    //                throw new MallException(ExceptionEnum.NAME_EXISTED);
    //            }
    //        }
    //
    //        int count = categoryMapper.updateByPrimaryKeySelective(category);
    //        if(count ==0){
    //            throw new MallException(ExceptionEnum.UPDATE_FAIL);
    //        }
    //
    //    }
    void update(Game game) throws MallException;

    List<Game> DisplayAllVideoGames();

    //    @Override
    //    public void delete(Integer id) throws MallException {
    //        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
    //        if(categoryOld == null){
    //            throw new MallException(ExceptionEnum.DELETE_FAIL);
    //        }
    //        int count =categoryMapper.deleteByPrimaryKey(id);
    //        if(count == 0){
    //            throw new MallException(ExceptionEnum.DELETE_FAIL);
    //        }
    //    }
    void delete(Integer id)throws MallException;
}
