package com.company.dnotz;

public class DataItems {

    String note_title;
    String note_description;

    public DataItems(String note_title, String note_description) {
        this.note_title = note_title;
        this.note_description = note_description;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    @Override
    public String toString() {
        return "DataItems{" +
                "note_title='" + note_title + '\'' +
                ", note_description='" + note_description + '\'' +
                '}';
    }
}
