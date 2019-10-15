package cn.shizihuihui.ssweddingserver.controller;


import cn.shizihuihui.ssweddingserver.entity.Photo;
import cn.shizihuihui.ssweddingserver.service.IBlessService;
import cn.shizihuihui.ssweddingserver.service.IGuestService;
import cn.shizihuihui.ssweddingserver.service.IPhotoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class WeddingController{
    @Autowired
    private IBlessService blessService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private IPhotoService photoServicel;


    @GetMapping("/getBless")
    public R getBless(){
        return R.ok(blessService.list(new QueryWrapper<>()));
    }

    @GetMapping("/getPhotos")
    public R<List<Photo>> getPhotos(){
        return R.ok(photoServicel.list(new QueryWrapper<>()));
    }

}

