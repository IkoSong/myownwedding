package cn.shizihuihui.ssweddingserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 小程序用户表
 * </p>
 *
 * @author song
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_user")
@ApiModel(value="User对象", description="小程序用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小程序用户的openid")
    private String openid;

    @ApiModelProperty(value = "用户头像")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatarurl;

    @ApiModelProperty(value = "性别  1男、2-女")
    private Integer gender;

    @ApiModelProperty(value = "所在国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "语种")
    private String language;

    @ApiModelProperty(value = "创建/注册时间")
    private Date ctime;

    @ApiModelProperty(value = "手机号码")
    private String mobile;


}
