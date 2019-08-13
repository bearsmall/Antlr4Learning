package com.tool;

import com.cmp.utils.IOAgent;
import com.extract.demo1.function.FunctionLevelCodeSimplifier;

import java.io.File;

public abstract class SingleFileTask{

    public TaskResult invoke(File file){
        TaskResult beforeResult = new TaskResult();
        beforeResult.setFile(file);
        String code = IOAgent.getInstance().getFileText(file);//源文件读取
        beforeResult.setCode(code);
        runTask(beforeResult);
        return beforeResult;
    }

    protected abstract void runTask(TaskResult beforeResult);

}
