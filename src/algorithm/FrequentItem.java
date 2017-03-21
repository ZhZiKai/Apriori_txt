package algorithm;
/**
 * Ƶ���
 * 
 * @author zzk
 * 
 */
public class FrequentItem implements Comparable<FrequentItem>{
	
	// Ƶ����ļ���ID
	private String[] idArray;
	
	// Ƶ�����֧�ֶȼ���
	private int count;
	
	//Ƶ����ĳ��ȣ�1�����2��������3�
	private int length;

	public FrequentItem(String[] idArray, int count){
		this.idArray = idArray;
		this.count = count;
		length = idArray.length;
	}

	public String[] getIdArray() {
		return idArray;
	}

	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int compareTo(FrequentItem o) {
		// TODO Auto-generated method stub
		return this.getIdArray()[0].compareTo(o.getIdArray()[0]);
	}
	
}
