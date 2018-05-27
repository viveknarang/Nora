package com.viveknarang.nora.model;

import java.util.List;

public class Rules {

	private String fileName = null;
	private String mapFromField = null;
	private String collectionName = null;
	private String mapToField = null;
	private int mapIndex = -1;
	private List<List<String>> transform = null;

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

	public List<List<String>> getTransform() {
		return transform;
	}

}