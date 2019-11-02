package com.linwl.weatherforecast.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 13:59
 * @description ：
 * @modified By：
 */
@Slf4j
public class YamlReader {
    private static HashMap properties;

    private YamlReader() {
        if (SingletonHolder.instance != null) {
            throw new IllegalStateException();
        }
    }


    /**
     * 单例类
     */
    private static class SingletonHolder {
        private static YamlReader instance = new YamlReader();
    }

    public static YamlReader getInstance() {
        return SingletonHolder.instance;
    }


    static {
        try {
            Yaml yaml = new Yaml();
            try (InputStream in =YamlReader.class.getClassLoader().getResourceAsStream("application.yaml")){
                properties = yaml.loadAs(in, HashMap.class);
            }
        } catch (IOException e) {
            log.error(MessageFormat.format("读取配置文件异常:{0}!",e.getMessage()));
        }
    }


    /**
     * 根据路劲获取配置
     * @param path
     * @return
     * @throws Exception
     */
    public Object getValueByPath(String path) throws Exception{
        if(StringUtils.isNotBlank(path))
        {
            Object result =null;
            String sep =".";
            if(path.contains(sep)) {
                String[] propertys = path.split("\\.");
                Map rootMap = properties;
                for (String property : propertys) {
                    result = getValue(rootMap, property);
                    if (result != null) {
                        if (result instanceof Map) {
                            rootMap = (Map) result;
                        } else {
                            break;
                        }
                    }
                }
                return result;
            }
            else {
                throw new Exception("属性配置路径规则不正确！");
            }
        }
        else {
            throw new Exception("属性配置路径不能为空！");
        }
    }

    /**
     * 获取属性
     * @param property
     * @param key
     * @return
     */
    public Object getValue(Map property,String key)
    {
        return property.getOrDefault(key, null);
    }
}
