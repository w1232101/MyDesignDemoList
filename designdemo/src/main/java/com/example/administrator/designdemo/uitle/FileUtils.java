package com.example.administrator.designdemo.uitle;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/8/31.
 * FileUtils
 * getFileSize():获取文件夹大小
 * getFileSizes():文件大小
 *FormetFileSize() : 格式化
 * getlist(File f)：递归求取目录文件个数
 *
 */
public class FileUtils {
    private static FileUtils instance;

    public FileUtils()
    {

    }

    public static FileUtils getInstance()
    {
        if (instance == null)
        {
            instance = new FileUtils();
        }
        return instance;
    }


    public long getFileSizes(File f) throws Exception
    {
        long s = 0;
        if (f.exists())
        {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        }
        else
        {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }


    public long getFileSize(File f) throws Exception
    {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            }
            else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }


    public String FormetFileSize(long fileS)
    {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    public long getlist(File f)
    {// 递归求取目录文件个数
        long size = 0;
        File[] flist = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }
}
