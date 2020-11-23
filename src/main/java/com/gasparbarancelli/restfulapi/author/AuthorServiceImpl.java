package com.gasparbarancelli.restfulapi.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

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
        } else {
            throw new AuthorNotFoundException(id);
        }
    }

}
