package cn.shizihuihui.ssweddingserver.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlessVO extends Bless {
    @ApiModelProperty(value = "来客名称")
    private String realName;

    @ApiModelProperty(value = "来客电话")
    private Long tel;

    @ApiModelProperty(value = "来客人数")
    private Integer num;

    private Boolean isCome;

    private String openId;

    private String formId;
}
