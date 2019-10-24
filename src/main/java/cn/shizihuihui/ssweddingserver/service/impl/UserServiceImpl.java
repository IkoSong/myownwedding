package cn.shizihuihui.ssweddingserver.service.impl;

import cn.shizihuihui.ssweddingserver.entity.User;
import cn.shizihuihui.ssweddingserver.mapper.UserMapper;
import cn.shizihuihui.ssweddingserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小程序用户表 服务实现类
 * </p>
 *
 * @author song
 * @since 2019-10-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
