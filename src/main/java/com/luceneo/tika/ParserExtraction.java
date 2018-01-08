package com.luceneo.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *  TIKA提取文档内容 使用Parser接口
 */
public class ParserExtraction {
    public static void main(String[] args)throws IOException,SAXException,TikaException{
        //新建存放各种文件的files文件夹
        File fileDir = new File("files");
        //如果文件夹路径错误：
        if(!fileDir.exists()){
            System.out.println("文件夹不存在，请检查！");
            System.exit(0);
        }
        //获取文件夹下所有文件
        File[] files = fileDir.listFiles();
        //创建内容处理器对象
        BodyContentHandler handler = new BodyContentHandler();
        //创建元数据对象
        Metadata metadata = new Metadata();

        Parser parser = new AutoDetectParser();
        //自动检测分词器
        ParseContext context = new ParseContext();

        FileInputStream inputStream = null;
        for(File f : files){
            inputStream = new FileInputStream(f);
            parser.parse(inputStream,handler,metadata,context);
            System.out.println(f.getName() + ":\n" + handler.toString());
        }
    }
}
