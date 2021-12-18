package com.example.bookstorage.repository;

import com.example.bookstorage.models.Reader;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Integer> {
}
