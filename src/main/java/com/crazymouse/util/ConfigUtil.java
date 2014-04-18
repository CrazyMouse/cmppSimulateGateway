package com.crazymouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title ：配置管理工具
 * Description :用springTask实现周期性配置重加载
 * Create Time: 14-3-28 下午2:04
 */
public class ConfigUtil {
    private final static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
    private long fileLastModifyTime = 0;

    public String getConfig(String name) {
        return cache.get(name.trim());
    }

    public void setConfig(String name, String value) {
        cache.put(name.trim(), value.trim());
    }

    public void loadConf() {
        Properties properties = new Properties();
        File file = new File("./conf/server.properties");
        if (!file.exists()) {
            logger.error("未找到配置文件!");
            return;
        }
        if (file.lastModified() == fileLastModifyTime) {
            return;
        }
        fileLastModifyTime = file.lastModified();

        try {
            InputStream fi = new FileInputStream(file);
            properties.load(fi);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.setConfig((String) entry.getKey(), (String) entry.getValue());
            }
            fi.close();
            logger.info("【配置更新】");
        } catch (IOException e) {
            logger.error("load properties Error:{}", e);
        }
    }
}