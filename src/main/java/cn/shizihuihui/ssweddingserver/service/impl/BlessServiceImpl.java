package cn.shizihuihui.ssweddingserver.service.impl;

import cn.shizihuihui.ssweddingserver.common.constant.CommonConstants;
import cn.shizihuihui.ssweddingserver.common.util.R;
import cn.shizihuihui.ssweddingserver.entity.Bless;
import cn.shizihuihui.ssweddingserver.entity.BlessVO;
import cn.shizihuihui.ssweddingserver.entity.Guest;
import cn.shizihuihui.ssweddingserver.mapper.BlessMapper;
import cn.shizihuihui.ssweddingserver.mapper.GuestMapper;
import cn.shizihuihui.ssweddingserver.service.IBlessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 祝福语表 服务实现类
 * </p>
 *
 * @author song
 * @since 2019-10-16
 */
@Service
public class BlessServiceImpl extends ServiceImpl<BlessMapper, Bless> implements IBlessService {
    @Autowired
    private GuestMapper guestMapper;

    @Override
    @Transactional
    public String saveBlessAndGuest(BlessVO blessVO) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = sdf.format(new Date());
        if(!blessVO.getIsCome()){
           //仅填祝福
            if(StringUtils.isBlank(blessVO.getWords())){
                throw new RuntimeException("请输入祝福内容");
            }
            if(StringUtils.isBlank(blessVO.getFace())){
                throw new RuntimeException("个人信息缺失,请重新获取");
            }
            Bless bless = new Bless();
           BeanUtils.copyProperties(blessVO,bless);
           bless.setCreateTime(formatTime);
           this.save(bless);
           return "发送成功，感谢您的祝福";
        }else{
            //出席信息
            //首先查询是否已有出席信息
            Boolean isUpdate = false;
            QueryWrapper<Guest> guestQw = new QueryWrapper<>();
            guestQw.eq("real_name",blessVO.getRealName()).eq("tel",blessVO.getTel());
            if(guestMapper.selectCount(guestQw)>0){
                //更新操作
                Guest guest = new Guest();
                guest.setUpdateTime(formatTime);
                guest.setNum(blessVO.getNum());
                guestMapper.update(guest,guestQw);
                isUpdate = true;
            }else{
                Guest guest = new Guest();
                guest.setNum(blessVO.getNum());
                guest.setRealName(blessVO.getRealName());
                guest.setTel(blessVO.getTel());
                guest.setFace(blessVO.getFace());
                guest.setNickname(blessVO.getNickname());
                guest.setCreateTime(formatTime);
                guestMapper.insert(guest);
            }
            if(StringUtils.isNotBlank(blessVO.getWords())){
                Bless bless = new Bless();
                BeanUtils.copyProperties(blessVO,bless);
                bless.setCreateTime(formatTime);
                this.save(bless);
            }
            if(isUpdate){
                return "您的信息更新成功,期待您的到来";
            }else{
                return "提交成功,期待您的到来";
            }
        }
    }
}
