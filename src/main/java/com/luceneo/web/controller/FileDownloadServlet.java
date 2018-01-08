package com.luceneo.web.controller;

import com.sun.javaws.Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileDownloadServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html;charset = GBK";

    public FileDownloadServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("Served at:").append(req.getContextPath());
        resp.reset();
        resp.setContentType(CONTENT_TYPE);
        String filename = req.getParameter("filename");
        System.out.println("文件路径：" + req.getServletContext().getRealPath("/files") + "\\" + filename);
        File file = new File(req.getServletContext().getRealPath("/files") + "\\" + filename);
        System.out.println(file.getPath());

        // 设置response的编码方式
        resp.setContentType("application/octet-stream");
        // 写明要下载的文件的大小
        resp.setContentLength((int) file.length());

        /**
         *  解决中文乱码 向客户端发送返回页面的头信息
         *  1.Content-disposition 是MIME协议的扩展
         *  2.attachment-附件下载
         *  3.在客户端弹出下载框
         *  4.文件下载的关键代码
         */
        resp.setHeader("Content-Disposition",
                "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
        // 读出文件到i/o流
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream buff = new BufferedInputStream(fis);
        byte[] b = new byte[1024];// 相当于我们的缓存
        int k = 0;// 该值用于计算当前实际下载了多少字节
        // 从response对象中得到输出流,准备下载
        OutputStream myout = resp.getOutputStream();
        // 开始循环下载
        while (-1 != (k = fis.read(b, 0, b.length))) {
            // 将b中的数据写到客户端的内存
            myout.write(b, 0, k);
        }
        // 将写入到客户端的内存的数据,刷新到磁盘
        myout.flush();
        fis.close();
        buff.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
