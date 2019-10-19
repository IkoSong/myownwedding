package cn.shizihuihui.ssweddingserver.service.impl;

import cn.shizihuihui.ssweddingserver.entity.Config;
import cn.shizihuihui.ssweddingserver.mapper.ConfigMapper;
import cn.shizihuihui.ssweddingserver.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小程序配置表 服务实现类
 * </p>
 *
 * @author song
 * @since 2019-10-19
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
