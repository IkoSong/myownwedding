package cn.shizihuihui.ssweddingserver.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class EnumUtils {

    private static Logger log = LoggerFactory.getLogger(EnumUtils.class);

    /**
     * 通过key取value
     * @param enumClass enum的class
     * @param code 代码
     * @return
     */
    public static String getDesc(Class enumClass,Object code){
        if(code == null) return "";
        String desc = "";
        try {
            Method method = enumClass.getMethod("values", new Class[0]);
            BaseEnum[] baseEnums = (BaseEnum[])method.invoke(enumClass,new Object[0]);
            Field codeField =enumClass.getDeclaredField("code");
            codeField.setAccessible(true);
            Field descField =enumClass.getDeclaredField("desc");
            descField.setAccessible(true);
            for(BaseEnum e : baseEnums){
                if(codeField.get(e) instanceof  String){
                    if(codeField.get(e).equals(code.toString())){
                        desc = (String)descField.get(e);
                        break;
                    }
                }else if(codeField.get(e) instanceof Integer){
                    if(((Integer) codeField.get(e)).equals((Integer) code)){
                        desc = (String)descField.get(e);
                        break;
                    }
                }else{
                    log.info("不支持code的类型，自己维护");
                }

            }
        } catch (NoSuchMethodException e) {
            log.info("EnumUtil : " + enumClass.getName() + "is not a enum!");
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has not implements BaseEnum!");
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has no code or desc field!");
            e.printStackTrace();
        }
        return desc;
    }

    /**
     * 通过value取key
     * @param enumClass enum的class
     * @param desc value
     * @return
     */
    public static Object getCode(Class enumClass,Object desc){
        Object code = null;
        try {
            Method method = enumClass.getMethod("values", new Class[0]);
            BaseEnum[] baseEnums = (BaseEnum[])method.invoke(enumClass,new Object[0]);
            Field codeField =enumClass.getDeclaredField("code");
            codeField.setAccessible(true);
            Field descField =enumClass.getDeclaredField("desc");
            descField.setAccessible(true);
            for(BaseEnum e : baseEnums){
                if(descField.get(e).equals(desc.toString())){
                    code = codeField.get(e);
                    break;
                }
            }
        } catch (NoSuchMethodException e) {
            log.info("EnumUtil : " + enumClass.getName() + "is not a enum!");
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has not implements BaseEnum!");
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has no code or desc field!");
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 遍历enum成LinkedHashMap
     * @param enumClass enum的class
     * @return
     */
    public static LinkedHashMap values(Class enumClass){
        LinkedHashMap map = null;
        try {
            Method method = enumClass.getMethod("values", new Class[0]);
            BaseEnum[] baseEnums = (BaseEnum[])method.invoke(enumClass,new Object[0]);
            Field codeField =enumClass.getDeclaredField("code");
            codeField.setAccessible(true);
            Field descField =enumClass.getDeclaredField("desc");
            descField.setAccessible(true);
            map = new LinkedHashMap<String, String>();
            for(BaseEnum e : baseEnums){
                map.put(codeField.get(e),descField.get(e));
            }
        } catch (NoSuchMethodException e) {
            log.info("EnumUtil : " + enumClass.getName() + "is not a enum!");
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has not implements BaseEnum!");
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            log.info("EnumUtil : " + enumClass.getName() + "has no code or desc field!");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 枚举接口，管理通用方法
     * @author Chandler
     * @date 2016-3-7
     */
    public interface BaseEnum{
//		public String getCode();
//		public String getDesc();
    }

    /**
     * 是否枚举
     * @author Chandler
     * @date 2016-3-7
     */
    public enum YesOrNoEnum implements BaseEnum{

        YES(1,"是"),
        NO(0,"否");

        private Integer code;
        private String desc;

        private YesOrNoEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 启用枚举
     * @author Chandler
     * @date 2016-3-7
     */
    public enum EnableEnum implements BaseEnum{

        ENABLE(1,"启用"),
        DISABLE(0,"停用");

        private Integer code;
        private String desc;

        private EnableEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 处理枚举
     * @author Chandler
     * @date 2016-3-7
     */
    public enum ProcessEnum implements BaseEnum{

        UNPROCESSED(0,"未处理"),
        PROCESSED(1,"已处理");

        private Integer code;
        private String desc;

        private ProcessEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 性别枚举
     * @author Chandler
     * @date 2016-3-9
     */
    public enum SexEnum implements BaseEnum{

        MALE(1,"男"),
        FEMALE(2,"女"),
        UNKNOW(0,"未知");

        private Integer code;
        private String desc;

        private SexEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 有效无效枚举
     * @author Chandler
     * @date 2016-3-10
     */
    public enum EffectiveEnum implements BaseEnum{

        EFFECTIVE(0,"有效"),
        INVALID(1,"无效");

        private Integer code;
        private String desc;

        private EffectiveEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 逻辑删除枚举
     * @author Chandler
     * @date 2016-3-10
     */
    public enum DelFlagEnum implements BaseEnum{

        UNDELETED(0,"未删除"),
        DELETED(1,"删除");

        private Integer code;
        private String desc;

        private DelFlagEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }


    public enum ConfigTypeEnum implements BaseEnum{

        MUSICURL(0,"背景音乐url");

        private Integer code;
        private String desc;

        private ConfigTypeEnum(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

}
