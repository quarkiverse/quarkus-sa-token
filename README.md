# Quarkus - Satoken
[![Build](https://github.com/quarkiverse/quarkus-dapr/workflows/Build/badge.svg?branch=main)](https://github.com/quarkiverse/quarkus-sa-token/actions?query=workflow%3ABuild)
[![License](https://img.shields.io/github/license/quarkiverse/quarkus-dapr)](http://www.apache.org/licenses/LICENSE-2.0)
[![Central](https://img.shields.io/maven-central/v/io.quarkiverse.satoken/quarkus-sa-token-parent?color=green)](https://search.maven.org/search?q=g:io.quarkiverse.satoken%20AND%20a:quarkus-satoken-parent)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## Introduction

### What is Quarkus?

Traditional Java stacks were engineered for monolithic applications with long startup times and large memory
requirements in a world where the cloud, containers, and Kubernetes did not exist. Java frameworks needed to evolve
to meet the needs of this new world.

Quarkus was created to enable Java developers to create applications for a modern, cloud-native world. Quarkus is
a Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and
standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering
developers a framework to address a wider range of distributed application architectures.

![](https://quarkus.io/assets/images/quarkus_metrics_graphic_bootmem_wide.png)

For more information about Quarkus, please go https://quarkus.io/.


### What is Sa-Token?

Sa-Token is a lightweight Java permission authentication framework, 
which mainly solves a series of permission related problems such as login authentication, 
permission authentication, SSO, OAuth2.0 and micro-service authentication

![](https://oss.dev33.cn/sa-token/art/sa-token-js4.png)

For more information about Sa-Token, please go https://sa-token.dev33.cn/.

## Examples

With Quarkus Sa-Token Extension, it's pretty easy for java developers.

There are some demo in every `integration-tests` module.

## Progress

### Main

- [x] quarkus-satoken-vertx
- [x] quarkus-satoken-resteasy
- [ ] quarkus-satoken-resteasy-reactive

### Plugin

- [x] quarkus-satoken-oauth2
- [x] quarkus-satoken-sso
- [x] quarkus-satoken-aop(in quarkus-satoken-resteasy)
- [x] quarkus-satoken-dao-redis-jackson(based on quarkus-redisson)
- [x] quarkus-satoken-context-dubbo
- [ ] quarkus-satoken-alone-redis
- [ ] quarkus-satoken-dao-redis
- [ ] quarkus-satoken-dialect-thymeleaf
- [ ] quarkus-satoken-jwt
- [ ] quarkus-satoken-quick-login

### Native

It has not been tested in native mode yet.

- [ ] quarkus-satoken-vertx
- [ ] quarkus-satoken-resteasy
- [ ] quarkus-satoken-resteasy-reactive
- [ ] quarkus-satoken-oauth2
- [ ] quarkus-satoken-sso
- [ ] quarkus-satoken-dao-redis-jackson（based on quarkus-redisson）
- [ ] quarkus-satoken-context-dubbo
- [ ] quarkus-satoken-alone-redis
- [ ] quarkus-satoken-dao-redis
- [ ] quarkus-satoken-dialect-thymeleaf
- [ ] quarkus-satoken-jwt
- [ ] quarkus-satoken-quick-login