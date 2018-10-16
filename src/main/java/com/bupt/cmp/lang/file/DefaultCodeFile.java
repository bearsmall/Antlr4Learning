package com.bupt.cmp.lang.file;

import com.bupt.cmp.common.IOAgent;
import com.bupt.cmp.lang.Language;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bearsmall on 2017/9/24.
 */
public abstract class DefaultCodeFile{
    private List<LineStruct> textLine=new ArrayList<LineStruct>();//文本节点数组
    private List<LineStruct> tokenLine=new ArrayList<LineStruct>();//token节点数组
    private List<StoreNode> tree=new ArrayList<StoreNode>();//语法树节点数组
    private List<String> line = new ArrayList<>();//源码文本字符串列表
    protected Language lang = null; //本代码文件所用的编程语言
    protected String content = null; //代码内容
    protected String filePath = null; //文件路径
    protected String name = null; //文件名称
    protected long lineNum = 0; //代码总行数
    protected long charNum = 0; //代码的总字符数(unicode字符)
    private int charrepeat = 1; //按字符进行处理的重度
    private int[] charHashs = null; //将本代码文本按charrepeat处理得到的hash值数组

    public List<LineStruct> getTextLine() {
        return textLine;
    }

    public void setTextLine(List<LineStruct> textLine) {
        this.textLine = textLine;
    }

    public List<LineStruct> getTokenLine() {
        return tokenLine;
    }

    public void setTokenLine(List<LineStruct> tokenLine) {
        this.tokenLine = tokenLine;
    }

    public List<String> getLine() {
        return line;
    }

    public void setLine(List<String> line) {
        this.line = line;
    }

    public List<StoreNode> getTree() {
        return tree;
    }

    public void setTree(List<StoreNode> tree) {
        this.tree = tree;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLineNum() {
        return lineNum;
    }

    public void setLineNum(long lineNum) {
        this.lineNum = lineNum;
    }

    public long getCharNum() {
        return charNum;
    }

    public void setCharNum(long charNum) {
        this.charNum = charNum;
    }

    public int getCharrepeat() {
        return charrepeat;
    }

    public void setCharrepeat(int charrepeat) {
        this.charrepeat = charrepeat;
    }

    public int[] getCharHashs() {
        return charHashs;
    }

    public void setCharHashs(int[] charHashs) {
        this.charHashs = charHashs;
    }

    public DefaultCodeFile(Language lang) {
        this.lang = lang;
    }

    /**
     * 最后一行有\n\n
     * @param file
     */
    @Deprecated
    public void init_ends(File file){
        filePath = file.getAbsolutePath();
        name = file.getName();
        try {
            content = IOAgent.getInstance().getFileText(filePath);
            BufferedReader reader = new BufferedReader(new StringReader(content));
            List<String> list = new ArrayList<>();
            for(String li = reader.readLine(); li != null; li = reader.readLine()) {
                list.add(li);
            }
            lineNum = list.size();
            line = list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = clearIllegalCharacter(content);
        //CommentRemover.getInstance().pretreat(this);
    }
    /**
     * 使用一个File类对象初始化一个工程文件对象，注意这个File对象必须的确存在(在磁盘中)，否则可能出错
     *
     * @param f
     */
    protected final void init(File f) {
        //TODO 可优化
        IOAgent ioagent = IOAgent.getInstance();
        try {
            filePath = f.getCanonicalPath();
            name = f.getName();
            content = ioagent.getFileText(f);
            char[] ca = content.toCharArray();
            charNum = ca.length;
            lineNum = 1;
            for(int i = 0; i < charNum; i++) {
                if(ca[i] == '\n') {
                    lineNum++;
                }
            }
            int index = filePath.lastIndexOf(".") + 1;
            if (index > 0) {
                // suffix = filePath.substring(index);
            }
        } catch (Exception e) {
        }
    }

    public void init(String code){
        filePath = "filePath";
        name = "name";
//        code.replaceAll("\r","");
        BufferedReader reader = new BufferedReader(new StringReader(code));
        List<String> list = new ArrayList<>();
        try {
            for(String li = reader.readLine(); li != null; li = reader.readLine()) {
                list.add(li);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//      String[] lines = code.split("\n");
//      line = Arrays.asList(lines);
        lineNum = list.size();
        line = list;
        code = clearIllegalCharacter(code);
        content = IOAgent.unifyLineSeparator(code);
//        CommentRemover.getInstance().pretreat(this);
    }

    private String clearIllegalCharacter(String str) {
        String text = str + "\n\n";
        char[] solvedText = text.toCharArray();

        String ans="";
        for(int i=0;i<solvedText.length;i++){
            if(solvedText[i]=='\\')
            {
                //System.out.println(i+" 01");
                if(solvedText[i+1]=='\\') {
                    ans+=' ';
                    i++;
                }
                else
                    for(int j=i+1;j<solvedText.length;j++)
                    {
                        char k = solvedText[j];
                        if(k == '\t'||k == ' '||k == '	') continue;
                        if(k == '\n') {
                            i=j;
                            ans+='\t';
                            break;
                        }
                        else {
                            ans+=' ';
                            i = j;
                            break;
                        }
                    }
            }
            else ans+=solvedText[i];
        }
        return ans;
    }

    /**
     * 提取代码的关键字序列
     * @param code  与本文件相同类型的一段代码文本
     * @return  整形数组表示的关键字序列
     */
    public abstract int[] convertCodeToKeySequence(String code);

    /**
     * @return 散列后的代码的关键字序列
     */
    public abstract int[] getKeySequence()  throws Exception;
}