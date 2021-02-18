package com.janaldous.sponsorship.repository.tfl;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.janaldous.sponsorship.config.TflApiConfig;

@TestConfiguration
@Import(TflApiConfig.class)
@ComponentScan("com.janaldous.sponsorship.repository.tfl")
public class TflApiTestConfig {

}
