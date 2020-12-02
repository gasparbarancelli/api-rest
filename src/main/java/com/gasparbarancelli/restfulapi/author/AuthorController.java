package com.gasparbarancelli.restfulapi.author;

import com.gasparbarancelli.restfulapi.author.dto.AuthorPersist;
import com.gasparbarancelli.restfulapi.author.dto.AuthorUpdateDto;
import com.gasparbarancelli.restfulapi.util.JsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorModelAssembler authorModelAssembler;

    private final ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, AuthorModelAssembler authorModelAssembler, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.authorModelAssembler = authorModelAssembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping("teste/{id}")
    @Operation(summary = "Metodo teste", responses = {
            @ApiResponse(description = "Operação realizada com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))),
            @ApiResponse(responseCode = "404", description = "Registro nao encontrado", content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<?> teste(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author nao encontrado");
    }

    @GetMapping
    public CollectionModel<EntityModel<Author>> all(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        var pageable = PageRequest.of(page, size);

        var authors = authorService.findAll(pageable).stream()
                .map(authorModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(authors, linkTo(methodOn(AuthorController.class).all(size, page))
                .withSelfRel().withType("GET"));
    }

    @GetMapping("{id}")
    public EntityModel<Author> one(@NonNull @PathVariable("id") Long id) {
        var author = authorService.findByIdOrElseThrow(id);
        return authorModelAssembler.toModel(author);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Author>> newAuthor(@NonNull @Valid @RequestBody AuthorPersist authorPersist) {
        var author = modelMapper.map(authorPersist, Author.class);
        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Author>> update(
            @NonNull @PathVariable Long id,
            @NonNull @Valid @RequestBody AuthorPersist authorPersist) {
        var author = authorService.findByIdOrElseThrow(id);

        modelMapper.map(authorPersist, author);

        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@NonNull @PathVariable Long id) {
        authorService.deleteByIdOrElseThrow(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<EntityModel<Author>> updateAuthor(
            @NonNull @PathVariable("id") Long id,
            @Valid @RequestBody AuthorUpdateDto authorUpdateDto) {
        Author author = authorService.findByIdOrElseThrow(id);

        author.setName(authorUpdateDto.getName().get());
        author.setEmail(authorUpdateDto.getEmail().get());
        JsonUtil.changeIfPresent(authorUpdateDto.getLinkedIn(), author::setLinkedIn);
        JsonUtil.changeIfPresent(authorUpdateDto.getFaceBook(), author::setFaceBook);
        JsonUtil.changeIfPresent(authorUpdateDto.getTwitter(), author::setTwitter);
        JsonUtil.changeIfPresent(authorUpdateDto.getSummary(), author::setSummary);

        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        return ResponseEntity.ok(entityModel);
    }

}
