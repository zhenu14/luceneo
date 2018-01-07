package com.luceneo.analyzer;

import com.luceneo.ik.MyIkAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class VariousAnalyzers {
    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";
    private static String StringEn = "Dogs can not achieve a place,eyes can reach";

    public static void main(String [] args) throws IOException {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        System.out.println("标准分词：");
        printAnalyzer(analyzer);
        analyzer = new WhitespaceAnalyzer();
        System.out.println("空格分词：");
        printAnalyzer(analyzer);
        analyzer = new SimpleAnalyzer();
        System.out.println("简单分词：");
        printAnalyzer(analyzer);
        analyzer = new CJKAnalyzer();
        System.out.println("二分法分词：");
        printAnalyzer(analyzer);
        analyzer = new KeywordAnalyzer();
        System.out.println("关键词分词：");
        printAnalyzer(analyzer);
        analyzer = new StopAnalyzer();
        System.out.println("停用词分词：");
        printAnalyzer(analyzer);

        analyzer = new MyIkAnalyzer(true);
        System.out.println("重写的分词器：");
        printAnalyzer(analyzer);

    }

    public static void printAnalyzer(Analyzer analyzer)throws IOException{
        StringReader reader = new StringReader(strCh);
        TokenStream tokenStream = analyzer.tokenStream(strCh,reader);
        tokenStream.reset();
        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()){
            System.out.print(termAttribute.toString() + "|");
        }
        System.out.println();
        analyzer.close();
    }
}
