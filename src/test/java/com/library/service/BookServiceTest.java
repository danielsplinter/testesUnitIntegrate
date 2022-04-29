package com.library.service;

import com.library.api.model.entity.Book;
import com.library.api.model.repository.BookRepository;
import com.library.exception.BusisnessException;
import com.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){

        Book book = createValidBook();

        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);

        Mockito.when(service.save(book)).thenReturn(
                Book.builder().id(1l).isbn("123").author("Fulano").title("As aventuras").build()
        );

        Book savedBook = service.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("Fulano", savedBook.getAuthor());
        assertEquals("123", savedBook.getIsbn());
        assertEquals("As aventuras", savedBook.getTitle());

    }

    private Book createValidBook(){
        return Book.builder().isbn("123").author("Fulano").title("As aventuas").build();
    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn splicado")
    public void shouldNotSaveABookWithDuplicatedISBN(){
        Book book = createValidBook();

        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        Throwable exception = assertThrows(BusisnessException.class, () -> service.save(book));
        //BDDMockito.given(service.save(Mockito.any())).willThrow(BusisnessException.class)

        assertEquals("Isbn já cadastrado", exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(book);
    }


}
