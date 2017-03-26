package updateDB;


import java.util.ArrayList;

import  algorithm.AprioriTool;
import algorithm.FrequentItem;
import algorithm.related;
import txt_work.JdbcConnect;


public class save_DB {
	

	public static ArrayList<related> rs;
	public static ArrayList<String> lists=AprioriTool.getlist();	
  
	
	/**        把 result类  的实例化对象 i 存入数据库。 
	 * @param temp_rs
	 */
	public  static void save_res(ArrayList<related> temp_rs){
	    
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(temp_rs.size());
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		
		
		 JdbcConnect conn = new JdbcConnect();
	        int flag = conn.update(temp_rs);
		
		
		
		for ( related i : temp_rs) {		
							
			// 
				System.out.print("支持度 是"+i.getSupport());				
				
				System.out.print(" {");
													
				   	System.out.print(i.getRelated());							
								
				System.out.print("} ");
				System.out.println();				
		}
	return ;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//get_temp_res();
	}

}
