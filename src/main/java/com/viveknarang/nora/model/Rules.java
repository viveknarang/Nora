package com.viveknarang.nora.model;

public class Rules {

	private String fileName;
	private String mapFromField;
	private String collectionName;
	private String mapToField;

	private boolean truncateEnabled = false;
	private int truncateAfter;
	private String truncateEndChars;
	private String replaceNullWith;
	private String[] replace;
	private String remove;
	private String extractFromEndTo;
	private String extractFromFrontTo;
	private boolean trim = false;
	private boolean removeAllSpaces = false;
	private boolean toLowerCase = false;
	private boolean toUpperCase = false;
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

	public boolean isTruncateEnabled() {
		return truncateEnabled;
	}

	public int getTruncateAfter() {
		return truncateAfter;
	}

	public String getTruncateEndChars() {
		return truncateEndChars;
	}

	public String getReplaceNullWith() {
		return replaceNullWith;
	}

	public String[] getReplace() {
		return replace;
	}

	public String getRemove() {
		return remove;
	}

	public String getExtractFromEndTo() {
		return extractFromEndTo;
	}

	public String getExtractFromFrontTo() {
		return extractFromFrontTo;
	}

	public boolean getTrim() {
		return trim;
	}

	public boolean isRemoveAllSpaces() {
		return removeAllSpaces;
	}

	public boolean isToLowerCase() {
		return toLowerCase;
	}

	public boolean isToUpperCase() {
		return toUpperCase;
	}

	public String[] getExtractFromTo() {
		return extractFromTo;
	}	

}
