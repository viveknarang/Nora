package com.viveknarang.nora.model;

public class Rules {

	private String fileName = null;
	private String mapFromField = null;
	private String collectionName = null;
	private String mapToField = null;
	private int mapIndex = -1;

	private String[] truncateAfter = null;
	private String[] replaceNullWith = null;
	private String[] replace = null;
	private String[] remove = null;
	private String[] extractFromEndTo = null;
	private String[] extractFromFrontTo = null;
	private String[] trim = null;
	private String[] removeAllSpaces = null;
	private String[] toLowerCase = null;
	private String[] toUpperCase = null;
	private String[] extractFromTo = null;

	public String getFileName() {
		return fileName;
	}

	public String getMapFromField() {
		return mapFromField;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public String getMapToField() {
		return mapToField;
	}

	public int getMapIndex() {
		return mapIndex;
	}

	public String[] getTruncateAfter() {
		return truncateAfter;
	}

	public String[] getReplaceNullWith() {
		return replaceNullWith;
	}

	public String[] getReplace() {
		return replace;
	}

	public String[] getRemove() {
		return remove;
	}

	public String[] getExtractFromEndTo() {
		return extractFromEndTo;
	}

	public String[] getExtractFromFrontTo() {
		return extractFromFrontTo;
	}

	public String[] getTrim() {
		return trim;
	}

	public String[] getRemoveAllSpaces() {
		return removeAllSpaces;
	}

	public String[] getToLowerCase() {
		return toLowerCase;
	}

	public String[] getToUpperCase() {
		return toUpperCase;
	}

	public String[] getExtractFromTo() {
		return extractFromTo;
	}

}