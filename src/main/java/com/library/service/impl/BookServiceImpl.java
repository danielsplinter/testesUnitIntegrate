package com.library.service.impl;

import com.library.api.model.entity.Book;
import com.library.api.model.repository.BookRepository;
import com.library.exception.BusisnessException;
import com.library.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())){
            throw new BusisnessException("Isbn já cadastrado");
        }

        return repository.save(book);
    }
}
