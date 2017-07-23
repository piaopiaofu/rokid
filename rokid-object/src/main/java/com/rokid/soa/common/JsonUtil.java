package com.rokid.soa.common;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: shipf
 * Date: 14-5-15
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtil {


    //public static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper;

    /**
     * 获取ObjectMapper实例
     *
     * @return
     */
    public static synchronized ObjectMapper getMapperInstance() {
        if (mapper == null) {
            mapper = new ObjectMapper();

        }
        mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param obj 准备转换的对象
     * @return json字符串
     * @throws Exception
     */
    public static String beanToJson(Object obj) throws Exception {
        ObjectMapper objectMapper = getMapperInstance();
        String json = objectMapper.writeValueAsString(obj);
        return json;

    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json 准备转换的json字符串
     * @param cls  准备转换的类
     * @return
     * @throws Exception
     */
    public static Object jsonToBean(String json, Class<?> cls) throws Exception {
        ObjectMapper objectMapper = getMapperInstance();

        Object vo = objectMapper.readValue(json, cls);
        return vo;

    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json 准备转换的json字符串
     * @param javaType  准备转换的类
     * @return
     * @throws Exception
     */
    public static <T>  T jsonToBean(String json, JavaType javaType) throws Exception {
        ObjectMapper objectMapper = getMapperInstance();
        return objectMapper.readValue(json, javaType);

    }

    /**
     * 复杂json转换的jsonNode,jsonNode.get("detail").get("defaultItemPrice").toString()
     *
     * @param json
     * @return jsonNode
     * @throws java.io.IOException
     */
    public static JsonNode complexJson(String json) throws IOException {
        StringReader reader = new StringReader(json);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(reader);

    }

    /**
     * 复杂对象list,map,需要javatype
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper objectMapper = getMapperInstance();
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    public static void main(String[] args){

        try {
            System.out.println(jsonToBean("true",Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
