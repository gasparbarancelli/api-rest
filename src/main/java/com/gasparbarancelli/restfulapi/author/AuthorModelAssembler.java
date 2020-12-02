package com.gasparbarancelli.restfulapi.author;

import com.gasparbarancelli.restfulapi.author.dto.AuthorPersist;
import com.gasparbarancelli.restfulapi.author.dto.AuthorUpdateDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

  @Override
  public EntityModel<Author> toModel(Author author) {
    List<Link> linkList = new ArrayList<>();

    linkList.add(linkTo(methodOn(AuthorController.class).one(author.getId())).withSelfRel().withRel("author").withType("GET"));
    if (author.getId() > 100L) {
      linkList.add(linkTo(methodOn(AuthorController.class).updateAuthor(author.getId(), new AuthorUpdateDto())).withRel("author").withType("PUT"));
      linkList.add(linkTo(methodOn(AuthorController.class).update(author.getId(), new AuthorPersist())).withRel("author").withType("PATCH"));
    }
    linkList.add(linkTo(methodOn(AuthorController.class).all(null, null)).withRel("authors").withType("GET"));
    return EntityModel.of(author, linkList);
  }
}
