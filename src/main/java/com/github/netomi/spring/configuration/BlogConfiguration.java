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
package com.github.netomi.spring.configuration;

import com.github.netomi.spring.model.Article;
import com.github.netomi.spring.model.ArticleRepository;
import com.github.netomi.spring.model.User;
import com.github.netomi.spring.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BlogConfiguration.class);

    @Bean
    public ApplicationRunner databaseInitializer(UserRepository userRepository, ArticleRepository articleRepository) {
        return args -> {
            logger.info("initializing database...");

            User bud     = userRepository.save(new User("bud", "Bud", "Spencer"));
            User terence = userRepository.save(new User("terence", "Terence", "Hill"));

            articleRepository.save(new Article("Reactor Bismuth is out", "Lorem ipsum", "dolor sit amet", bud));
            articleRepository.save(new Article("Reactor Aluminium has landed", "Lorem ipsum", "dolor sit amet", terence));
        };
    }
}
