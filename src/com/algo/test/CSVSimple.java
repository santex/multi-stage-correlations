package com.algo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.multistage.correlations.gui.SetEnv;

/* Simple demo of CSV parser class.
 */
public class CSVSimple {  
  public static ArrayList exec() {
    CSV parser = new CSV('\n'," ~ ");
    FileIO io = new FileIO();
    BufferedReader c;
    List list = null;
    Iterator it;
    ArrayList returnList = new ArrayList();
    
    
	try {
		
		if(!SetEnv.RAW.toString().contains("java.lang")){
			String[] syms=SetEnv.RAW.toString().split(",");
			
			for(String s:syms){
				returnList.add(s);
			}
		}
		return returnList;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return returnList;
    
  }
}
/** Parse comma-separated values (CSV), a common Windows file format.
 * Sample input: "LU",86.25,"11/4/1998","2:19PM",+4.0625
 * <p>
 * Inner logic adapted from a C++ original that was
 * Copyright (C) 1999 Lucent Technologies
 * Excerpted from 'The Practice of Programming'
 * by Brian W. Kernighan and Rob Pike.
 * <p>
 * Included by permission of the http://tpop.awl.com/ web site, 
 * which says:
 * "You may use this code for any purpose, as long as you leave 
 * the copyright notice and book citation attached." I have done so.
 * @author Brian W. Kernighan and Rob Pike (C++ original)
 * @author Ian F. Darwin (translation into Java and removal of I/O)
 * @author Ben Ballard (rewrote advQuoted to handle '""' and for readability)
 */
class CSV {  

  public static final String DEFAULT_SEP = " ~ ";

  private String special;

  /** Construct a CSV parser, with the default separator (`,'). 
 * @param string 
 * @param c */
  public CSV(char c, String string) {
    this(DEFAULT_SEP.toCharArray()[1]);
    
    this.special = string;
  }

  /** Construct a CSV parser with a given separator. 
   * @param sep The single char for the separator (not a list of
   * separator characters)
   */
  public CSV(char sep) {
    fieldSep = sep;
  }

  /** The fields in the current String */
  protected List list = new ArrayList();

  /** the separator char for this parser */
  protected char fieldSep;

  /** parse: break the input String into fields
   * @return java.util.Iterator containing each field 
   * from the original as a String, in order.
   */
  public List parse(String line)
  {
    StringBuffer sb = new StringBuffer();
    list.clear();      // recycle to initial state
    int i = 0;

    if (line.length() == 0) {
      list.add(line);
      return list;
    }

    do {
            sb.setLength(0);
            if (i < line.length() && line.charAt(i) == '"')
                i = advQuoted(line, sb, ++i);  // skip quote
            else
                i = advPlain(line, sb, i);
            list.add(sb.toString());
      i++;
    } while (i < line.length());

    return list;
  }

  /** advQuoted: quoted field; return index of next separator */
  protected int advQuoted(String s, StringBuffer sb, int i)
  {
    int j;
    int len= s.length();
        for (j=i; j<len; j++) {
            if (s.charAt(j) == '"' && j+1 < len) {
                if (s.charAt(j+1) == '"') {
                    j++; // skip escape char
                } else if (s.charAt(j+1) == fieldSep) { //next delimeter
                    j++; // skip end quotes
                    break;
                }
            } else if (s.charAt(j) == '"' && j+1 == len) { // end quotes at end of line
                break; //done
      }
      sb.append(s.charAt(j));  // regular character.
    }
    return j;
  }

  /** advPlain: unquoted field; return index of next separator */
  protected int advPlain(String s, StringBuffer sb, int i)
  {
    int j;

    j = s.indexOf(fieldSep, i); // look for separator
        if (j == -1) {                 // none found
            sb.append(s.substring(i));
            return s.length();
        } else {
            sb.append(s.substring(i, j));
            return j;
        }
    }
}

