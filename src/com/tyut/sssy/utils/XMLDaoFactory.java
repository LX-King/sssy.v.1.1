package com.tyut.sssy.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class XMLDaoFactory {

    private static XMLDaoFactory instance = null;

    /*dao配置文件*/
    private String configure = "tax-mapping.xml";

    /*所有dao的仓库*/
    private Map<String, String> map = new HashMap<String, String>();


    /**
     * 私有的构造函数
     */
    private XMLDaoFactory() {

        //dom4j
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            URL url = XMLDaoFactory.class.getClassLoader().getResource(configure);
            doc = reader.read(url);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        assert doc != null;
        Element root = doc.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element bean = (Element) i.next();
            String taxName = bean.selectSingleNode("name").getStringValue();
            String mapping = bean.selectSingleNode("mapping").getStringValue();
            map.put(taxName,mapping);

        }//遍历创建dao impl 的实例并存储到map中
    }

    /**
     * 创建单粒子
     *
     * @return XMLDaoFactory
     */
    public static XMLDaoFactory getInstance() {
        if (instance == null) {
            synchronized (XMLDaoFactory.class) {
                if (instance == null) {
                    instance = new XMLDaoFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 返回 实例
     *
     * @param taxName
     * @return object
     */
    public String getMapping(String taxName) {
        return this.map.get(taxName.trim());
    }
}