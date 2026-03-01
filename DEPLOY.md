# 🚀 Guia de Deploy

## ⚠️ Problema com Vercel

**Vercel NÃO suporta aplicações Java!** 

Vercel é uma plataforma focada em:
- Node.js
- Python
- Go
- Ruby
- PHP

Sua aplicação é **Java + Spring Boot**, então não pode rodar lá.

## ✅ Soluções Recomendadas

### Opção 1: Railway (RECOMENDADO) ⭐
Railway suporta Java nativamente e é fácil de usar.

**Passos:**
1. Acesse https://railway.app
2. Crie uma conta e faça login
3. Clique em "New Project" → "Deploy from GitHub"
4. Selecione seu repositório
5. Railway detectará automaticamente que é uma aplicação Java/Maven
6. Configure variáveis de ambiente no dashboard
7. Deploy automático feito!

### Opção 2: Render
Similar ao Railway, suporta Java e PostgreSQL.

**Passos:**
1. Acesse https://render.com
2. Crie uma conta
3. Clique em "New +" → "Web Service"
4. Conecte seu GitHub
5. Configure como "Docker" (usará o Dockerfile)
6. Deploy automático!

### Opção 3: AWS, Google Cloud, Azure
Plataformas tradicionais que suportam com mais controle.

## 🐳 Usar Docker Localmente

```bash
# Build da imagem
docker build -t api-agendamento .

# Rodar container
docker run -p 8080:8080 api-agendamento
```

## 📝 Variáveis de Ambiente (production.yml)

```yaml
SPRING_DATASOURCE_URL=jdbc:postgresql://seu-host:5432/clinica_db
SPRING_DATASOURCE_USERNAME=seu-usuario
SPRING_DATASOURCE_PASSWORD=sua-senha
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQL10Dialect
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SERVER_PORT=8080
```

## 🔧 Próximas Melhorias

1. Adicionar JWT para autenticação
2. Implementar rate limiting
3. Adicionar logging centralizado
4. Criar CI/CD automático com GitHub Actions
5. Adicionar testes unitários
