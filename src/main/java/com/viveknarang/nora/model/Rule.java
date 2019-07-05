package com.viveknarang.nora.model;

import java.util.List;

/**
 * @author Vivek Narang
 */
public class Rule {

    private String fileName = null;
    private String mapFromField = null;
    private int fromFieldIndex = 0;
    private String overWrite = null;
    private String mapToField = null;

    private List<List<String>> transform = null;

    public String getFileName() {
        return fileName;
    }

    public String getMapFromField() {
        return mapFromField;
    }

    public int getFromFieldIndex() { return fromFieldIndex; }

    public String getMapToField() {
        return mapToField;
    }

    public String getOverwrite() { return overWrite; }

    public List<List<String>> getTransform() {
        return transform;
    }

}