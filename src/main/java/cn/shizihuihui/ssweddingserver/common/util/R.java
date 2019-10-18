package cn.shizihuihui.ssweddingserver.common.util;

import cn.shizihuihui.ssweddingserver.common.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private String msg = "success";


    @Getter
    @Setter
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = CommonConstants.FAIL;
    }
    public R success(T data){
        R<T> r = new R<>();
        r.data = data;
        r.code = CommonConstants.SUCCESS;
        return r;
    }
    public R(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}