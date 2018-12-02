## 第三章 入门的ANTLR项目
>作为我们的第一个ANTLR项目，我们会构造一个语法，它是C语言或其他继承者Java语法的一个很小的字迹。

### 3.1 ANTLR工具、运行库以及自动生成的代码
>我们完成工作的一般步骤是：首先我们对一个语法运行ANTLR，然后将生成的代码与jar包中的运行库一起编译，最后将编译好的代码和运行库放在一起运行。

* starter/ArrayInit.g4
```g4
/**
 * 语法文件通常以grammar关键字开头
 * 这是一个名为ArrayInit的语法，它必须和文件名ArrayInit.g4相匹配
 */
 grammar ArrayInit;
 
 /** 一条名为init的规则，它匹配一对花括号中的、逗号分隔的value
 init : '{' value (',' value)* '}';		// 必须匹配至少一个value
 
 /** 一个value可以是嵌套的花括号结构，也可以是一个简单的整数，即INT词法符号	*/
 value : init
 	| INT
 	;
 //语法分析器的规则必须以小写字母开头，词法分析器的规则必须用大写字母开头
 INT : [0-9]+;				// 定义词法符号INT，它由一个或多个数字组成
 WS : [\t\r\n}+  -> skip ;  //定义词法规则“空白符号”，丢弃之
```
>$> antlr4 ArrayInit.g4 命令生成了很多文件
* ArrayInitParser.java
> 该文件包含一个语法分析器的定义，这个语法分析器专门用来识别我们的“数组语言”的语法ArrayInit
* ArrayInitLexer.java
> 在该类中，每条规则都有对应的方法，除此之外，还有一些其他的辅助代码
* ArrayInit.tokens
> ANTLR会给每个我们定义的词法符号指定一个数字形式的类型，然后将它们的对应关系存储于该文件中。
* ArrayInitListener.java、ArrayInitBaseListener.java
> 默认情况下，ANTLR生成的语法分析器能将输入文本转换为一棵语法分析树。在遍历树的时候，遍历器能够触发一系列的“事件”，并通知我们提供的监听器对象。

### 3.2 测试生成的语法分析器
> 对语法运行ANTLR之后，我们需要编译自动生成的Java源代码。简单起见，我哦们在工作目录/tmp/array下完成所有的编译工作。

>$> cd /tmp/array

>$> javac *.java			#编译ANTLR自动生成的代码

* 我们使用第一章中提到的grun别名来启动TestRig，执行对语法的测试。下面的命令告诉我们如何将词法分析器生成的词法符号打印出来。

>$> grun ArratInit init -tokens

* 如果需要语法分析器关于输入文本识别过程中的更多信息，我们可以使用“-tree”选项查看语法分析树

>$> grun ArrayInit init -tree

* 另外我们也可以使用“-gui”选项生成一个可视化的对话框。

>$> grun ArrayInit init -gui

### 3.3 将生成的语法分析器与Java程序集成
> 在语法准备就绪之后，我们就可以将ANTLR自动生成的代码和一个更大的程序进行集成。如下：
* starter/Test.java
```java
//导入 ANTLR的运行库
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Test{
    public static void main(String[] args){

	//1. 新建一个CharSteam,从标准输入流读取数据
        CharStream inputStream = CharStreams.fromStream(System.in);
        //2. 新建一个词法分析器，处理CharSteam
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        //3. 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //4. 新建一个语法分析器，处理词法符号缓冲区中的内容
        ArrayInitParser parser = new ArrayInitParser(tokens);
        //5. 针对init规则，开始语法分析
        ParseTree tree = parser.init();
        //6. 用LISP风格打印生成的树
        System.out.println(tree.toStringTree(parser));
    }
}
```

### 3.4 构建一个语言类应用的程序
>我们继续完成能够处理数组初始化语句的示例程序，下一个目标是能够翻译初始化语句，而不仅仅是能够识别它们。例如，我们想要将Java中，类似{99,3,45}
>的short数组翻译成"\u0063\u0003\u01c3"。注意，其中十进制数字99的十六进制表示是63。

>我们如果想要通过编写程序来操作输入的数据的话，只需要继承ArrayInitBaseListener类，然后覆盖其中必要的方法即可。我们的基本思想是，在遍历器进行
>语法分析树的遍历时，令每个监听器方法翻译输入数据的一部分并将结果打印出来。

* {    99 ,   3 ,   451  }
* " \u0063 \u0003 \u01c3 "

>1. 将{翻译为"。
>2. 将}翻译为"。
>3. 将每个整数翻译为4位的十六进制形式，然后加前缀\u。

* starter/ShortToUnicodeString.java
```java
/** 将类似{1,2,3}的short数组初始化语句翻译为"\u001\u002\u003"  */
public class ShortToUnicodeString extends ArrayInitBaseListener {
    /**
     * 将原本的“{ ”翻译为“" ”
     * @param ctx
     */
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        System.out.print('"');
    }

    /**
     * 将原本的“}”翻译为“" ”
     * @param ctx
     */
    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        System.out.print('"');
    }

    /**
     * 将每个整数翻译为四位的十六进制形式，然后加前缀【\u】
     * @param ctx
     */
    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        int value = Integer.valueOf(ctx.INT().getText());
        System.out.printf("\\u%04x",value);
    }
}
```

>我们不需要覆盖每个enter/exit方法，我们只需要覆盖自己需要的那些。上述代码中唯一令我们不那么熟悉的一个表达式是ctx.INT(),它从上下文对象中获取INT
>词法符号对应的整数值，该词法符号由匹配value规则得来。现在，剩下的事情就是把之前的Test类扩展成一个翻译程序了。

*starter/Translate.java
```java
public class TranslateTest {
    public static void main(String[] args){
        //新建一个CharSteam，从标准输入读取数据
        CharStream inputStream = CharStreams.fromStream(System.in);
        //新建一个词法分析器，处理输入的CharSteam
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //新建一个语法分析器，处理词法符号缓冲区中的内容
        ArrayInitParser parser = new ArrayInitParser(tokens);
        //针对init规则，开始语法分析
        ParseTree tree = parser.init();

        //新建一个通用的、能够触发回调函数的语法分析树遍历器
        ParseTreeWalker walker = new ParseTreeWalker();
        //遍历语法分析过程中生成的语法分析器，触发回调
        walker.walk(new ShortToUnicodeString(),tree);
        //翻译完成后，打印一个\n
        System.out.println();
    }
}
```
>这份代码和之前代码的唯一区别在于最后一部分，它新建了一个语法分析树遍历器，令器对语法分析器生成的语法分析树进行遍历。在遍历器遍历过程中，它触发了我们的
>ShortToUnicodeString监听器的回调函数。情注意：限于篇幅，为了使读者的注意力更加集中，在本书剩下的章节中，通常给出代码的关键部分而不是完整的代码。

>一切顺利。无需深入理解语法的细节，我们就完成了我们的第一个翻译器。我们所做的一切不过是实现了几个方法，在这些方法中打印出对输入文本的适当的翻译结果。
>另外，我们可以通过给遍历器穿第一个不同的监听器以实现完全不同的输入。监听器有效地将语言类应用程序和语法进行了解耦，从而使得同一个语法能够被不同的程序复用。