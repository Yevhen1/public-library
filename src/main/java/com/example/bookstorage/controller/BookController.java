package com.example.bookstorage.controller;

import com.example.bookstorage.models.Book;
import com.example.bookstorage.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//    http://localhost:8080/book

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/book")
    public ResponseEntity<String> createBook(@RequestBody Book book){
        try {
            Book bookEntity = bookRepository.save(new Book(book.getBookName(), book.getNumberPages()));
            return new ResponseEntity<>(bookEntity.getBookName(), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(String.valueOf(book.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/book/{id}")
    public ResponseEntity<String> getBook(@PathVariable("id") int id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            return new ResponseEntity<>(book.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") int id, @RequestBody Book requestBook){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            Book updateBook = book.get();
            updateBook.setBookName(requestBook.getBookName());
            updateBook.setNumberPages(requestBook.getNumberPages());
            return  new ResponseEntity<>(updateBook.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED.NOT_FOUND);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id){
            try {
                bookRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @DeleteMapping("/book/all")
    public ResponseEntity<HttpStatus> deleteAllBook(){
        try {
            bookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


