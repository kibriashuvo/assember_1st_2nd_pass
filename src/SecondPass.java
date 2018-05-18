import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SecondPass {
	
	//---------Variables-----------
	int LC = 0;
	HashMap<String, String> MRI = new HashMap<String,String>();
	HashMap<String, String> nonMRI = new HashMap<String,String>();
	HashMap<String, String> addressSymbolSP = new HashMap<String,String>(); 					//For Future need
	LinkedList<String> pseudoInstruction = new LinkedList<String>();
	LinkedList<String> printable = new LinkedList<String>();
	
	
	//----------------------------------------------------------
	
	//-----------------Checkers--------------------------------
	public static boolean isPseudo(String line,LinkedList<String> list){
		for(String s:list){
			if(line.contains(s))
				return true;
		}
		return false;
	}
	
	public static boolean isMRI(String line,HashMap<String, String> map){
		String test[] = line.split(" ");
		if(map.containsKey(test[0]))
			return true;
		return false;
	}
	
	public static boolean isNonMRI(String line,HashMap<String, String> map){
		String test[] = line.split(" ");
		if(map.containsKey(test[0]))
			return true;
		return false;
	}
	
	public static boolean isIndirect(String line){
		String test[] = line.split(" ");
		if(test.length==3)
			return true;
		return false;
	}
	//-------------------MRI workers----------------------
	
	
	
	//-----------------MRI workers-------------------------
	
	public static String convertDEC(String line){
		int operend = getOperend(line);
		String decValue = String.format("%16s", Integer.toBinaryString(operend)).replace(' ', '0');
		if(operend<0){
			String hack = "";
			for(int i=31;i>15;i--){
				hack = decValue.charAt(i)+hack;
			}
			return hack;
			
			
		}
		
		return decValue;
		
	}
	
	public static String convertHEX(String line){				//Used a different method here :p
		String operend = getOperendForHex(line);
		int len = operend.length();
		while(len<4){
			operend = 0+operend;
			len++;
		}
		
		String hexValue="";
		for(int i=0;i<4;i++){
			int parser = Integer.parseInt(""+operend.charAt(i),16);
			hexValue = hexValue+String.format("%4s", Integer.toBinaryString(parser)).replace(' ', '0');
		}
		return hexValue;
	}
	
	public static int getOperend(String line){
		String temp[] = line.split(" ");
		return Integer.parseInt(temp[2]);
		
		
	}
	
	public static String getOperendForHex(String line){
		String temp[] = line.split(" ");
		return temp[2];
		
		
	}
	
	
	//---------------Checkers---------------------------------
	
	//---------File Read-------------------------
	
		public static LinkedList<String> fileReadPseudo(String filePath){
			BufferedReader br = null;
			LinkedList<String> parser = new LinkedList<String>();
			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(filePath));

				while ((sCurrentLine = br.readLine()) != null) {
					
					parser.add(sCurrentLine);
					
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
			return parser;

		}
		
		
		public static HashMap<String, String> fileReadMRIs(String filePath){
			BufferedReader br = null;
			HashMap<String, String> parser = new HashMap<String,String>();

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(filePath));

				while ((sCurrentLine = br.readLine()) != null) {
					String temp[] = sCurrentLine.split(" ");
					parser.put(temp[0], temp[1]);
					
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
			return parser;

		}
		
		
		//------File Read End------------

		//------------------Second Pass Worker----------------------------
		
		public void fileReadSecondPass(String filePath){
			BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(filePath));

				while ((sCurrentLine = br.readLine()) != null) {
					//-------Pseudo-----------------
					if(isPseudo(sCurrentLine, pseudoInstruction)){
						if(sCurrentLine.contains("ORG")){
							this.LC = Integer.parseInt(sCurrentLine.replaceAll("[\\D]", ""));
							continue;
						}else if(sCurrentLine.contains("END")){
							break;
						}else{
							if(sCurrentLine.contains("DEC")){
								 String decValue = convertDEC(sCurrentLine);
								 printable.add(decValue);
							}else if(sCurrentLine.contains("HEX")){
								String hexValue = convertHEX(sCurrentLine);
								printable.add(hexValue);
							}
						}
						this.LC++;
					}
					//-----------------MRI's or Non MRIs-----------------------
					else{
						if(isMRI(sCurrentLine, MRI)){
							String temp[] = sCurrentLine.split(" ");
							String construct ="";
							String opCode = this.MRI.get(temp[0]);
							String address = this.addressSymbolSP.get(temp[1]);
							
							if(isIndirect(sCurrentLine)){
								construct = 1+opCode+address;
							}else{
								construct = 0+opCode+address;
							}
							this.printable.add(construct);
							
							
							
						}else if(isNonMRI(sCurrentLine, nonMRI)){
							
							String code = this.nonMRI.get(sCurrentLine);
							
							this.printable.add(code);
							
						}else{
							System.out.println("Error in Code");
							
						}
						this.LC++;
					}
					
					
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

		
		//-----------------Second Pass Worker------------------------------
	
	
	public void initiazizeSecondPass(){
		MRI = fileReadMRIs("F://MRI_table.txt");
		nonMRI = fileReadMRIs("F://Non_MRI_Table.txt");
		pseudoInstruction = fileReadPseudo("F://Pseudoinstruction_table.txt");
		First_pass fp = new First_pass();
		fp.fileReadFirstPass("F://test.txt");
		addressSymbolSP = fp.addressSymbol;
		System.out.println(addressSymbolSP.get("DIF"));
		fileReadSecondPass("F://test.txt");
		
			
	}
	
	
	public static void main(String[] args){
		//System.out.println(convertHEX("SUB, HEX F6F0"));
		SecondPass sp =new SecondPass();
		sp.initiazizeSecondPass();
		for(String s:sp.printable){
			System.out.println(s);
		}
	}
	
	
}
