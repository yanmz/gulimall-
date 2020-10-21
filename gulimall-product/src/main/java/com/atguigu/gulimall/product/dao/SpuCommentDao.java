package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.SpuCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价
 * 
 * @author yanmengzhang
 * @email yanmengzhang@gmail.com
 * @date 2020-10-21 11:25:09
 */
@Mapper
public interface SpuCommentDao extends BaseMapper<SpuCommentEntity> {
	
}
