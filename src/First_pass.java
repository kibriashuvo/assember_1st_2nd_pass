import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;



public class First_pass {
	
	//----------Variables----------
	int LC = 0;
	HashMap<String, String> addressSymbol = new HashMap<String,String>(); 					//For Future need
	LinkedList<String> ls = new LinkedList<>();												//For ease of Converting to hex
	//--------Variables-----------
	
	
	//---------File Read-------------------------
	
	public static void  fileRead(String filePath){
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	//------File Read End------------
	
	//--------File Write start----------------------
	
	public static void writeFile(String content){
	
		
		 try{
	          File file =new File("F://address_symbol_table.txt");
	    	  if(!file.exists()){
	    	 	file.createNewFile();
	    	  }
	    	  FileWriter fw = new FileWriter(file,true);
	    	  BufferedWriter bw = new BufferedWriter(fw);
	    	  PrintWriter pw = new PrintWriter(bw);	         
	    	  pw.println(content);	    	
	    	  pw.close();

		 
	       }catch(IOException ioe){
	    	   System.out.println("Exception occurred:");
	    	   ioe.printStackTrace();
	      }
	}
	
	
	//----------File Write end----------------------
	
	public void  fileReadFirstPass(String filePath){
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains(",")){
					
					String address[] = sCurrentLine.split(",");
					ls.add(address[0]+" "+LC);
					addressSymbol.put(address[0], addSymHashmap(LC));
					manageFirstPass(ls.poll());
					writeFile("");
					
				}
				else if(sCurrentLine.contains("ORG")){
					this.LC = Integer.parseInt(sCurrentLine.replaceAll("[\\D]", ""));
					continue;
					
				}else if(sCurrentLine.contains("END")){
					break;
				}
				this.LC++;
				//System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	//-------------------------Main Workers-------------------------------
	
	public static void manageFirstPass(String newLine){
		
		String temp[] = newLine.split(" ");
		String var = temp[0]+",";
		int address = Integer.parseInt(temp[1]);

		LinkedList<String> proMap= processVarPart(var);;
		
		String processedAddress= processAddPart(address);
		
		for(String s:proMap){
			writeFile(s);
		}
		writeFile(processedAddress);
	
	}
	
	public static String processAddPart(int add){
		String address = String.format("%04d", add);
		String hexValue="(LC)";
		for(int i=0;i<4;i++){
			int curr = Integer.parseInt(""+address.charAt(i));					// As charAt takes is char but Integr.parse(St)
			hexValue = hexValue + String.format("%4s", Integer.toBinaryString(curr)).replace(' ', '0')+" ";
			
		}
		return hexValue;
	}
	
	//----------------For Future----------------------------
	
	public static String addSymHashmap(int add){
		String address = String.format("%03d", add);
		String hexValue="";
		for(int i=0;i<3;i++){
			int curr = Integer.parseInt(""+address.charAt(i));
			hexValue = hexValue + String.format("%4s", Integer.toBinaryString(curr)).replace(' ', '0');
			
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

	
	
	//------------------------End Main----------------------

	 
	
	public static void main(String[] args) {
	
		First_pass fp  = new First_pass();
		fp.fileReadFirstPass("F://test.txt");
		
		System.out.println(fp.addressSymbol.get("SUB"));
		
		SecondPass sp = new SecondPass();
		
		sp.initiazizeSecondPass();
		
		
		
		
	}
}
