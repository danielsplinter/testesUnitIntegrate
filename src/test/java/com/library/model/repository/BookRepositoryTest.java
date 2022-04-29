package com.library.model.repository;

import com.library.api.model.entity.Book;
import com.library.api.model.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;


    @Test
    @DisplayName("Deve retornar verdadeiro quuando existir um livro na base com o isbn informado")
    public void returnTrueWhenIsbnExists(){
        String isbn = "123";
        Book book = Book.builder().author("Fulano").title("Aventuras").isbn(isbn).build();

        entityManager.persist(book);

        boolean exists = repository.existsByIsbn(isbn);

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Deve retorar false quando não existir um livro na base com o isbn informado")
    public void returnFalseWhenIsbnDoesNotExists(){
        String isbn = "123";

        boolean exists = repository.existsByIsbn(isbn);

        Assertions.assertFalse(exists);
    }
}
