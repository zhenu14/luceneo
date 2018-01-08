package com.luceneo.web.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import com.luceneo.web.model.FileModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class SearchFileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //索引路径
//        String indexpathStr = request.getServletContext().getRealPath("/indexdir");
        String indexpathStr = "C:/ideaCode/luceneo/indexdir";
        //接收查询字符串
        String query = request.getParameter("query");
        //编码格式转换
        System.out.println(query);
//        query = new String(query.getBytes("iso8859-1"), "UTF-8");
//        System.out.println(query);
        if (query.equals("") || query == null) {
            System.out.println("参数错误!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } else {
            ArrayList<FileModel> hitsList = getTopDoc(query, indexpathStr,100);
            System.out.println("共搜到:" + hitsList.size() + "条数据!");
            request.setAttribute("hitsList", hitsList);
            request.setAttribute("queryback", query);
            request.getRequestDispatcher("result.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


    public static ArrayList<FileModel> getTopDoc(String key, String indexpathStr,int N) {
        ArrayList<FileModel> hitsList = new ArrayList<FileModel>();
        //检索域
        String[] fields = { "title", "content" };
        Path indexPath = Paths.get(indexpathStr);
        Directory dir;
        try {
            dir = FSDirectory.open(indexPath);
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new SmartChineseAnalyzer();
            // QueryParser parser = new QueryParser(field, analyzer);
            MultiFieldQueryParser parser2 = new MultiFieldQueryParser(fields, analyzer);
            // 查询字符串
            Query query = parser2.parse(key);
            TopDocs topDocs = searcher.search(query, N);
            // 定制高亮标签
            SimpleHTMLFormatter fors = new SimpleHTMLFormatter("<span style=\"color:red;font-weight:bold;\">", "</span>");
            QueryScorer scoreTitle = new QueryScorer(query, fields[0]);
            Highlighter hlqTitle = new Highlighter(fors, scoreTitle);// 高亮分析器
            QueryScorer scoreContent = new QueryScorer(query, fields[0]);
            Highlighter hlqContent = new Highlighter(fors, scoreTitle);// 高亮分析器
            TopDocs hits = searcher.search(query, 100);
            for (ScoreDoc sd : topDocs.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                String title = doc.get("title");
                String content = doc.get("content");
                TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), sd.doc, fields[0],
                        new SmartChineseAnalyzer());// 获取tokenstream
                Fragmenter fragment = new SimpleSpanFragmenter(scoreTitle);
                hlqTitle.setTextFragmenter(fragment);
                String hl_title = hlqTitle.getBestFragment(tokenStream, title);// 获取高亮的片段，可以对其数量进行限制

                tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), sd.doc, fields[1],
                        new SmartChineseAnalyzer());
                fragment = new SimpleSpanFragmenter(scoreContent);
                hlqContent.setTextFragmenter(fragment);
                String hl_content = hlqTitle.getBestFragment(tokenStream, content);// 获取高亮的片段，可以对其数量进行限制
                FileModel fm = new FileModel(hl_title != null ? hl_title : title,
                        hl_content != null ? hl_content : content);
//                System.out.println(hl_title);
//                System.out.println(hl_content);
                hitsList.add(fm);
            }
            dir.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }
        return hitsList;
    }

}
