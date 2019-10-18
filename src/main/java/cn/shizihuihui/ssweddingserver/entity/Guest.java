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
 * 
 * </p>
 *
 * @author song
 * @since 2019-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_guest")
@ApiModel(value="Guest对象", description="")
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "来客表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "来客名称")
    private String realName;

    @ApiModelProperty(value = "来客电话")
    private Long tel;

    @ApiModelProperty(value = "来客人数")
    private Integer num;

    private String createTime;
    private String updateTime;

}
