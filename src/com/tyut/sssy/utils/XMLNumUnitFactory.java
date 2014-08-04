package com.tyut.sssy.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public final class XMLNumUnitFactory {

    private static XMLNumUnitFactory instance = null;

    /*dao配置文件*/
    private String configure = "number-unit.xml";

    private File xmlFile =null;
    private Document doc = null;
    /*所有dao的仓库*/
    private NumberFormat numberFormat;


    /**
     * 私有的构造函数
     */
    private XMLNumUnitFactory() {

        //dom4j
        SAXReader reader = new SAXReader();
        try {
            URL url = XMLNumUnitFactory.class.getClassLoader().getResource(configure);
            assert url != null;
            this.xmlFile = new File(url.getFile());
            this.doc = reader.read(url);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        assert doc != null;
        Element root = doc.getRootElement();
        numberFormat = new NumberFormat();
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element bean = (Element) i.next();
            String value = bean.selectSingleNode("value").getStringValue();
            String unit = bean.selectSingleNode("unit").getStringValue();
            numberFormat.setUnit(unit);
            numberFormat.setValue(value);
        }//遍历创建dao impl 的实例并存储到map中
    }

    /**
     * 创建单粒子
     *
     * @return XMLDaoFactory
     */
    public static XMLNumUnitFactory getInstance() {
        if (instance == null) {
            synchronized (XMLNumUnitFactory.class) {
                if (instance == null) {
                    instance = new XMLNumUnitFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 返回 实例
     *
     * @return object
     */
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public boolean setNumberFormat(NumberFormat numberFormat) {
        this.doc.selectSingleNode("/numbers/number/value").setText(numberFormat.getValue());
        this.doc.selectSingleNode("/numbers/number/unit").setText(numberFormat.getUnit());
        boolean flag= true;
        try {
            XMLWriter writer = new XMLWriter(new FileOutputStream(this.xmlFile));
            writer.write(this.doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}