package com.viveknarang.nora.model;

import java.util.Arrays;

public class Rules {

	private String fileName = null;
	private String mapFromField = null;
	private String collectionName = null;
	private String mapToField = null;
	private int mapIndex = -1;

	private boolean truncateEnabled = false;
	private int truncateAfter = -1;
	private String truncateEndChars = null;
	private String replaceNullWith = null;
	private String[] replace = null;
	private String remove = null;
	private String extractFromEndTo = null;
	private String extractFromFrontTo = null;
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

	public int getMapIndex() {
		return mapIndex;
	}

	@Override
	public String toString() {
		return "Rules [fileName=" + fileName + ", mapFromField=" + mapFromField + ", collectionName=" + collectionName
				+ ", mapToField=" + mapToField + ", mapIndex=" + mapIndex + ", truncateEnabled=" + truncateEnabled
				+ ", truncateAfter=" + truncateAfter + ", truncateEndChars=" + truncateEndChars + ", replaceNullWith="
				+ replaceNullWith + ", replace=" + Arrays.toString(replace) + ", remove=" + remove
				+ ", extractFromEndTo=" + extractFromEndTo + ", extractFromFrontTo=" + extractFromFrontTo + ", trim="
				+ trim + ", removeAllSpaces=" + removeAllSpaces + ", toLowerCase=" + toLowerCase + ", toUpperCase="
				+ toUpperCase + ", extractFromTo=" + Arrays.toString(extractFromTo) + "]";
	}
	

}