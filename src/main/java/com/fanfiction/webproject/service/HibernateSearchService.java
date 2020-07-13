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
    public List<ArtworkDto> fuzzySearch(String searchTerm)  {
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        List<ArtworkDto> artworksByComments = searchDistinctArtworkDtosByComments(searchTerm, fullTextEntityManager);
        List<ArtworkDto> artworksByChapters = searchDistinctArtworkDtosByChaptersContent(searchTerm, fullTextEntityManager);
        return ListUtils.union(artworksByComments, artworksByChapters).stream()
                .distinct()
                .collect(Collectors.toList());

    }

    public List<ArtworkDto> searchDistinctArtworkDtosByChaptersContent(String searchTerm, FullTextEntityManager fullTextEntityManager) {
        List<Chapter> chaptersResult = getChaptersBySearchTerm(searchTerm, fullTextEntityManager);
        return chaptersResult.stream()
                .map(chapter -> artworkService.findByChapter(chapter))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ArtworkDto> searchDistinctArtworkDtosByComments(String searchTerm, FullTextEntityManager fullTextEntityManager) {
        List<Comment> commentsResult = getCommentsBySearchTerm(searchTerm, fullTextEntityManager);
        return commentsResult.stream()
                .map(comment -> artworkService.findByComment(comment))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Comment> getCommentsBySearchTerm(String searchTerm, FullTextEntityManager fullTextEntityManager) {
        QueryBuilder commentQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Comment.class).get();
        Query commentQuery = getQuery(searchTerm, commentQueryBuilder, "content");
        javax.persistence.Query commentsPersistenceQuery = getPersistenceQuery(fullTextEntityManager, commentQuery, Comment.class);
        return (List<Comment>) commentsPersistenceQuery.getResultList();
    }

    public List<Chapter> getChaptersBySearchTerm(String searchTerm, FullTextEntityManager fullTextEntityManager) {
        QueryBuilder chapterQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Chapter.class).get();
        Query chapterQuery = getQuery(searchTerm, chapterQueryBuilder, "content");
        javax.persistence.Query chapterPersistenceQuery = getPersistenceQuery(fullTextEntityManager, chapterQuery, Chapter.class);
        return (List<Chapter>) chapterPersistenceQuery.getResultList();
    }

    public javax.persistence.Query getPersistenceQuery(FullTextEntityManager fullTextEntityManager, Query searchQuery, Class clazz) {
        return fullTextEntityManager.createFullTextQuery(searchQuery, clazz);
    }

    public Query getQuery(String searchTerm, QueryBuilder queryBuilder, String field) {
        return queryBuilder
                .keyword()
                .onField(field)
                .matching(searchTerm)
                .createQuery();
    }


}
