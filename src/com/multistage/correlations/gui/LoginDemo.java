package com.multistage.correlations.gui;

import javax.swing.*;

import com.messingarround.ListDialog;


import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDemo extends JFrame{

	static String userhome = System.getProperty("user.home");
	public static String input_file=userhome+"/mscorrelation/";
	static JLabel name;
    static JFrame frame;

    static String[] names = {"en","de"};
    static String[] desc = {"english","deutsch"};
    static JButton buttonchoose;
	
	
	JButton SUBMIT;
 JLabel label1,label2;
 final JTextField  text1,text2;
private String defaultlang = "lang:en";
  
LoginDemo(){
    
	String input_file2 =userhome+"/mscorrelation/data/userDefaultraw.msc";
	
	System.out.println("cluster-folder: " + input_file);


	setTitle("Login Form");
    setLayout(null);
    label1 = new JLabel();
    label1.setText("Username:");
    text1 = new JTextField(19);
    text1.setText("hagen");
    label2 = new JLabel();
    label2.setText("Password:");
    text2 = new JPasswordField(19);
    text2.setText("pass");
    SUBMIT=new JButton("Submit");
    label1.setBounds(5,10,100,20);
    text1.setBounds(100,10,210,20);
    label2.setBounds(5,30,100,20);
    text2.setBounds(100,30,210,20);
    SUBMIT.setBounds(100,60,100,20);
    JLabel intro = new JLabel("en:");
    name = new JLabel(names[1]);
    intro.setLabelFor(name);

    //intro.setLabelFor(name);

    //Use a wacky font if it exists. If not, this falls
    //back to a font we know exists.


    //Create the button.
    buttonchoose = new JButton(defaultlang );
    buttonchoose.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedName = ListDialog.showDialog(
                                    frame,
                                    buttonchoose,
                                    "languages:",
                                    "Name Chooser",
                                    names,
                                    name.getText(),
                                    "en");
            buttonchoose.setText("lang:"+selectedName);
            SetEnv.LANGU=selectedName;
        }

    });

    //Create the panel we'll return and set up the layout.
    intro.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    name.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    buttonchoose.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    //Add the labels to the content pane.
    add(intro);
    add(Box.createVerticalStrut(5)); //extra space
    
    buttonchoose.setBounds(210,60,100,20);
   //name.setBounds(200,120,100,20);
    
    //Add a vertical spacer that also guarantees us a minimum width:
    

    //Add the button.
    
    add(buttonchoose);
    add(name);


    
    //add(lang.getComponent(0));
   add(label1);
   add(text1);
   add(label2);
   add(text2);
   add(SUBMIT);
   //add();
   setVisible(true);
   setSize(390,140);

 SUBMIT.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent ae){
    String value1=text1.getText();
    String value2=text2.getText();
    try{
 Class.forName("com.mysql.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://108.59.253.25:3306/signup", "hagen", "x123pass");
           Statement st=con.createStatement();
           
           ResultSet rs=st.executeQuery("select * from login where username='"+value1+"' and password='"+value2+"'");
           
           String uname="",pass="";
           if(rs.next()){
               uname=rs.getString("username");
               pass=rs.getString("password");
           }
 if(value1.equals("") && value2.equals("")) {
      JOptionPane.showMessageDialog(null,"Enter login name or password","Error",JOptionPane.ERROR_MESSAGE);
  }
 else if(value1.equals(uname) && value2.equals(pass)) {
	
	SetEnv.USER=uname;
	st.execute("INSERT IGNORE INTO foo (i)  VALUES (0);");
	st.close();
	con.close();
	setVisible(false);
    Main main=new Main();
    main.frame.setVisible(true);
    }
 else if (!value1.equals(uname) && !value2.equals(pass)) {
     text1.setText("warning");
     text2.setText("warning");

     String[] env  = new String[]{"file.separator",
    	 "java.class.path",
    	 "java.class.version",
    	 "java.home",
    	 "java.vendor",
    	 "java.vendor.url",
    	 "java.version",
    	 "line.separator",
    	 "os.arch",
    	 "os.name",
    	 "path.separator",
    	 "user.dir",
    	 "user.home",
    	 "user.name"};
  
     StringBuffer buf = new StringBuffer();
     for(String prop:env){
    	 buf.append("\n").append(prop).append("=").append(System.getProperty(prop).trim());
    	 
     }
    //
    st.execute("insert ignore into unauthorised (username ,password, dumper ) values('"+text1+"','"+text1+"','"+buf.toString().replaceAll("'","")+"');");
 	st.close();
 	con.close();
 	System.out.println("logging:" + buf.toString());
 }     
     /*
      * 
      * -------------------     ------------------------------
"file.separator"        File separator (e.g., "/")
"java.class.path"       Java classpath
"java.class.version"    Java class version number
"java.home"             Java installation directory
"java.vendor"           Java vendor-specific string

"java.vendor.url"       Java vendor URL
"java.version"          Java version number
"line.separator"        Line separator
"os.arch"               Operating system architecture
"os.name"               Operating system name

"path.separator"        Path separator (e.g., ":")
"user.dir"              User's current working directory
"user.home"             User home directory
"user.name"             User account name

      * SetEnv.USER=uname;
	st.execute("INSERT IGNORE INTO foo (i)  VALUES (0);");
	st.close();
	con.close();
	setVisible(false);
    Main main=new Main();
    main.frame.setVisible(true);
      * 
      * */
  
    }
    catch(Exception e){
    	e.printStackTrace();
    	
    }
   }

   

   }

);
  }

  
  protected Font getAFont() {
      //initial strings of desired fonts
      String[] desiredFonts =
          {"French Script", "FrenchScript", "Script"};

      String[] existingFamilyNames = null; //installed fonts
      String fontName = null;        //font we'll use

      //Search for all installed font families.  The first
      //call may take a while on some systems with hundreds of
      //installed fonts, so if possible execute it in idle time,
      //and certainly not in a place that delays painting of
      //the UI (for example, when bringing up a menu).
      //
      //In systems with malformed fonts, this code might cause
      //serious problems; use the latest JRE in this case. (You'll
      //see the same problems if you use Swing's HTML support or
      //anything else that searches for all fonts.)  If this call
      //causes problems for you under the latest JRE, please let
      //us know.
      GraphicsEnvironment ge =
          GraphicsEnvironment.getLocalGraphicsEnvironment();
      if (ge != null) {
          existingFamilyNames = ge.getAvailableFontFamilyNames();
      }

      //See if there's one we like.
      if ((existingFamilyNames != null) && (desiredFonts != null)) {
          int i = 0;
          while ((fontName == null) && (i < desiredFonts.length)) {

              //Look for a font whose name starts with desiredFonts[i].
              int j = 0;
              while ((fontName == null) && (j < existingFamilyNames.length)) {
                  if (existingFamilyNames[j].startsWith(desiredFonts[i])) {

                      //We've found a match.  Test whether it can display 
                      //the Latin character 'A'.  (You might test for
                      //a different character if you're using a different
                      //language.)
                      Font f = new Font(existingFamilyNames[j],
                                        Font.PLAIN, 1);
                      if (f.canDisplay('A')) {
                          fontName = existingFamilyNames[j];
                          System.out.println("Using font: " + fontName);
                      }
                  }

                  j++; //Look at next existing font name.
              }
              i++;     //Look for next desired font.
          }
      }

      //Return a valid Font.
      if (fontName != null) {
          return new Font(fontName, Font.PLAIN, 36);
      } else {
          return new Font("Serif", Font.ITALIC, 36);
      }
  }
  
  
  public static void main(String[] args) {
		 

			new LoginDemo();			  
		
		


}
}

