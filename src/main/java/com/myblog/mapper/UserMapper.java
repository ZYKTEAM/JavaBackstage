package com.myblog.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.myblog.entity.UserEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author:zyk
 * @Description:用户
 * @Date:Create in 14:44 2017/11/29
 * @Modified By:
 **/
//@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 查询全部用户
     *
     * @return
     */
    List<UserEntity> queryList();
    
    UserEntity userLoginOpendId(@Param("opendId")String opendId);
}
