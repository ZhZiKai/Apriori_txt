package algorithm;
/**
 * apriori���������ھ��㷨������
 * @author lyq
 *
 */
public class Client { 
	public static void main(String[] args){
		//String filePath = "C:\\Users\\lyq\\Desktop\\icon\\testInput.txt";
		 String filePath = "testInput.txt";
		
		AprioriTool tool = new AprioriTool(filePath, 2);
		tool.printAttachRule(0.7);
	}
}