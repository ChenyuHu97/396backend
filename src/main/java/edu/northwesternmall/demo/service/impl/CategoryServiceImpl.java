package edu.northwesternmall.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.northwesternmall.demo.common.ApiRestResponse;
import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.VO.CategoryVO;
import edu.northwesternmall.demo.model.dao.CategoryMapper;
import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.request.AddCategoryReq;
import edu.northwesternmall.demo.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(AddCategoryReq addCategoryReq) throws MallException {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category categoryold =categoryMapper.selectByName(addCategoryReq.getName());
        if(categoryold != null){
            throw new MallException(ExceptionEnum.NAME_EXISTED);
        }

        int count =categoryMapper.insertSelective(category);
        if(count == 0){
            throw new MallException(ExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Category category) throws MallException {
        if(category.getName() != null){
            Category oldCategory =categoryMapper.selectByName(category.getName());
            if(oldCategory != null && !oldCategory.getId().equals(category.getId())){
                throw new MallException(ExceptionEnum.NAME_EXISTED);
            }
        }

        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if(count ==0){
            throw new MallException(ExceptionEnum.UPDATE_FAIL);
        }

    }

    @Override
    public void delete(Integer id) throws MallException {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if(categoryOld == null){
            throw new MallException(ExceptionEnum.DELETE_FAIL);
        }
        int count =categoryMapper.deleteByPrimaryKey(id);
        if(count == 0){
            throw new MallException(ExceptionEnum.DELETE_FAIL);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList =categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;

    }

    @Override
    public List<CategoryVO> listCategoryForCustomer(){
        List<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList,0);
        return categoryVOList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOArrayList,Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)){
            for(int i = 0; i<categoryList.size();i++){
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOArrayList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }

}
