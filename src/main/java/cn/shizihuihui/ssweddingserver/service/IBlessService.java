package cn.shizihuihui.ssweddingserver.service;

import cn.shizihuihui.ssweddingserver.entity.Bless;
import cn.shizihuihui.ssweddingserver.entity.BlessVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 祝福语表 服务类
 * </p>
 *
 * @author song
 * @since 2019-10-16
 */
public interface IBlessService extends IService<Bless> {

    String saveBlessAndGuest(BlessVO blessVO);

}
