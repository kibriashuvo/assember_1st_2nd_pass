import java.util.HashSet;
import java.util.Set;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "MIN, DEC 23";
		
		String t[] = a.split(" ");
		
		Set<String> s = new HashSet<String>();
		
		s.add("DEC");
		s.add("END");
		s.add("HEX");
		s.add("ORG");
		
			
		String temp[] = a.split(" ");
		
		//short twos = (short)Integer.parseInt(Integer.toBinaryString(-23));
		
		String decValue = String.format("%16s", Integer.toBinaryString(-23)).replace(' ', '0');
		
		int len = decValue.length();
		//int i = Integer.parseInt(decValue);
		String p ="";
		for(int i=31;i>15;i--){
			p = decValue.charAt(i)+p;
		}
		String sd = Integer.toHexString(0);
		
		int i = Integer.parseInt("A", 16);
	    String bin = Integer.toBinaryString(i);
		
		System.out.println(bin);
	}

}
