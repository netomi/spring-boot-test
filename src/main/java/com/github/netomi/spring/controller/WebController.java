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

import com.github.netomi.spring.configuration.BlogProperties;
import com.github.netomi.spring.model.Article;
import com.github.netomi.spring.model.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class WebController {

    private ArticleRepository repository;

    private BlogProperties properties;

    @Autowired
    public WebController(ArticleRepository repository, BlogProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    @GetMapping("/")
    public String blog(Model model) {
        model.addAttribute("title",    properties.getTitle());
        model.addAttribute("banner",   properties.getBanner());

        Collection<Article.RenderedArticle> articles =
                StreamSupport.stream(repository.findAllByOrderByCreationTime().spliterator(), false)
                             .map(Article::render)
                             .collect(Collectors.toList());

        model.addAttribute("articles", articles);

        return "blog";
    }

    @GetMapping("/article/{slug}")
    public String article(@PathVariable String slug, Model model) {
        Article article = repository.findBySlug(slug);

        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "article with slug '" + slug + "' does not exist");
        }

        // render
        Article.RenderedArticle renderedArticle = article.render();

        model.addAttribute("title",   renderedArticle.getTitle());
        model.addAttribute("article", renderedArticle);
        return "article";
    }
}
