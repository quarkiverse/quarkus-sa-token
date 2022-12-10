# Quarkus - Satoken
[![Build](https://github.com/quarkiverse/quarkus-sa-token/workflows/Build/badge.svg?branch=main)](https://github.com/quarkiverse/quarkus-sa-token/actions?query=workflow%3ABuild)
[![License](https://img.shields.io/github/license/quarkiverse/quarkus-sa-token)](http://www.apache.org/licenses/LICENSE-2.0)
[![Central](https://img.shields.io/maven-central/v/io.quarkiverse.satoken/quarkus-sa-token-parent?color=green)](https://search.maven.org/search?q=g:io.quarkiverse.satoken%20AND%20a:quarkus-sa-token-parent)
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

- [x] quarkus-sa-token-resteasy
- [ ] quarkus-sa-token-resteasy-reactive

### Plugin

- [x] quarkus-sa-token-oauth2
- [x] quarkus-sa-token-sso
- [x] quarkus-sa-token-aop(in quarkus-sa-token-resteasy)
- [x] quarkus-sa-token-dao-redis-jackson(based on quarkus-redisson)
- [x] quarkus-sa-token-alone-redis(based on quarkus-redisson)
- [x] quarkus-sa-token-context-dubbo
- [ ] quarkus-sa-token-dao-redis
- [ ] quarkus-sa-token-dialect-thymeleaf
- [ ] quarkus-sa-token-jwt
- [ ] quarkus-sa-token-quick-login

### Native

It has not been tested in native mode yet.

- [ ] quarkus-sa-token-resteasy
- [ ] quarkus-sa-token-resteasy-reactive
- [ ] quarkus-sa-token-oauth2
- [ ] quarkus-sa-token-sso
- [ ] quarkus-sa-token-dao-redis-jackson（based on quarkus-redisson）
- [ ] quarkus-sa-token-context-dubbo
- [ ] quarkus-sa-token-alone-redis
- [ ] quarkus-sa-token-dao-redis
- [ ] quarkus-sa-token-dialect-thymeleaf
- [ ] quarkus-sa-token-jwt
- [ ] quarkus-sa-token-quick-login

### migration
Maven artifacts was changed from `quarkus-satoken-*` to `quarkus-sa-token-*` in  version `1.31.1` 


## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://www.naah69.com"><img src="https://avatars.githubusercontent.com/u/25682169?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Naah</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-sa-token/commits?author=naah69" title="Code">💻</a> <a href="#maintenance-naah69" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
