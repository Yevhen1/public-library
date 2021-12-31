package com.example.bookstorage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(min = 2, max = 15, message = "name must be between 2 and 15 characters")
    private String readerName;

    @OneToMany(mappedBy = "readerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
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
