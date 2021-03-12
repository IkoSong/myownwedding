package cn.shizihuihui.ssweddingserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 小程序配置表
 * </p>
 *
 * @author song
 * @since 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_config")
@ApiModel(value="Config对象", description="小程序配置表")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "配置类型")
    private Integer configType;

    @ApiModelProperty(value = "配置键")
    private String configKey;

    @ApiModelProperty(value = "配置键配置值")
    private String configValue;


}
