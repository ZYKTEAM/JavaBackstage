package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.myblog.entity.UserEntity;
import com.myblog.mapper.UserMapper;

/**
 * Created by Administrator on 2017/11/30.
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserEntity> {

    @Autowired
    private UserMapper userMapper;


    /**
     * @CacheEvict 支持如下几个参数：
     *value：缓存位置名称，不能为空，同上
     *key：缓存的key，默认为空，同上
     *condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
     *allEntries：true表示清除value中的全部缓存，默认为false
     */
    /**
     * Cacheable
     * value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
     * key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
     * condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
     *
     * @return
     */
    @Cacheable(value = "user", key = "#root.targetClass+#username", unless = "#result eq null")
    public List<UserEntity> queryList() {
        return userMapper.queryList();
    }
    
    @Cacheable(value = "user", key = "#root.targetClass+#username", unless = "#result eq null")
    public UserEntity userLoginOpendId(String opendId) {
    	UserEntity user = userMapper.userLoginOpendId(opendId);
        return user;
    }

}
