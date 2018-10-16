/*                                                                                      
 * 文件名：		PretreatDirector.java                                                       
 * 类名：		PretreatDirector                                                            
 * 创建日期：	2011-02-20                                                                  
 * 最近修改：	2013-04-25                                                                  
 * 作者：		徐犇                                                                          
 */                                                                                     
package com.bupt.cmp.pretreat;


import com.bupt.cmp.lang.Java;
import com.bupt.cmp.lang.Language;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import lombok.extern.slf4j.Slf4j;

/**
 * 执行预处理操作的类                                                                            
 *                                                                                      
 * @author ben                                                                          
 */
@Slf4j
public final class PretreatDirector {                                                   
                                                                                        
	/**                                                                                 
	 * 是否进行去注释操作的标志位                                                                    
	 */                                                                                 
	public static final int REMOVE_COMMENT = 1;                                         
                                                                                        
	/**                                                                                 
	 * 是否进行去除空行操作的标志位                                                                   
	 */                                                                                 
	public static final int REMOVE_EMPTY_LINE = 2;                                      
	                                                                                    
	/**                                                                                 
	 * 是否对续行符进行处理的标志位                                                                   
	 */                                                                                 
	public static final int TREAT_LINE_CONTINUE = 4;                                    
	                                                                                    
	/**                                                                                 
	 * 是否对代码中字符串进行解码的标志位                                                                
	 */                                                                                 
	public static final int DECODE_STRING = 8;                                          
	                                                                                    
	/**                                                                                 
	 * 是否对C/C++代码中的文件包含命令进行处理                                                           
	 */                                                                                 
	public static final int TREAT_INCLUDING = 16;                                       
	                                                                                    
	/**                                                                                 
	 * 是否对C/C++代码中的宏命令进行展开处理                                                            
	 */                                                                                 
	public static final int TREAT_MACRO = 32;                                           
	                                                                                    
	/**                                                                                 
	 * 是否对C/C++代码中的条件编译命令进行处理的标志位                                                       
	 */                                                                                 
	public static final int TREAT_CONDITIONAL_COMMAND = 64;                             
	/**                                                                                 
	 * 是否将代码中的注释替换成空行进行处理的标志位                                                           
	 */                                                                                 
	public static final int COMMENT_REPLACE = 128;                                      
	                                                                                    
	                                                                                    
	/**                                                                                 
	 * 记录需要进行哪些预处理的标志                                                                   
	 */                                                                                 
	private int flag = 0;                                                               
	                                                                                    
	/**                                                                                 
	 * 私有构造函数                                                                           
	 */                                                                                 
	private PretreatDirector(int flag) {                                                
		this.flag = flag;                                                               
	}                                                                                   
	                                                                                    
	/**                                                                                 
	 * @param flag 标记需要进行哪些预处理操作的标记位                                                    
	 * @return 一个本类的实例                                                                  
	 */                                                                                 
	public static PretreatDirector getInstance(int flag) {                              
		return new PretreatDirector(flag);                                              
	}                                                                                   
                                                                                        
	/**                                                                                 
	 * @param defaultCodeFile
	 *            需要进行预处理的代码文件                                                          
	 */                                                                                 
	public void pretreat(DefaultCodeFile defaultCodeFile) {
		if (defaultCodeFile == null) {
			return ;                                                                    
		}                                                                               
		Language lang = defaultCodeFile.getLang();
		log.debug(defaultCodeFile.getName());
		if(lang instanceof Java) {
			if((flag & REMOVE_COMMENT) > 0) {                                           
				Pretreator cr = CommentRemover.getInstance();                           
				log.debug("REMOVE_COMMENT");
				cr.pretreat(defaultCodeFile);
				
			}                                                                           
			if((flag & REMOVE_EMPTY_LINE) > 0) {                                        
				Pretreator elr = EmptyLineRemover.getInstance();                        
				log.debug("REMOVE_EMPTY_LINE");
				elr.pretreat(defaultCodeFile);
				
			}                                                                           
			if((flag & COMMENT_REPLACE) > 0) {                                          
				Pretreator crp = CommentReplace.getInstance();
				log.debug("COMMENT_REPLACE");
				crp.pretreat(defaultCodeFile);
			}                                                                           
		}                                                                               
	}                                                                                   
                                                                                        
}                                                                                       
                                                                                        