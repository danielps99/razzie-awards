# Razzie Awards - Golden Raspberry Awards

## Backend
Rest Api que utiliza Spring Boot, Maven e java 11. Utiliza banco de dados H2 e possui testes automatizados de integração. 

### Funcionalidades

1. Carragar dados a partir de arquivo dentro do projeto com dados CSV, delimitados por ponto e virgula.
2. Com base nos dados carregados, o endpoint deve retornar:
   - Lista de produtores que tenha o mínimo de intervalo entre os prêmios.
   - Lista de produtores que tenha o máximo de intervalo entre os prêmios.

### Api Endpoint
#### Obter produtores vencedores
Por utilizar método GET HTTP, pode ser requisitado até mesmo do navegador. Dados retornados no formato Json.
- http://127.0.0.1:8080/api/winner/producers-minimum-maximum-interval

### Requisitos para executar a aplicação
- Java 11
- Apache Maven 3.6.3

## Sobre o carregamento de dados
1. O carremento dos dados é feito ao iniciar a aplicação.
2. Já existe o arquivo em /src/main/resources/movielist.csv com alguns dados.
3. Para carregar outros dados, substitua o conteúdo do arquivo /src/main/resources/movielist.csv.
4. O arquivo CSV deve ser delimitado por ponto e virgula;.
   
## Como executar aplicação
### Baixar dependências da aplicação
1. No diretorio do projeto, via comand line digite:
    - mvn install
### Executar aplicação
2. No diretorio do projeto, via comand line digite:
    - mvn spring-boot:run
### Executar teste de integração
2. No diretorio do projeto, via comand line digite:
   - mvn test
