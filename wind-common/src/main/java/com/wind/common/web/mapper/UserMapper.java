package com.wind.common.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wind.common.web.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
