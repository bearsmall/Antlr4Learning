* ArrayInitParser
>该文件包含一个语法分析器的定义，这个语法分析器专门用来识别我们的“数组语言”的语法ArrayInit
>>该类中，每条规则都有对应的方法，除此之外，还有一些其他的辅助代码。
* ArrayInitLexer
> ANTLR能够自动识别出我们的语法中的文法规则和词法规则。这个文件包含的是
>词法分析器的类定义，它是由ANTLR通过分析词法规则INT和WS，以及语法中的字面值'{'、','，和'}'生成的
>。回想一下上一章的内容，词法分析器的作用是讲输入字符序列分解成词汇符号。
* ArrayInit.tokens
> ANTLR会给每个我们定义的词法符号指定一个数字形式的类型，然后将它们的对应关系存储于该文件中。
> 有时，我们需要将一个大型语法切分为多个更小的语法，在这种情况下，这个文件就非常有用了。通过
> 它，ANTLR可以在多个小型语法间同步全部的语法符号类型。
* ArrayInitListener、ArrayInitBaseListener
> 默认情况下，ANTLR生成的语法分析器能将输入文本转换为一棵语法分析树。在遍历语法分析树时，遍历器
> 能够触发一系列“事件”（回调），并通知我们提供的监听器对象。ArrayInitListener接口给出了这些回调
> 方法的定义，我们可以实现它来完成自定义的功能。ArrayInitBaseListener是该接口的默认实现类，
> 为其中的每个方法提供了一个空实现。ArrayInitBaseListener使得我们只需要覆盖那些我们感兴趣的回调方法
> 。通过指定-visitor命令行参数，ANTLR也可以为我们生成语法分析树的访问器。