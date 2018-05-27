package com.viveknarang.nora.model;

import java.util.Arrays;

/**
 * 
 * @author Vivek Narang
 *
 */
public class Job {

	public String name;
	public String[] files;
	public String start;

	public String getName() {
		return name;
	}

	public String[] getFiles() {
		return files;
	}

	public String getStart() {
		return start;
	}

	@Override
	public String toString() {
		return "Job [name=" + name + ", files=" + Arrays.toString(files) + ", start=" + start + "]";
	}	

}
