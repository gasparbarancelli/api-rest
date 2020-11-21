package com.gasparbarancelli.restfulapi.author;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService, CommandLineRunner {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findByIdOrElseThrow(@NonNull Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public Author save(@NonNull Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Page<Author> findAll(@NonNull PageRequest pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public void deleteByIdOrElseThrow(@NonNull Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
        throw new AuthorNotFoundException(id);
    }

    @Override
    public void run(String... args) throws Exception {
        var gaspar = Author.builder("Gaspar", "gaspar@gaspar.com")
                .faceBook("facebook/gaspar")
                .linkedIn("linkedin/gaspar")
                .twitter("twitter/gaspar")
                .summary("summary")
                .build();

        var junior = Author.builder("Junior", "junior@junior.com")
                .faceBook("facebook/junior")
                .summary("summary")
                .build();
        var authorList = List.of(gaspar, junior);
        authorRepository.saveAll(authorList);
    }
}
