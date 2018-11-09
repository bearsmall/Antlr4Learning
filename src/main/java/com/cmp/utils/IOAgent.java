package com.cmp.utils;

import java.io.*;

public final class IOAgent {

    private static IOAgent ioagent = null;

    /**
     * 文件BOM头的数值
     */
    private final int BOM_VALUE = 65279;

    private IOAgent() {
    }

    /**
     * @return 获取单例对象
     */
    public static synchronized IOAgent getInstance() {
        if (ioagent == null) {
            ioagent = new IOAgent();
        }
        return ioagent;
    }

    /**
     * 判断字符是否为空
     * @param cs 字符
     * @return 空字符：true，非空字符串：false
     */
    public static boolean isBlank(CharSequence cs){
        int strLen;
        if ((cs == null) || ((strLen = cs.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将文本内容设置统一字符串的行结束符为'\n'
     * @param content 需要进行处理的文本字符串
     * @return 经过处理的文本字符串
     */
    public static final String unifyLineSeparator(String content) {
        /**
         * 空字符串判断
         */
        if (isBlank(content)) {
            return null;
        }
        /*
         * 如果字符串中没有'\r'字符，则无需处理
         */
        if (content.indexOf('\r') < 0) {
            return content;
        }
        /*
         * 【有"\r\n"出现，则认为其行结束符为"\r\n"】
         */
        if (content.indexOf("\r\n") >= 0) {
            return content.replaceAll("\r\n", "\n");
        }
        return content.replaceAll("\r", "\n");
    }

    /**
     * 还原分隔符，因为在本程序内部，行分隔符统一为'\n'， 这里取到系统的行分隔符并将'\n'替换为该分隔符
     * 【必须要在统一分隔符之后调用】
     * @param content 需要进行还原分隔符处理的文本内容
     * @return 处理好的文本内容
     */
    private final String restoreLineSeparator(String content) {
        if (isBlank(content)) {
            return null;
        }
        String linesep = System.getProperty("line.separator");
        if (!linesep.equals("\n")) {
            content = content.replaceAll("\n", linesep);
        }
        return content;
    }

    /**
     * 得到磁盘(文本)文件的内容，并将内容的行结束符统一为'\n'
     *
     * @param filePath   给定的磁盘文件的文件路径
     * @return 文件内容
     */
    public final String getFileText(String filePath) {
        if (isBlank(filePath)) {
            return null;
        }
        File f = new File(filePath);
        return this.getFileText(f);
    }

    /**
     * 得到磁盘(文本)文件的内容，并将内容的行结束符统一为'\n'
     *
     * @param f  给定的磁盘文件
     * @return 文件内容
     */
    public String getFileText(File f) {
        /**
         * 为空、不存在、不是文件、不可读
         */
        if (f == null || !f.exists() || !f.isFile() || !f.canRead()) {
            return null;
        }
        int len = (int) f.length();
        if (len <= 0) {
            return null;
        }
        /*
         * 得到该文件的编码格式字符串
         */
        String type = getCodeType(f);

        try {
            FileInputStream fis = new FileInputStream(f);
            /*
             * 指定读取文件时以type的编码格式读取
             */
            InputStreamReader isr = new InputStreamReader(fis, type);
            BufferedReader br = new BufferedReader(isr);
            char[] content = new char[len]; //文本文件一般比较小
            int textLen = br.read(content);
            int offset = 0;
            /*
             * 去掉BOM头无效字符
             */
            if (BOM_VALUE == (int) content[0]) {
                offset = 1;
            }
            String ret = String.valueOf(content, offset, textLen - offset);
            return unifyLineSeparator(ret);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * new method to get the
     * @param f
     * @return
     */
    private String getCodeType(File f) {
        String encode = EncodingDetect.getJavaEncode(f.getAbsolutePath());
        return encode;
    }

    /**
     * 写磁盘文件方法
     *
     * @param f
     *            要向其中写入内容的文件，此文件必须已经存在，并且具有相应的写入权限
     * @param text
     *            要写入的文本内容
     * @param append
     *            是否采用追加的写入方式(否则为覆盖的写入方式)
     * @param charset
     *            文件采用的字符编码
     * @return 操作是否成功
     */
    public boolean setFileText(File f, String text, boolean append,
                               String charset) {
        if (f == null || !f.exists() || !f.isFile() || !f.canWrite()) {
            return false;
        }
        if (isBlank(text)) {
            return false;
        }
        try {
            FileOutputStream fos = new FileOutputStream(f, append);
            OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
            BufferedWriter bw = new BufferedWriter(osw);
            if (!append) {
                // 写入文件BOM头
                bw.write(BOM_VALUE);
            }
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     *
     * 写磁盘文件方法
     *
     * @param filepath
     *            要向其中写入内容的文件的文件路径
     * @param text
     *            要写入的文本内容
     * @param append
     *            是否采用追加的写入方式(否则为覆盖的写入方式)
     * @param charset
     *            文件采用的字符编码
     * @return 操作是否成功
     */
    public boolean setFileText(String filepath, String text, boolean append,
                               String charset) {
        if (isBlank(filepath)) {
            return false;
        }
        File f = new File(filepath);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return setFileText(f, text, append, charset);
    }

    /**
     *
     * 采用默认方式(采用系统默认字符编码和行结束符，写入BOM文件头，替换原有文件)写磁盘文件的方法
     *
     * @param filepath
     *            要向其中写入内容的文件的文件路径
     * @param text
     *            要写入的文本内容
     * @return 操作是否成功
     */
    public boolean setFileText(String filepath, String text) {
        String charset = System.getProperty("file.encoding");
        text = restoreLineSeparator(text);
        return this.setFileText(filepath, text, false, charset);
    }

}
