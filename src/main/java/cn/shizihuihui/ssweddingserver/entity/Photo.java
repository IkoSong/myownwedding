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
 * 照片表
 * </p>
 *
 * @author song
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_photo")
@ApiModel(value="Photo对象", description="照片表")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "祝福表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "照片名称")
    private String name;

    @ApiModelProperty(value = "照片URL")
    private String url;

    @ApiModelProperty(value = "展示顺序")
    private Integer showIndex;


}
