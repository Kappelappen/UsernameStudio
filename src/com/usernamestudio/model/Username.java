package com.usernamestudio.model;

public class Username {

    private int id;
    private String name;
    private String style;

    private int length;
    private boolean hasNumbers;
    private boolean hasUnderscore;
    private boolean hasSymbols;

    public Username() {}

    // ID (DB id)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Username
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Style (just nu TEXT-version)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    // Options
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isHasNumbers() {
        return hasNumbers;
    }

    public void setHasNumbers(boolean hasNumbers) {
        this.hasNumbers = hasNumbers;
    }

    public boolean isHasUnderscore() {
        return hasUnderscore;
    }

    public void setHasUnderscore(boolean hasUnderscore) {
        this.hasUnderscore = hasUnderscore;
    }

    public boolean isHasSymbols() {
        return hasSymbols;
    }

    public void setHasSymbols(boolean hasSymbols) {
        this.hasSymbols = hasSymbols;
    }

    @Override
    public String toString() {
        return name;
    }
}