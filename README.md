# Documentação Rápida da API - Agenda Escolar

## Base URL
```txt
http://localhost:8080
```

## Autenticação
A API utiliza JWT Bearer Token.

Após login, enviar o token no header:

```http
Authorization: Bearer TOKEN
```

---

# AUTH

## Login
### POST `/auth/login`

Realiza autenticação do usuário.

### Request
```json
{
  "id": 1,
  "senha": "123456"
}
```

### Campos
| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| id | number | Sim | ID do professor/usuário |
| senha | string | Sim | Senha do usuário |

### Response
```json
{
  "token": "jwt-token"
}
```

---

# PROFESSORES

## Listar professores
### GET `/professor`

Retorna todos os professores.

### Response
```json
[
  {
    "id": 1,
    "name": "Carlos"
  }
]
```

---

## Criar professor
### POST `/professor`

Permissão: `ADMIN` ou `SECRETARIO`

Cria um professor comum.

### Request
```json
{
  "name": "Carlos",
  "senha": "123456",
  "permissao": "USUARIO"
}
```

### Campos
| Campo | Tipo | Obrigatório | Valores |
|---|---|---|---|
| name | string | Sim | Nome do professor |
| senha | string | Sim | Senha |
| permissao | string | Sim | `ADMIN`, `SECRETARIO`, `USUARIO` |

### Response
```json
{
  "id": 1,
  "name": "Carlos"
}
```

---

## Criar professor admin
### POST `/professor/adm`

Permissão: `ADMIN`

Cria um professor administrador.

### Request
```json
{
  "name": "Admin",
  "senha": "123456",
  "permissao": "ADMIN"
}
```

### Response
```json
{
  "id": 1,
  "name": "Admin"
}
```

---

## Atualizar professor
### PUT `/professor/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

Atualiza todos os dados do professor.

### Path Params
| Campo | Tipo |
|---|---|
| id | number |

### Request
```json
{
  "name": "Carlos",
  "senha": "novaSenha",
  "permissao": "USUARIO"
}
```

### Response
```json
"Professor atualizado com sucesso"
```

---

## Atualizar apenas nome do professor
### PATCH `/professor/{id}`

Atualiza apenas o nome.

### Request
```json
{
  "name": "Novo Nome"
}
```

### Response
```json
"Nome atualizado com sucesso"
```

---

## Deletar professor
### DELETE `/professor/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

### Response
```json
"Professor deletado com sucesso"
```

---

# MATÉRIAS

## Listar matérias
### GET `/materia`

Retorna todas as matérias.

### Response
```json
[
  {
    "id": 1,
    "materia": "Matemática"
  }
]
```

---

## Criar matéria
### POST `/materia`

Permissão: `ADMIN` ou `SECRETARIO`

### Request
```json
{
  "materia": "Matemática"
}
```

### Response
```json
{
  "id": 1,
  "materia": "Matemática"
}
```

---

## Atualizar matéria
### PUT `/materia/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

### Request
```json
{
  "materia": "Português"
}
```

### Response
```json
"Matéria atualizada com sucesso"
```

---

## Deletar matéria
### DELETE `/materia/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

### Response
```json
"Matéria deletada com sucesso"
```

---

# TURMAS

## Listar turmas
### GET `/turma`

### Response
```json
[
  {
    "id": 1,
    "periodo": "MATUTINO",
    "serie": 2,
    "turma": 1
  }
]
```

---

## Criar turma
### POST `/turma`

Permissão: `ADMIN` ou `SECRETARIO`

### Request
```json
{
  "periodo": "MATUTINO",
  "serie": 2,
  "turma": 1
}
```

### Campos
| Campo | Tipo | Obrigatório | Valores |
|---|---|---|---|
| periodo | string | Sim | `MATUTINO`, `VESPERTINO` |
| serie | number | Sim | 1 até 9 |
| turma | number | Sim | 1 até 10 |

### Response
```json
{
  "id": 1,
  "periodo": "MATUTINO",
  "serie": 2,
  "turma": 1
}
```

---

## Atualizar turma
### PUT `/turma/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

### Request
```json
{
  "periodo": "VESPERTINO",
  "serie": 3,
  "turma": 2
}
```

### Response
```json
"Turma atualizada com sucesso"
```

---

## Deletar turma
### DELETE `/turma/{id}`

Permissão: `ADMIN` ou `SECRETARIO`

### Response
```json
"Turma deletada com sucesso"
```

---

# AGENDA

## Listar agenda da sala de informática
### GET `/agenda/informatica`

Retorna todos os agendamentos da sala de informática.

### Response
```json
[
  {
    "id": 1,
    "turma": {
      "serie": 2,
      "turma": 1
    },
    "materia": {
      "materia": "Matemática"
    },
    "tipoAula": "AULA_1",
    "tipoAgenda": "SALA_INFORMATICA",
    "tipoPeriodo": "MATUTINO",
    "data": "2026-05-18",
    "professor": {
      "id": 1,
      "name": "Carlos"
    },
    "observacao": "Levar notebook"
  }
]
```

---

## Listar agenda do auditório
### GET `/agenda/auditorio`

Retorna todos os agendamentos do auditório.

### Response
Mesmo formato do endpoint anterior.

---

## Listar agenda dos tablets
### GET `/agenda/tablet`

Retorna todos os agendamentos de tablets.

### Response
Mesmo formato do endpoint anterior.

---

## Criar agendamento
### POST `/agenda`

Cria um novo agendamento.

### Request
```json
{
  "turmaId": 1,
  "materiaId": 1,
  "tipoAula": "AULA_1",
  "tipoAgenda": "SALA_INFORMATICA",
  "tipoPeriodo": "MATUTINO",
  "data": "2026-05-20",
  "observacao": "Opcional"
}
```

### Campos
| Campo | Tipo | Obrigatório | Valores |
|---|---|---|---|
| turmaId | number | Sim | ID da turma |
| materiaId | number | Sim | ID da matéria |
| tipoAula | string | Sim | `AULA_1`, `AULA_2`, `AULA_3`, `AULA_4`, `AULA_5` |
| tipoAgenda | string | Sim | `SALA_INFORMATICA`, `TABLET`, `AUDITORIO` |
| tipoPeriodo | string | Sim | `MATUTINO`, `VESPERTINO` |
| data | string | Sim | Formato `YYYY-MM-DD` |
| observacao | string | Não | Máximo 255 caracteres |

### Response
```json
"Agendamento criado com sucesso"
```

---

## Atualizar agendamento
### PUT `/agenda/{id}`

Atualiza um agendamento.

### Request
```json
{
  "turmaId": 1,
  "materiaId": 1,
  "tipoAula": "AULA_2",
  "tipoAgenda": "TABLET",
  "tipoPeriodo": "VESPERTINO",
  "data": "2026-05-21",
  "observacao": "Atualizado"
}
```

### Response
```json
"Agendamento atualizado com sucesso"
```

---

## Deletar agendamento
### DELETE `/agenda/{id}`

### Response
```json
"Agendamento deletado com sucesso"
```

---

# ENUMS IMPORTANTES

## TipoUsuario
```txt
ADMIN
SECRETARIO
USUARIO
```

## TipoPeriodo
```txt
MATUTINO
VESPERTINO
```

## TipoAgenda
```txt
SALA_INFORMATICA
TABLET
AUDITORIO
```

## TipoAula
```txt
AULA_1
AULA_2
AULA_3
AULA_4
AULA_5
```

