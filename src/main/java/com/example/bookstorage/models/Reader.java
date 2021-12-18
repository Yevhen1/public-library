package com.example.bookstorage.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String readerName;

    @OneToMany(mappedBy = "readerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkReaderList = new ArrayList<>();

    public Reader(){
    }

    public Reader(String readerName){
        this.readerName = readerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public List<Bookmark> getBookmarkReaderList() {
        return bookmarkReaderList;
    }

    public void setBookmarkReaderList(List<Bookmark> bookmarkReaderList) {
        this.bookmarkReaderList = bookmarkReaderList;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder(id + readerName);
        for (Bookmark bookmark : bookmarkReaderList){
            result.append(bookmark.getBookId().getBookName());
        }
        return result.toString();
    }
}
