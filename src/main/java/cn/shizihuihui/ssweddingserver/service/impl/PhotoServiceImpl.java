package cn.shizihuihui.ssweddingserver.service.impl;

import cn.shizihuihui.ssweddingserver.entity.Photo;
import cn.shizihuihui.ssweddingserver.mapper.PhotoMapper;
import cn.shizihuihui.ssweddingserver.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 照片表 服务实现类
 * </p>
 *
 * @author song
 * @since 2019-10-14
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

}
