package txt_work;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.sql.SQLException;
 
public class work {
	
	public static final  String  sql = "select summary from t_record limit 1";
	public static final  String  field = "summary";
	
	
	public static ArrayList<String> addDatalist(){
		ArrayList<String> list=new ArrayList<String>();
		try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            StringBuilder result = new StringBuilder();
            String theLine=null;
            while((theLine=reader.readLine())!=null){
                    list.add(theLine);
            }
            
            //测试 System.out.println(list.get(3587));
            
        }
		
        catch (IOException e) {
                e.printStackTrace();
        }
		return list;
	}
	
	public static ArrayList<String> addWordnum(ArrayList<String> list, ArrayList<String> text){
		ArrayList num = new ArrayList();
		int cnt=0;
		System.out.println("******");
		System.out.println(text.size());
		System.out.println("******");
		while (cnt<text.size()){
			String wordNumStr="T"+Integer.toString(cnt)+" ";
			String nowText=(String)text.get(cnt);
			
	        for (int i=0;i<list.size();i++){
	        	String tmp=(String)list.get(i);
	        	int flag=nowText.indexOf(tmp);
	        	if (flag!=-1){
	        		wordNumStr+=(Integer.toString(i)+" ");
	        	}
	        }
	    	num.add(wordNumStr);  			   		//wordNumStr类型是String ;   num类型是ArrayList<String>
	        cnt++;
		}
		return num;
	}
	
	//返回值作为Client.java的输入，类型  ArrayList<String>
	public static ArrayList<String> getas() throws SQLException{
		 ArrayList<String> total_text = new ArrayList();
	        ArrayList<String> list = new ArrayList<String>();
	        ArrayList<String> word_num = new ArrayList<String>();
	        
	        list = addDatalist();        //
	        JdbcConnect conn = new JdbcConnect();
	        total_text = conn.getSummary("select summary from t_record limit 1,5000", "summary");
	        word_num = addWordnum(list,total_text);
	     
		return word_num;
	}
			
	

	//main函数 仅用于测试
	public static void main(String[] args) throws SQLException {
    	
        ArrayList<String> total_text = new ArrayList();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> word_num = new ArrayList<String>();
        
//        total_text.add("1 2 3 4 5 6 77 8 2 1 5 8 4 ");
//        total_text.add("2 5 1 2 4 8 4 6 77 8 4 6");
//        System.out.println(total_text.size());
        list = addDatalist();        //
        JdbcConnect conn = new JdbcConnect();
        total_text = conn.getSummary("select summary from t_record limit 1,1000", "summary");
        word_num = addWordnum(list,total_text);
    
        
    	 /*   //word_num 中（ArrayList<String>） --> （String[]）             
        	String test[] = new String[word_num.size()];
        	
        	 test = word_num.toArray(test);			    	 
        	 for(String temp:test){
        		 System.out.println(temp);
        	 }
        	 */
        	    
   
        System.out.println(word_num);		         	//     类型  ArrayList<String>
         
         
        
//        
        
//		文件操作 勿删
// 		total_text = summary
//        try {
//        	FileWriter writer;
//        	writer = new FileWriter("test2.txt",true);
//        	writer.write("filename"+" ");
//        	for (int j=0;j<word_num.size();j++){
//        		writer.write(word_num.get(j)+" ");
//        		System.out.print(word_num.get(j)+" ");
//        		writer.flush();
//        	}
//        	writer.write("\r\n");
//        	writer.close();
//    	}
//        catch (IOException e) {
//        	e.printStackTrace();
//        }
    }
}