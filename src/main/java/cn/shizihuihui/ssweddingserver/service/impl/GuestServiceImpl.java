package cn.shizihuihui.ssweddingserver.service.impl;

import cn.shizihuihui.ssweddingserver.entity.Guest;
import cn.shizihuihui.ssweddingserver.mapper.GuestMapper;
import cn.shizihuihui.ssweddingserver.service.IGuestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author song
 * @since 2019-10-24
 */
@Service
public class GuestServiceImpl extends ServiceImpl<GuestMapper, Guest> implements IGuestService {

}
