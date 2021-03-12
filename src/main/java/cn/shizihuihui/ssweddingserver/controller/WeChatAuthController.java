package cn.shizihuihui.ssweddingserver.controller;

import cn.shizihuihui.ssweddingserver.common.util.R;
import cn.shizihuihui.ssweddingserver.entity.User;
import cn.shizihuihui.ssweddingserver.service.IUserService;
import cn.shizihuihui.ssweddingserver.util.AesCbcUtil;
import cn.shizihuihui.ssweddingserver.util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author song
 * @since 2019-10-09
 */
@RestController
@RequestMapping("/wechatApi")
@Slf4j
public class WeChatAuthController {
    @Autowired
    private IUserService userService;

    //小程序唯一标识   (在微信小程序管理后台获取)

    @Value("${app.wechat.appID}")
    String wechatAppId;
    //小程序的 app secret (在微信小程序管理后台获取)
    @Value("${app.wechat.appsecret}")
    String wechatSecretKey;
    //授权（必填）
    String grantType = "authorization_code";


    @GetMapping("/login")
    public Map login(String iv, String encryptedData, String code){
        Map map = new HashMap();
        // login code can not be null
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        // using login code to get sessionId and openId
        String params = "appid=" + wechatAppId + "&secret=" + wechatSecretKey + "&js_code=" + code + "&grant_type=" + grantType;

        // sending request
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);

        // analysis request content
        JSONObject json = JSONObject.parseObject(sr);

        // getting session_key
        String sessionKey = json.getString("session_key");

        // getting open_id
        String openId = json.getString("openid");

        // decoding encrypted info with AES
        try {
            String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.parseObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                map.put("userInfo", userInfo);
                //插入用户信息表
                QueryWrapper<User> userQw = new QueryWrapper<>();
                userQw.eq("openId",openId);
                if(userService.count(userQw)<=0){
                    //新用户
                    User user = new User();
                    user.setOpenid( userInfoJSON.getString("openId"));
                    user.setNickname(userInfoJSON.getString("nickName"));
                    user.setGender(userInfoJSON.getInteger("gender"));
                    user.setCity( userInfoJSON.getString("city"));
                    user.setProvince(userInfoJSON.getString("province"));
                    user.setCountry(userInfoJSON.getString("country"));
                    user.setLanguage(userInfoJSON.getString("language"));
                    user.setCtime(new Date());
                    userService.save(user);
                }

                return map;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

}
