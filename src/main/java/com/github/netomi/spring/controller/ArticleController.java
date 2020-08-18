/*
 *  Copyright (c) 2020 Thomas Neidhart.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.netomi.spring.controller;

import com.github.netomi.spring.model.Article;
import com.github.netomi.spring.model.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private ArticleRepository repository;

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
	public Iterable<Article> findAll() {
        return repository.findAllByOrderByCreationTime();
    }

    @GetMapping("/{slug}")
	public Article findOne(@PathVariable String slug) {
        Article article = repository.findBySlug(slug);
        if (article == null) {
            logger.error("trying to retrieve non-existing article: " + slug);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "article with slug '" + slug + "' not found");
        } else {
            return article;
        }
    }
}
