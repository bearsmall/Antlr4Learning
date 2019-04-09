package com.cmp.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class IOAgent {

    private static volatile IOAgent ioagent = null;

    /**
     * 文件BOM头的数值
     */
    private final int BOM_VALUE = 65279;

    private IOAgent() {
    }

    /**
     * @return 获取单例对象
     */
    public static IOAgent getInstance() {
        if (ioagent == null) {
            synchronized (IOAgent.class){
                if(ioagent==null){
                    ioagent = new IOAgent();
                }
            }
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
        if (!"\n".equals(linesep)) {
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
    public String getFileTextOld(File f) {
        //为空、不存在、不是文件、不可读
        if (f == null || !f.exists() || !f.isFile() || !f.canRead()) {
            return null;
        }
        if (f.length() <= 0) {
            return null;
        }
        //得到该文件的编码格式字符串
        String type = getCodeType(f);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(f);
            //指定读取文件时以type的编码格式读取
            isr = new InputStreamReader(fis, type);
            br = new BufferedReader(isr);
            //文本文件一般比较小
            char[] content = new char[fis.available()];
            int textLen = br.read(content);
            int offset = 0;
            //去掉BOM头无效字符
            if (BOM_VALUE == (int) content[0]) {
                offset = 1;
            }
            String ret = String.valueOf(content, offset, textLen - offset);
            return unifyLineSeparator(ret);
        } catch (IOException e) {
            return null;
        }finally {//资源关闭【import！不关闭会导致内存泄露或频繁的full gc】
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(isr!=null){
                        try {
                            isr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if(fis!=null){
                                try {
                                    fis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public String getFileText(File f) {
        //为空、不存在、不是文件、不可读
        if (f == null || !f.exists() || !f.isFile() || !f.canRead()) {
            return null;
        }
        if (f.length() <= 0) {
            return null;
        }
        //得到该文件的编码格式字符串
        String type = getCodeType(f);
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(f);
            channel = fis.getChannel();
            int capacity = 4096;
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;
            StringBuilder sb = new StringBuilder();
            while ((length = channel.read(bf)) != -1) {
                bf.clear();
                byte[] bytes = bf.array();
                sb.append(new String(bytes,0,length,type));
            }
            bf.clear();
            return unifyLineSeparator(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * new method to get the encoding type（accurate but too slow）
     * @param f
     * @return
     */
    private String getCodeTypeNew(File f) {
        String encode = EncodingDetect.getJavaEncode(f.getAbsolutePath());
        return encode;
    }
    /**
     * 判断文件f的字符编码
     *
     * @param f
     *            需要进行分析的文件
     * @return 文件f的字符编码名称
     */
    public static String getCodeType(File f) {
        final byte _ef = (byte) 0xef;
        final byte _bb = (byte) 0xbb;
        final byte _bf = (byte) 0xbf;
        final byte _fe = (byte) 0xfe;
        final byte _ff = (byte) 0xff;
        byte[] bom = new byte[10];
        int cn = -1;
        try {
            FileInputStream is = new FileInputStream(f);
            cn = is.read(bom);
            is.close();
        } catch (Exception ex) {
        }
        if (cn >= 3 && bom[0] == _ef && bom[1] == _bb && bom[2] == _bf) {
            return "UTF-8";
        } else if (cn >= 2 && bom[0] == _ff && bom[1] == _fe) {
            return "Unicode";
        } else if (cn >= 2 && bom[0] == _fe && bom[1] == _ff) {
            // Unicode big endian
            return "Unicode";
        } else {
            // 初步认为是文件无BOM头，返回当前操作系统的默认文件编码
            return System.getProperty("file.encoding");
        }
        // String os = System.getProperty("os.name").toLowerCase();
        // if (os.indexOf("win") >= 0) {// windows
        // } else if (os.indexOf("mac") >= 0) {// mac
        // } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {//
        // linux或unix
        // }else {
        // }
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
