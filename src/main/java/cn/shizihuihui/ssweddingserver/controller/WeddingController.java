package cn.shizihuihui.ssweddingserver.controller;


import cn.shizihuihui.ssweddingserver.common.constant.CommonConstants;
import cn.shizihuihui.ssweddingserver.entity.Bless;
import cn.shizihuihui.ssweddingserver.entity.BlessVO;
import cn.shizihuihui.ssweddingserver.entity.Photo;
import cn.shizihuihui.ssweddingserver.service.IBlessService;
import cn.shizihuihui.ssweddingserver.service.IGuestService;
import cn.shizihuihui.ssweddingserver.service.IPhotoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.shizihuihui.ssweddingserver.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author song
 * @since 2019-10-09
 */
@RestController
@RequestMapping("/wedding")
@Slf4j
public class WeddingController{
    @Autowired
    private IBlessService blessService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private IPhotoService photoServicel;


    @GetMapping("/getBless")
    public R getBless(){
        return new R<>(blessService.list(new QueryWrapper<>()));
    }

    @PostMapping("/sendBless")
    public R sendBless(@RequestBody BlessVO blessVO){
        try {
            String reMsg = blessService.saveBlessAndGuest(blessVO);
            List<Bless> listData = blessService.list(new QueryWrapper<>());
            return new R<>(CommonConstants.SUCCESS,reMsg,listData);
        } catch (Exception e) {
            log.error("祝福信息保存错误+"+e.getMessage());
            return new R(CommonConstants.FAIL,"系统错误！");
        }
    }

    @GetMapping("/getPhotos")
    public R<List<Photo>> getPhotos(){
        //根据index排序
        List<Photo> photoList = photoServicel.list(new QueryWrapper<>());
        photoList.sort(Comparator.comparingInt(Photo::getShowIndex));
        return new R<>(photoList);
    }

}

