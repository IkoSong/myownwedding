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
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_bless")
@ApiModel(value="Bless对象", description="")
public class Bless implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "祝福表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "祝福人名字")
    private String name;

    @ApiModelProperty(value = "祝福人内容")
    private String text;

    @ApiModelProperty(value = "创建时间")
    private String createTime;


}
