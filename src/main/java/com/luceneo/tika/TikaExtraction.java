package com.luceneo.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

/**
 *  自动解析文件夹下文档的内容
 */
public class TikaExtraction {
    public static void main(String[] args) throws IOException,TikaException{
        Tika tika = new Tika();
        //新建存放各种文件的files文件
        File fileDir = new File("files");
        if(!fileDir.exists()){
            System.out.println("文件夹不存在，请检查");
            System.exit(0);
        }
        File[] files = fileDir.listFiles();
        String filecontent;
        for(File f : files){
            filecontent = tika.parseToString(f);
            System.out.println("解析文件内容:" + filecontent);
        }
    }
}
