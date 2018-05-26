package com.viveknarang.nora.model;

public class Rules {

	private String fileName;
	private String mapFromField;
	private String collectionName;
	private String mapToField;

	private boolean truncateEnabled;
	private int truncateAfter;
	private String truncateEndChars;
	private String replaceNullWith;
	private String replace;
	private String remove;
	private String extractFromEndTo;
	private String extractFromFrontTo;
	private String trim = "true";
	private boolean removeAllSpaces;
	private boolean toLowerCase;
	private boolean toUpperCase;

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

	public String getReplace() {
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

	public String getTrim() {
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

}
