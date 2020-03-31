package com.cloudStorage.service.connection;

public class FileForTable {
    public String nameFileTable;
    public String sizeFileTable;
    public String dateCreatFileTable;

    public FileForTable(String nameFileTable, String sizeFileTable, String dateCreatFileTable) {
        this.nameFileTable = nameFileTable;
        this.sizeFileTable = sizeFileTable;
        this.dateCreatFileTable = dateCreatFileTable;
    }

    public FileForTable() {
    }

    public String getNameFileTable() {
        return nameFileTable;
    }

    public void setNameFileTable(String nameFileTable) {
        this.nameFileTable = nameFileTable;
    }

    public String getSizeFileTable() {
        return sizeFileTable;
    }

    public void setSizeFileTable(String sizeFileTable) {
        this.sizeFileTable = sizeFileTable;
    }

    public String getDateCreatFileTable() {
        return dateCreatFileTable;
    }

    public void setDateCreatFileTable(String dateCreatFileTable) {
        this.dateCreatFileTable = dateCreatFileTable;
    }

    @Override
    public String toString() {
        return nameFileTable+" "+ sizeFileTable+ " "+ dateCreatFileTable+"<END>";
    }
}
