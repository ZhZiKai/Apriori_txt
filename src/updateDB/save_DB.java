package updateDB;


import java.util.ArrayList;

import  algorithm.AprioriTool;
import algorithm.FrequentItem;
import algorithm.related;
import txt_work.JdbcConnect;


public class save_DB {
	

	public static ArrayList<related> rs;
	public static ArrayList<String> lists=AprioriTool.getlist();	
  
	
	/**        �� result��  ��ʵ�������� i �������ݿ⡣ 
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
				System.out.print("支持度是"+i.getSupport());				
				
				System.out.print(" {");
													
				   	System.out.print(i.getRelated());							
								
				System.out.print("} ");
				System.out.println();				
		}
	return ;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		//get_temp_res();
	}

}
