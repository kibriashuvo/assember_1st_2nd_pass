import java.util.HashMap;
import java.util.LinkedList;

public class tester {

	public static void manageFirstPass(String newLine){
		String temp[] = newLine.split(" ");
		String var = temp[0]+",";
		int address = Integer.parseInt(temp[1]);

		LinkedList<String> proMap= processVarPart(var);;
		
		String processedAddress= processAddPart(address);
		
		for(String s:proMap){
			System.out.println(s);
		}
	
	}
	
	public static String processAddPart(int add){
		String address = String.format("%04d", add);
		String hexValue="(LC) ";
		for(int i=0;i<4;i++){
			int curr = Integer.parseInt(""+address.charAt(i));
			hexValue = hexValue + String.format("%4s", Integer.toBinaryString(curr)).replace(' ', '0')+" ";
			
		}
		return hexValue;
	}
	
	//-------------------Var Helper----------------
	public static String getHexValue(char symbol) {
		int ascii = (int) symbol;
		return String.format("%8s", Integer.toBinaryString(ascii)).replace(' ', '0');
	}
	
	public static LinkedList<String> processVarPart(String var){
		
		int varsize = var.length();
		HashMap<Character, String> parser = new HashMap<Character, String>();

		
		//-------generate------------------
		
		for (int i = 0; i < varsize; i++) {
			String hexValue = getHexValue(var.charAt(i));
			parser.put(var.charAt(i), hexValue);
		//	System.out.println(var.charAt(i)+" "+hexValue);
		}
		
		return printVarPart(var, parser);
	}
	
	public static LinkedList<String> printVarPart(String var, HashMap<Character, String> map){
		int varsize = var.length();
		LinkedList<String> printable = new LinkedList<String>();
		
		//------------Managing less than 3 characters-----------------
		
		if(varsize==2){
			String space = getHexValue(' ');
			printable.add(var.charAt(0)+" "+" "+" "+map.get(var.charAt(0))+" "+space);
			printable.add(" "+" "+var.charAt(1)+" "+space+" "+map.get(var.charAt(1)));
			
		}else if(varsize==3){
			String space = getHexValue(' ');
			printable.add(var.charAt(0)+" "+var.charAt(1)+" "+map.get(var.charAt(0))+" "+map.get(var.charAt(1)));
			printable.add(" "+" "+var.charAt(2)+" "+space+" "+map.get(var.charAt(2)));
			
			
		}else{
			printable.add(var.charAt(0)+" "+var.charAt(1)+" "+map.get(var.charAt(0))+" "+map.get(var.charAt(1)));
			printable.add(var.charAt(2)+" "+var.charAt(3)+" "+map.get(var.charAt(2))+" "+map.get(var.charAt(3)));
			
		}
		
		
		
		return printable;
	}

	public static void main(String[] args) {
		// --------read a line------------

		String m = "A 105";
		String temp[] = m.split(" ");

		LinkedList<String> proMap;

		String var = temp[0]+",";
		int address = Integer.parseInt(temp[1]);
		

		proMap = processVarPart(var);
		
	//System.out.println(proMap.));
		System.out.println(processAddPart(address));
		
		for(String s:proMap){
			System.out.println(s);
		}
	

	}

}
