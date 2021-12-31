package com.example.bookstorage.controller;

import com.example.bookstorage.models.Book;
import com.example.bookstorage.models.Bookmark;
import com.example.bookstorage.models.Reader;
import com.example.bookstorage.repository.BookRepository;
import com.example.bookstorage.repository.BookmarkRepository;
import com.example.bookstorage.repository.ReaderRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ReaderController {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookRepository bookRepository;

    //    http://localhost:8080/reader/
    @PostMapping("/reader")
    public ResponseEntity<?> createReader(@Valid @RequestBody Reader reader){
        try {
            for (Reader readerCount : readerRepository.findAll()){
                if (readerCount.getReaderName().equals(reader.getReaderName())){
                    return new ResponseEntity<>(readerCount, HttpStatus.OK);
                }
            }
            Reader readerCreate = readerRepository.save(new Reader(reader.getReaderName()));
            return new ResponseEntity<>(readerCreate, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reader/{id}")
    public ResponseEntity<?> getReader(@PathVariable("id") int id){
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()){
            return new ResponseEntity<>(reader, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/reader/{id}")
    public ResponseEntity<HttpStatus> updateReader(@PathVariable("id") int id, @RequestBody Reader requestReader){
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()){
            Reader updateReader = reader.get();
            updateReader.setReaderName(requestReader.getReaderName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/reader/{id}")
    public ResponseEntity<HttpStatus> deleteReader(@PathVariable("id") int id){
        try {
            readerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addBook/{readerId}")
    public ResponseEntity<HttpStatus> addBook(@PathVariable("readerId") int readerId, @RequestBody Book requestBook){
        try {
            Book bookCreate = new Book(requestBook.getBookName(), requestBook.getNumberPages());
            Bookmark bookmarkCreate = new Bookmark();

            bookCreate.setBookmark(bookmarkCreate);
            bookmarkCreate.setBookId(bookCreate);

            Optional<Reader> reader = readerRepository.findById(readerId);

            if (reader.isPresent()){
                Reader updateReader = reader.get();
                updateReader.getBookmarkReaderList().add(bookmarkCreate);
                bookmarkCreate.setReaderId(updateReader);
                bookmarkRepository.save(bookmarkCreate);
                bookRepository.save(bookCreate);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/setBookMark/{readerId}/{bookName}/{bookmarkPage}")
    public ResponseEntity<HttpStatus> setBookMark(@PathVariable("readerId") int readerId,
                                                  @PathVariable("bookName")String bookName,
                                                  @PathVariable("bookmarkPage") int bookmarkPage){
        try {
            Optional<Reader> reader = readerRepository.findById(readerId);
            if (reader.isPresent()) {
                for (Bookmark bookmark : reader.get().getBookmarkReaderList()) {
                    if (bookmark.getBookId().getBookName().equals(bookName) &&
                            isValidNumberPages(bookmark, bookmarkPage)) {
                        bookmark.setBookmarkPage(bookmarkPage);
                        bookmarkRepository.save(bookmark);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ValidationException validationException){
            return new ResponseEntity<>(HttpStatus.valueOf("NOT VALID BOOKMARK"));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private boolean isValidNumberPages(Bookmark bookmark, int bookmarkPage) throws ValidationException{
        if (bookmark.getBookId().getNumberPages() >= bookmarkPage){
            return true;
        }
        throw  new ValidationException();
    }


    @GetMapping("/getAllBooks/{readerId}")
    public ResponseEntity<String> getAllBook(@PathVariable("readerId") int readerId){
        String resultBook = "";
        Optional<Reader> reader = readerRepository.findById(readerId);
        if (reader.isPresent()){
            for (Bookmark bookmark : reader.get().getBookmarkReaderList()){
                resultBook += bookmark.getBookId().toString() + " ";
            }
            return new ResponseEntity<>(resultBook, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getBook/{readerId}/{bookName}")
    public ResponseEntity<String> getBookByName(@PathVariable("readerId") int readerId,
                                                      @PathVariable("bookName") String bookName){
        Optional<Reader> reader = readerRepository.findById(readerId);
        if (reader.isPresent()){
            for (Bookmark bookmark : reader.get().getBookmarkReaderList()){
                if (bookmark.getBookId().getBookName().equals(bookName)){
                    return new ResponseEntity<>(bookmark.toString(), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getAllBookmark/{readerId}")
    public ResponseEntity<List<String>> getAllBookmark(@PathVariable("readerId") int readerId){
        List<String> result = new ArrayList<>();
        Optional<Reader> reader = readerRepository.findById(readerId);
        if (reader.isPresent()){
            for (Bookmark bookmark : reader.get().getBookmarkReaderList()){
                result.add(bookmark.toString());
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
