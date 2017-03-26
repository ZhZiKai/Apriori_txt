package algorithm;


import java.util.ArrayList;

import txt_work.work;
import updateDB.save_DB;     //引入save_DB类
import algorithm.AprioriTool;

/**
 * apriori关联规则挖掘算法调用类
 * @author zzk
 *
 */
public class Client { 
	
	
	/**从包 txt_work.work 中获取 Apriori算法的输入项集 word_num --> word_num1.
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> get_input() throws Exception {
		  ArrayList<String> word_num1 = new ArrayList<String>();
		  word_num1= work.getas();
		  return word_num1;                 													  									
	}
		
	
	public static void main(String[] args) throws Exception{
		
		// 从 get_input() 方法获取算法的输入 temp_as.
		ArrayList<String> temp_as=new ArrayList<String>();     
		temp_as=get_input();
		
		AprioriTool tool = new AprioriTool( temp_as,2);
		ArrayList<related> rs=new ArrayList<related>();
		rs=tool.showRelated(0.7);	
		
		save_DB.save_res(rs);
		
	}
}