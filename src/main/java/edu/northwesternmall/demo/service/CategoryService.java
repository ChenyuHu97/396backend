package edu.northwesternmall.demo.service;

import com.github.pagehelper.PageInfo;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.VO.CategoryVO;
import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.request.AddCategoryReq;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq) throws MallException;

    void update(Category category) throws MallException;

    void delete(Integer id) throws MallException;

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer();
}
