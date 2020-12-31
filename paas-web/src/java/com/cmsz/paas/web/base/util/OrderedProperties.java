package com.cmsz.paas.web.base.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * OrderedProperties
 */
public class OrderedProperties extends Properties {

	private static final long serialVersionUID = -4627607243846121965L;

	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	public Enumeration<Object> keys() {
		return Collections.<Object> enumeration(keys);
	}

	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}

	public Set<Object> keySet() {
		return keys;
	}

	public Set<String> stringPropertyNames() {
		Set<String> set = new LinkedHashSet<String>();

		for (Object key : this.keys) {
			set.add((String) key);
		}

		return set;
	}
	
	@Override
	public void store(OutputStream out, String comments) throws IOException {
		store0(new BufferedWriter(new OutputStreamWriter(out, "8859_1")),
	               comments,
	               true);
	}
	
	private void store0(BufferedWriter bw, String comments, boolean escUnicode)
	        throws IOException
	    {
	        if (comments != null) {
	            writeComments(bw, comments);
	        }
	        bw.write("#" + new Date().toString());
	        bw.newLine();
	        synchronized (this) {
	            for (Enumeration e = keys(); e.hasMoreElements();) {
	                String key = (String)e.nextElement();
	                String val = (String)get(key);
	                key = saveConvert(key, true, escUnicode);
	                /* No need to escape embedded and trailing spaces for value, hence
	                 * pass false to flag.
	                 */
	                val = saveConvert(val, false, escUnicode);
	                bw.write(key + "=" + val);
	                bw.newLine();
	            }
	        }
	        bw.flush();
	    }
	
	 private static void writeComments(BufferedWriter bw, String comments)
		        throws IOException {
		        bw.write("#");
		        int len = comments.length();
		        int current = 0;
		        int last = 0;
		        char[] uu = new char[6];
		        uu[0] = '\\';
		        uu[1] = 'u';
		        while (current < len) {
		            char c = comments.charAt(current);
		            if (c > '\u00ff' || c == '\n' || c == '\r') {
		                if (last != current)
		                    bw.write(comments.substring(last, current));
		                if (c > '\u00ff') {
		                    uu[2] = toHex((c >> 12) & 0xf);
		                    uu[3] = toHex((c >>  8) & 0xf);
		                    uu[4] = toHex((c >>  4) & 0xf);
		                    uu[5] = toHex( c        & 0xf);
		                    bw.write(new String(uu));
		                } else {
		                    bw.newLine();
		                    if (c == '\r' &&
		                        current != len - 1 &&
		                        comments.charAt(current + 1) == '\n') {
		                        current++;
		                    }
		                    if (current == len - 1 ||
		                        (comments.charAt(current + 1) != '#' &&
		                        comments.charAt(current + 1) != '!'))
		                        bw.write("#");
		                }
		                last = current + 1;
		            }
		            current++;
		        }
		        if (last != current)
		            bw.write(comments.substring(last, current));
		        bw.newLine();
		    }
	 private String saveConvert(String theString,
             boolean escapeSpace,
             boolean escapeUnicode) {
			int len = theString.length();
			int bufLen = len * 2;
			if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
			}
			StringBuffer outBuffer = new StringBuffer(bufLen);
			
			for(int x=0; x<len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
			if (aChar == '\\') {
			  outBuffer.append('\\'); outBuffer.append('\\');
			  continue;
			}
			outBuffer.append(aChar);
			continue;
			}
			switch(aChar) {
			case ' ':
			  if (x == 0 || escapeSpace)
			      outBuffer.append('\\');
			  outBuffer.append(' ');
			  break;
			case '\t':outBuffer.append('\\'); outBuffer.append('t');
			        break;
			case '\n':outBuffer.append('\\'); outBuffer.append('n');
			        break;
			case '\r':outBuffer.append('\\'); outBuffer.append('r');
			        break;
			case '\f':outBuffer.append('\\'); outBuffer.append('f');
			        break;
			default:
			  if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
			      outBuffer.append('\\');
			      outBuffer.append('u');
			      outBuffer.append(toHex((aChar >> 12) & 0xF));
			      outBuffer.append(toHex((aChar >>  8) & 0xF));
			      outBuffer.append(toHex((aChar >>  4) & 0xF));
			      outBuffer.append(toHex( aChar        & 0xF));
			  } else {
			      outBuffer.append(aChar);
			  }
			}
			}
			return outBuffer.toString();
			}
	 
	 private static char toHex(int nibble) {
	        return hexDigit[(nibble & 0xF)];
	    }

	    /** A table of hex digits */
	    private static final char[] hexDigit = {
	        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	    };
}