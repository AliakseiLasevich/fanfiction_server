package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import org.apache.commons.collections4.ListUtils;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HibernateSearchService {

    private EntityManager entityManager;
    private ArtworkService artworkService;

    @Autowired
    public HibernateSearchService(EntityManager entityManager, ArtworkService artworkService) {
        this.entityManager = entityManager;
        this.artworkService = artworkService;
    }

    @Transactional
    public List<ArtworkDto> fuzzySearch(String searchTerm) throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

        QueryBuilder commentQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Comment.class).get();
        QueryBuilder chapterQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Chapter.class).get();

        Query commentQuery = commentQueryBuilder
                .keyword()
                .onFields("content")
                .matching(searchTerm)
                .createQuery();

        Query chapterQuery = chapterQueryBuilder
                .keyword()
                .onFields("content")
                .matching(searchTerm)
                .createQuery();


        // wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query commentsPersistenceQuery =
                fullTextEntityManager.createFullTextQuery(commentQuery, Comment.class);

        javax.persistence.Query chapterPersistenceQuery =
                fullTextEntityManager.createFullTextQuery(commentQuery, Chapter.class);

// execute search
        List<Comment> commentsResult = commentsPersistenceQuery.getResultList();
        List<Chapter> chaptersResult = chapterPersistenceQuery.getResultList();


        List<ArtworkDto> artworksByComments = commentsResult.stream()
                .map(comment -> artworkService.findByComment(comment))
                .distinct()
                .collect(Collectors.toList());

        List<ArtworkDto> artworksByChapters = chaptersResult.stream()
                .map(chapter -> artworkService.findByChapter(chapter))
                .distinct()
                .collect(Collectors.toList());

        return ListUtils.union(artworksByComments, artworksByChapters).stream()
                .distinct()
                .collect(Collectors.toList());

    }


}
