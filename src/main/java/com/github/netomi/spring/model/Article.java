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
package com.github.netomi.spring.model;

import com.github.netomi.spring.util.Extensions;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String headline;

    @NonNull
    private String content;

    @ManyToOne
    private User author;

    @NonNull
    private String slug;

    @NonNull
    private LocalDateTime creationTime;

    public Article() {}

    public Article(String title, String headline, String content, User author) {
        this.title    = title;
        this.headline = headline;
        this.content  = content;
        this.author   = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(@NonNull String headline) {
        this.headline = headline;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

    @NonNull
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(@NonNull LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public RenderedArticle render() {
        return new RenderedArticle(slug, title, headline, content, author, Extensions.format(creationTime));
    }

    // helper methods.

    @PrePersist
    void preInsert() {
        if (slug == null) {
            slug = Extensions.toSlug(this.title);
        }

        if (creationTime == null) {
            creationTime = LocalDateTime.now();
        }
    }

    public static class RenderedArticle {
        private String slug;
        private String title;
        private String headline;
        private String content;
        private User   author;
        private String creationTime;

        public RenderedArticle(String slug, String title, String headline, String content, User author, String creationTime) {
            this.slug = slug;
            this.title = title;
            this.headline = headline;
            this.content = content;
            this.author = author;
            this.creationTime = creationTime;
        }

        public String getSlug() {
            return slug;
        }

        public String getTitle() {
            return title;
        }

        public String getHeadline() {
            return headline;
        }

        public String getContent() {
            return content;
        }

        public User getAuthor() {
            return author;
        }

        public String getCreationTime() {
            return creationTime;
        }
    }
}
