package com.janaldous.sponsorship.repository.postcodesio;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.janaldous.sponsorship.config.PostCodesApiConfig;

@TestConfiguration
@Import(PostCodesApiConfig.class)
@ComponentScan("com.janaldous.sponsorship.repository.postcodesio")
public class PostCodesIoApiTestConfig {

}
