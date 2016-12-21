package com.luminos;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import com.luminos.tools.instanceinfo.GLFWInstance;
import com.luminos.tools.instanceinfo.JavaEnvironmentInstance;
import com.luminos.tools.instanceinfo.OpenGLInstance;

/**
 * 
 * Custom Luminos Debugger
 * 
 * @author Nick Clark
 * @version 1.1
 *
 */

public class Debug {
	
	public static boolean DEBUG = true;
	public static boolean PRINT_TO_FILE = true;
	public static int FRAMES = 1000;
	public static boolean BENCHMARK = false;
		
	private static StringBuilder debug_data = new StringBuilder();
	private static StringBuilder header = new StringBuilder();
	
	public static void prepare() {
		header.append(JavaEnvironmentInstance.getEnvironmentData());
		header.append(OpenGLInstance.getContextInformation());
		header.append(GLFWInstance.getContextInformation());
	}
	
	/**
	 * Append string to debug buffer
	 * 
	 * @param e		Throwable error
	 */
	public static void addData(Throwable e) {
		
		if(DEBUG) {
			appendNewLine(debug_data);
			debug_data.append(e.getMessage());
			StackTraceElement[] elements = e.getStackTrace();
			for(StackTraceElement element : elements) {
				appendNewLine(debug_data);
				debug_data.append(element.getFileName() + " " + element.getClassName() + " " + element.getLineNumber());
			}
		}
		
	}
	
	/**
	 * Prints to console
	 */
	public static void out() {
		System.out.println(debug_data.toString());
		System.exit(-1);
	}
	
	/**
	 * Prints to file
	 */
	public static void print() {
		if(debug_data.toString() != null) {
			try {
				FileWriter fw = new FileWriter("DEBUG" + ManagementFactory.getRuntimeMXBean().getName() + ".lof", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.write(header.toString());
				pw.write(debug_data.toString());
				pw.flush();
				pw.close();
				bw.close();
				fw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
//***********************************Private Methods*******************************************//
	
	/**
	 * Appends new line to string builder
	 * 
	 * @param sb		String builder to append to
	 */
	private static void appendNewLine(StringBuilder sb) {
		sb.append(System.lineSeparator());
	}
	

}