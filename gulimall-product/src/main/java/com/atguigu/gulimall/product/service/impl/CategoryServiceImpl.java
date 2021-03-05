package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private  CategoryDao categoryDao;

    @Autowired
    private  CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查出所有分类
        List<CategoryEntity> entities = categoryDao.selectList(null);
        //2.组装成父子的树形结构
        //2.1找到所有一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) -> {
            //过滤出一级菜单
            return categoryEntity.getParentCid() == 0;
        }).map(childerLevel->{
            childerLevel.setChildren(getChildrens(childerLevel,entities));
            return childerLevel;
        }).sorted((meun1,meun2)->{
            return  (meun1.getSort()==null?0:meun1.getSort())-(meun2.getSort()==null?0:meun2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前删除的菜单，是否被别的地方引用
        categoryDao.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCateLogPath(Long catelogId) {
        List<Long>  paths = new ArrayList<>();
        List<Long> cateLogPath = findCateLogPath(catelogId, paths);

        Collections.reverse(cateLogPath);
        return  cateLogPath.toArray(new Long[cateLogPath.size()]);
    }

    /**
     * 级联更新所有的数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());

    }

    private List<Long>  findCateLogPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findCateLogPath(byId.getParentCid(),paths);
        }
        return  paths;
    }

    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> collect = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((meun1, meun2) -> {
            return (meun1.getSort() == null ? 0 : meun1.getSort()) - (meun2.getSort() == null ? 0 : meun2.getSort());
        }).collect(Collectors.toList());
        return  collect;
    }

}