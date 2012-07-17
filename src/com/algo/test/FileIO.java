package com.algo.test;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.multistage.correlations.gui.SetEnv;

/**
 * Some simple file I-O primitives reimplemented in Java. All methods are
 * static, since there is no state.
 * 
 * @version $Id: FileIO.java,v 1.18 2004/05/30 01:39:27 ian Exp $
 */
public class FileIO {

	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm ss a");
	static String userhome = System.getProperty("user.home");
	
  /** Nobody should need to create an instance; all methods are static */
  FileIO() {
    // Nothing to do
  }

  /** Copy a file from one filename to another */
  public static void copyFile(String inName, String outName)
      throws FileNotFoundException, IOException {
    BufferedInputStream is = new BufferedInputStream(new FileInputStream(
        inName));
    BufferedOutputStream os = new BufferedOutputStream(
        new FileOutputStream(outName));
    copyFile(is, os, true);
  }

  /** Copy a file from an opened InputStream to opened OutputStream */
  public static void copyFile(InputStream is, OutputStream os, boolean close)
      throws IOException {
    byte[] b = new byte[BLKSIZ]; // the byte read from the file
    int i;
    while ((i = is.read(b)) != -1) {
      os.write(b, 0, i);
    }
    is.close();
    if (close)
      os.close();
  }

  /** Copy a file from an opened Reader to opened Writer */
  public static void copyFile(Reader is, Writer os, boolean close)
      throws IOException {
    int b; // the byte read from the file
    BufferedReader bis = new BufferedReader(is);
    while ((b = is.read()) != -1) {
      os.write(b);
    }
    is.close();
    if (close)
      os.close();
  }

  /** Copy a file from a filename to a PrintWriter. */
  public static void copyFile(String inName, PrintWriter pw, boolean close)
      throws FileNotFoundException, IOException {
    BufferedReader ir = new BufferedReader(new FileReader(inName));
    copyFile(ir, pw, close);
  }

  /** Open a file and read the first line from it. */
  public static String readLine(String inName) throws FileNotFoundException,
      IOException {
    BufferedReader is = new BufferedReader(new FileReader(inName));
    String line = null;
    line = is.readLine();
    is.close();
    return line;
  }

  /** The size of blocking to use */
  protected static final int BLKSIZ = 16384;

  /**
   * Copy a data file from one filename to another, alternate method. As the
   * name suggests, use my own buffer instead of letting the BufferedReader
   * allocate and use the buffer.
   */
  public void copyFileBuffered(String inName, String outName)
      throws FileNotFoundException, IOException {
    InputStream is = new FileInputStream(inName);
    OutputStream os = new FileOutputStream(outName);
    int count = 0; // the byte count
    byte[] b = new byte[BLKSIZ]; // the bytes read from the file
    while ((count = is.read(b)) != -1) {
      os.write(b, 0, count);
    }
    is.close();
    os.close();
  }

  /** Read the entire content of a Reader into a String */
  public static String readerToString(Reader is) throws IOException {
    StringBuffer sb = new StringBuffer();
    char[] b = new char[BLKSIZ];
    int n;

    // Read a block. If it gets any chars, append them.
    while ((n = is.read(b)) > 0) {
      sb.append(b, 0, n);
    }

    // Only construct the String object once, here.
    return sb.toString();
  }

  /** Read the content of a Stream into a String */
  public static String inputStreamToString(InputStream is) throws IOException {
    return readerToString(new InputStreamReader(is));
  }

  /** Write a String as the entire content of a File */
  public static void stringToFile(String text, String fileName)
      throws IOException {
    BufferedWriter os = new BufferedWriter(new FileWriter(userhome+"/mscorrelation/"+fileName));
    os.write(text);
    os.flush();
    os.close();
  }
  /** Write a String as the entire content of a File */
  public static void stringToFile(String text, File file)
      throws IOException {
	  
    BufferedWriter os = new BufferedWriter(new FileWriter(file));
    os.write(text);
    os.flush();
    os.close();
  }

  /** Open a BufferedReader from a named file. */
  public static BufferedReader openFile(String fileName) throws IOException {
	  if(fileName.contains("home") || fileName.contains("User")){
		  return new BufferedReader(new FileReader(fileName));
	  }else{
		  return new BufferedReader(new FileReader(userhome+"/mscorrelation/"+fileName));
	  }
	  
  }

public static boolean checkForConfig() throws IOException {
	
	File config = new File(userhome+"/mscorrelation/user/","userconfig");
	if(config.exists()){
		return true;
	}else{
		FileIO.createConfig();
	}
	
	return false;
}

public static void createConfig() {
	File config = new File(userhome+"/mscorrelation/user/","userconfig");
	
	
	try {
		
		if(!config.isFile())
			config.createNewFile();
		
		StringBuffer buff  = new StringBuffer();
		
		buff.append("last-access-time="+dateFormat.format(calendar.getTime())).append("\n");
		buff.append("user="+SetEnv.USER).append("\n");
		buff.append("lang=en").append("\n");
		buff.append("market="+SetEnv.MARKET).append("\n");
		buff.append("cap="+SetEnv.MARKETCAP).append("\n");
		buff.append("file="+SetEnv.FILE).append("\n");
		buff.append("mode="+SetEnv.Mode).append("\n");
		buff.append("matrix="+SetEnv.matrix).append("\n");
		
		for(Object[] o:SetEnv.names){
			buff.append("useing-dimensions-"+o[0]+"="+o[1]).append("\n");
		}
		
		int x=0;
		for(String o:SetEnv.RULES){
			String set = "used";
			if(!SetEnv.RUNOPTIONS[x]){
			set="unused";
			}
			
			buff.append("option-"+SetEnv.RULES[x]+"="+set).append("\n");
			x++;
		}
		
		stringToFile(buff.toString(),config);
		
	} catch (IOException e) {

		e.printStackTrace();
	}
}
	
	public static void modifyConfig(String string, String format) {
		
	}
	
	public static void writeSetings() {
		File config = new File(userhome+"/mscorrelation/user/","userconfig");

		
		BufferedReader in;
		try {
			in = FileIO.openFile(config.getAbsolutePath());
		
			String[] line=null;
	        String inputLine="";
			int linen=0;
			int linenn=0;
			String[] check = new String[]{"lang","cap","market","file","useing-dimensions-","option-","matrix"};
			Object[][] names=SetEnv.names;
	        DATA:
			while ((inputLine = in.readLine()) != null) {	        	
	     
			//	for(String s:check){
				if(inputLine.contains("useing-dimensions-")) {
					
					System.out.println(inputLine);
					inputLine=inputLine.replaceAll("useing-dimensions-","");
					String[] dat = inputLine.split("=");
					names[linen]=new Object[]{dat[0],dat[1].equals("true")?true:false,names[linen][2]};
					
					linen++;
				}
			
				if(inputLine.contains("option-")) {
				
				System.out.println(inputLine);
				inputLine=inputLine.replaceAll("option-","");
				String[] dat = inputLine.split("=");
				SetEnv.RUNOPTIONS[linenn]=dat[1].equals("used")?true:false;
				//names[linen]=new Object[]{dat[0],dat[1].equals("true")?true:false,names[linen][2]};
				
				linenn++;
				}
				
				
				if(inputLine.contains("cap=")) {
					
					System.out.println(inputLine);
					inputLine=inputLine.replace("cap=","");
					SetEnv.MARKETCAP=Double.parseDouble(inputLine);
				
				}
		
				if(inputLine.contains("mode=")){
					System.out.println(inputLine);
					inputLine=inputLine.replace("mode=","");
					SetEnv.Mode=Integer.parseInt(inputLine);
				}
				if(inputLine.contains("matrix=")){
					System.out.println(inputLine);
					inputLine=inputLine.replace("matrix=","");
					SetEnv.matrix=Integer.parseInt(inputLine);
				}
				 
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
