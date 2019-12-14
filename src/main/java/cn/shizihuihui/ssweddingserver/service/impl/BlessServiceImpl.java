package cn.shizihuihui.ssweddingserver.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.shizihuihui.ssweddingserver.common.constant.CommonConstants;
import cn.shizihuihui.ssweddingserver.common.util.R;
import cn.shizihuihui.ssweddingserver.entity.*;
import cn.shizihuihui.ssweddingserver.mapper.BlessMapper;
import cn.shizihuihui.ssweddingserver.mapper.GuestMapper;
import cn.shizihuihui.ssweddingserver.mapper.UserMapper;
import cn.shizihuihui.ssweddingserver.service.IBlessService;
import cn.shizihuihui.ssweddingserver.util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class BlessServiceImpl extends ServiceImpl<BlessMapper, Bless> implements IBlessService {
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private UserMapper userMapper;
    @Value("${app.wechat.appID}")
    String wechatAppId;
    //小程序的 app secret (在微信小程序管理后台获取)
    @Value("${app.wechat.appsecret}")
    String wechatSecretKey;

    @Override
    @Transactional
    public String saveBlessAndGuest(BlessVO blessVO) {
        log.info("祝福信息保存:"+blessVO.toString());
        String accessToken = null;
        //内容安全校验
        if(StringUtils.isNotBlank(blessVO.getWords())){
            String tokenParam ="grant_type=client_credential&appid=" + wechatAppId + "&secret=" + wechatSecretKey;
            String res = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", tokenParam);
            JSONObject jsonObject = JSONObject.parseObject(res);
            accessToken = jsonObject.getString("access_token");

            JSONObject msgObj = new JSONObject();
            msgObj.put("content",blessVO.getWords());
            String result = HttpRequest.sendPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken, msgObj.toJSONString());
            JSONObject msgCheckRes = JSONObject.parseObject(result);
            if(msgCheckRes.getInteger("errcode")!=0 || !msgCheckRes.getString("errmsg").equals("ok")){
                return "含有违法违规内容,发送失败";
            }
        }



        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("openid",blessVO.getOpenId());
        User user = userMapper.selectOne(userQw);
        if(user==null){
            user = new User();
            //登录信息保存错误，进行补偿 保存
            user.setOpenid(blessVO.getOpenId());
            user.setNickname(blessVO.getNickname());
            user.setCtime(new Date());
            userMapper.insert(user);
        }



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
           bless.setUserId(user.getId());
           bless.setCreateTime(formatTime);
           this.save(bless);
           return "发送成功，感谢您的祝福";
        }else{
            //出席信息
            //首先查询是否已有出席信息
            Boolean isUpdate = false;
            QueryWrapper<Guest> guestQw = new QueryWrapper<>();
            guestQw.eq("user_id",user.getId());
            if(guestMapper.selectCount(guestQw)>0){
                //更新操作
                Guest guest = new Guest();
                guest.setUpdateTime(formatTime);
                guest.setRealName(blessVO.getRealName());
                guest.setTel(blessVO.getTel());
                guest.setFace(blessVO.getFace());
                guest.setNickname(blessVO.getNickname());
                guest.setNum(blessVO.getNum());
                guestMapper.update(guest,guestQw);
                isUpdate = true;
            }else{
                Guest guest = new Guest();
                guest.setNum(blessVO.getNum());
                guest.setRealName(blessVO.getRealName());
                guest.setTel(blessVO.getTel());
                guest.setUserId(user.getId());
                guest.setFace(blessVO.getFace());
                guest.setNickname(blessVO.getNickname());
                guest.setCreateTime(formatTime);
                guestMapper.insert(guest);
            }
            if(StringUtils.isNotBlank(blessVO.getWords())){
                Bless bless = new Bless();
                BeanUtils.copyProperties(blessVO,bless);
                bless.setCreateTime(formatTime);
                bless.setUserId(user.getId());
                this.save(bless);
            }
            this.sendMsg(blessVO,isUpdate,accessToken);
            if(isUpdate){
                //发送模板请求
                return "您的信息更新成功,期待您的到来";
            }else{
                return "提交成功,期待您的到来";
            }
        }
    }

//{
//  "touser": "OPENID",
//  "template_id": "TEMPLATE_ID",
//  "page": "index",
//  "form_id": "FORMID",
//  "data": {
//      "keyword1": {
//          "value": "339208499"
//      },
//      "keyword2": {
//          "value": "2015年01月05日 12:30"
//      },
//      "keyword3": {
//          "value": "腾讯微信总部"
//      } ,
//      "keyword4": {
//          "value": "广州市海珠区新港中路397号"
//      }
//  },
//  "emphasis_keyword": "keyword1.DATA"
//}
    private void sendMsg(BlessVO blessVO,Boolean isUpdate,String accessToken){
        try {
            //获取access_token
            if(accessToken==null){
                //如果已经取到则不需要再次取
                String tokenParam ="grant_type=client_credential&appid=" + wechatAppId + "&secret=" + wechatSecretKey;
                String res = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", tokenParam);
                JSONObject jsonObject = JSONObject.parseObject(res);
                accessToken = jsonObject.getString("access_token");
            }
            JSONObject params = new JSONObject();
            params.put("touser",blessVO.getOpenId());
            params.put("template_id","57WPG6l8ujS8LgdnHxEmcpIfoM2rwxDYBJI_1SuYx7k");
            params.put("page","pages/bless/index");
            params.put("form_id",blessVO.getFormId());
            JSONObject msgData = new JSONObject();
            TemplateData templateData1 = new TemplateData();
            if(!isUpdate){
                templateData1.setValue("新增出席信息");
            }else{
                templateData1.setValue("更新出席信息");
            }
            TemplateData templateData2 = new TemplateData();
            templateData2.setValue(blessVO.getRealName());
            TemplateData templateData3 = new TemplateData();
            templateData3.setValue(blessVO.getTel().toString());
            TemplateData templateData4 = new TemplateData();
            templateData4.setValue(blessVO.getNum());
            TemplateData templateData5 = new TemplateData();
            String formatDateTime = DateUtil.formatDateTime(new Date());
            templateData5.setValue(formatDateTime);
            msgData.put("keyword1",templateData1);
            msgData.put("keyword2",templateData2);
            msgData.put("keyword3",templateData3);
            msgData.put("keyword4",templateData4);
            msgData.put("keyword5",templateData5);
            params.put("data",msgData);
//            log.info(params.toJSONString());
            String result = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken, params.toJSONString());
//            log.info("发送模板消息结果");
        }catch (Exception e){
            log.error("发送模板消息失败"+e.getMessage());
        }

    }

}
