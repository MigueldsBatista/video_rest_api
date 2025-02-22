## Documentação seguida

Spring Authorization Server

https://docs.spring.io/spring-authorization-server/reference/core-model-components.html#_oauth2client

Componentes

Registered Client - Representa um cliente OAuth 2.0 registrado no Authorization Server.
- clientId *
- clientSecret *
- clientAuthenticationMethods
- authorizationGrantTypes
- redirectUris *
- scopes *
- clientSettings

Campos com * são obrigatórios.

Registered Client Repository - Interface para armazenar e recuperar RegisteredClient.

Registered Client Service - Interface para gerenciar RegisteredClient.