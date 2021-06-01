package edu.northwesternmall.demo.model.dao;

import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.pojo.Game;

import java.util.List;

public interface GameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);

    List<Category> DisplayList();

    Game selectByName(String name);
}
