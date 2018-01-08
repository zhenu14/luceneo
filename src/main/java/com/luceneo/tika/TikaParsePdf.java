package com.luceneo.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *  解析提取PDF文件内容
 */
public class TikaParsePdf {
    public static void main(String[] args) throws IOException,SAXException,TikaException{
        //文件路径
        String filepath = "files/中国人工智能大会CCAI 2016圆满落幕.pdf";
        //新建FIle对象
        File pdfFile = new File(filepath);
        //创建内容处理器对象
        BodyContentHandler handler = new BodyContentHandler();
        //创建元数据对象
        Metadata metadata = new Metadata();
        //读入文件
        //FileInputStream inputStream = new FileInputStream(pdfFile);
        InputStream inputStream = TikaInputStream.get(pdfFile);
        //创建内容解析器
        ParseContext parseContext = new ParseContext();
        //实例化PDFParser
        PDFParser parser = new PDFParser();
        //调用parse()解析文件
        parser.parse(inputStream,handler,metadata,parseContext);
        //便利元数据内容
        System.out.println("文件属性信息:");
        for(String name : metadata.names()){
            System.out.println(name + ":" + metadata.get(name));
        }
        //打印PDF文件中的内容
        System.out.println("pdf文件中的内容");
        System.out.println(handler.toString());
    }
}
