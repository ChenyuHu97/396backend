package edu.northwesternmall.demo.controller;

import com.github.pagehelper.PageInfo;
import edu.northwesternmall.demo.common.ApiRestResponse;
import edu.northwesternmall.demo.common.Constant;
import edu.northwesternmall.demo.exception.ExceptionEnum;
import edu.northwesternmall.demo.exception.MallException;
import edu.northwesternmall.demo.model.VO.CategoryVO;
import edu.northwesternmall.demo.model.pojo.Category;
import edu.northwesternmall.demo.model.pojo.User;
import edu.northwesternmall.demo.model.request.AddCategoryReq;
import edu.northwesternmall.demo.model.request.UpdateCategoryReq;
import edu.northwesternmall.demo.service.CategoryService;
import edu.northwesternmall.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;


    @ApiOperation("add category")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession httpSession, @Valid @RequestBody AddCategoryReq addCategoryReq) throws MallException {
        if(addCategoryReq.getName() == null || addCategoryReq.getType() == null
        || addCategoryReq.getOrderNum() == null || addCategoryReq.getParentId() == null){
            return ApiRestResponse.error(ExceptionEnum.NAME_NOT_NULL);
        }
        User user = (User)httpSession.getAttribute(Constant.MALL_USER);
        if(user == null){
            return ApiRestResponse.error(ExceptionEnum.NEED_LOGIN);
        }

        boolean adminRole = userService.checkAdminRole(user);
        if(adminRole){
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(ExceptionEnum.NOT_ADMIN);
        }

    }

    @ApiOperation("update category")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq,HttpSession httpSession) throws MallException {

        User user = (User)httpSession.getAttribute(Constant.MALL_USER);
        if(user == null){
            return ApiRestResponse.error(ExceptionEnum.NEED_LOGIN);
        }

        boolean adminRole = userService.checkAdminRole(user);
        if(adminRole){
            //categoryService.add(addCategoryReq);
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq,category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(ExceptionEnum.NOT_ADMIN);
        }

    }

    @ApiOperation("delete category")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) throws MallException {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }


    @ApiOperation("admin category List")
    @PostMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo =categoryService.listForAdmin(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("user category List")
    @PostMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer();
        return ApiRestResponse.success(categoryVOList);
    }



}
