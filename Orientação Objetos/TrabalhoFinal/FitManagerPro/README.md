# FitManager Pro 🏋
**Sistema de Gerenciamento de Academia — Trabalho Final de POO**

| Campo | Info |
|---|---|
| **Aluno** | Gabriel Teixeira |
| **Disciplina** | Programação Orientada a Objetos |
| **Professor** | Rafael Bisogno |
| **Curso** | Sistemas de Informação |
| **Ano** | 2026 |

---

## Descrição do Sistema

O **FitManager Pro** é uma aplicação desktop desenvolvida em Java, com interface textual interativa (console), voltada para o gerenciamento completo de academias de pequeno e médio porte.

O sistema abrange:
- Cadastro e controle de **alunos** e **instrutores**
- Gerenciamento de **planos de treinamento** (Básico, Premium, VIP)
- Registro de **matrículas** com associação de instrutor
- Controle de **pagamentos** e inadimplência
- **Relatórios gerenciais** (total de alunos, receita estimada, matrículas em atraso)

---

## Como Compilar e Executar

### Pré-requisito
- Java JDK 17+ instalado

### Compilar
```bash
# A partir da pasta FitManagerPro/
javac -encoding UTF-8 -d out src/fitmanagerpro/interfaces/*.java src/fitmanagerpro/exceptions/*.java src/fitmanagerpro/models/*.java src/fitmanagerpro/services/*.java src/fitmanagerpro/Main.java
```

### Executar (Windows PowerShell / CMD)
```bash
java -Dfile.encoding=UTF-8 -cp out fitmanagerpro.Main
```

---

## Estrutura do Projeto

```
FitManagerPro/
├── src/
│   └── fitmanagerpro/
│       ├── Main.java
│       ├── exceptions/
│       │   ├── PlanoInvalidoException.java
│       │   └── MatriculaInvalidaException.java
│       ├── interfaces/
│       │   └── Relatorio.java
│       ├── models/
│       │   ├── Pessoa.java
│       │   ├── Aluno.java
│       │   ├── Instrutor.java
│       │   ├── Plano.java
│       │   ├── Matricula.java
│       │   └── Pagamento.java
│       └── services/
│           ├── AcademiaService.java
│           └── MenuService.java
└── README.md
```

---

## Mapeamento dos 7 Requisitos Obrigatórios de POO

### 1. Classes, Objetos e Atributos
**Onde:** pacote `models` — classes `Pessoa`, `Aluno`, `Instrutor`, `Plano`, `Matricula` e `Pagamento`.

Cada classe modela uma entidade real do domínio de academia, com atributos de tipos adequados (String, int, double, boolean, LocalDate). No `Main.java`, múltiplos objetos são instanciados diretamente no fluxo de execução para demonstração.

---

### 2. Construtores (com Sobrecarga)
**Onde:** classes `Aluno` e `Plano` demonstram sobrecarga de construtores.

- `Aluno` possui **construtor padrão** (sem parâmetros, com valores defaults) e **construtor parametrizado** completo. No `Main.java`, o `aluno3` é criado com o construtor padrão e configurado via setters.
- `Plano` possui **três construtores**: padrão (cria plano Básico), por tipo (`new Plano("Premium")`) e completo (nome, descrição, preço, duração).

---

### 3. Encapsulamento e Modificadores de Acesso
**Onde:** todos os atributos em todas as classes do pacote `models` são declarados como `private`.

O acesso é feito exclusivamente por meio de **getters e setters**, alguns com validação de negócio (ex.: `setIdade()` valida o intervalo 0–120; `setPreco()` rejeita valores negativos lançando `PlanoInvalidoException`).

---

### 4. Herança
**Onde:** pacote `models` — hierarquia `Pessoa → Aluno` e `Pessoa → Instrutor`.

- `Pessoa` é a superclasse abstrata com atributos comuns (nome, CPF, telefone, idade) e construtores padrão/parametrizado.
- `Aluno extends Pessoa` — adiciona `id`, `objetivo` e `ativo`. Chama `super(...)` no construtor.
- `Instrutor extends Pessoa` — adiciona `id`, `especialidade` e `cref`. Também chama `super(...)`.

Ambas as subclasses **reutilizam** a lógica e os atributos da superclasse, caracterizando o relacionamento "É UM".

---

### 5. Polimorfismo
**Onde:** `AcademiaService.java` — `ArrayList<Pessoa> pessoas` e método `exibirTodasAsPessoas()`.

- **Polimorfismo de inclusão:** `ArrayList<Pessoa>` armazena tanto `Aluno` quanto `Instrutor`.
- **Sobrescrita (@Override):** ambas as subclasses sobrescrevem o método abstrato `exibirInfo()` com comportamentos distintos.
- **Despacho dinâmico:** ao chamar `p.exibirInfo()` sobre a lista, Java decide em tempo de execução qual implementação usar (Aluno ou Instrutor).

---

### 6. Classes Abstratas e Interfaces
**Onde:**
- `Pessoa.java` — **classe abstrata** com o método abstrato `exibirInfo()`. Não pode ser instanciada diretamente.
- `Relatorio.java` (pacote `interfaces`) — **interface** com os métodos `gerarRelatorioGeral()` e `gerarRelatorioItem(String)`.
- `AcademiaService implements Relatorio` — cumpre o contrato da interface implementando ambos os métodos.

---

### 7. Tratamento de Exceções
**Onde:** pacote `exceptions` + `MenuService.java` + `models/`.

**Exceções customizadas criadas:**
- `PlanoInvalidoException` — lançada quando um tipo de plano inválido é informado ou o preço é negativo.
- `MatriculaInvalidaException` — lançada quando um aluno já possui matrícula ativa no mesmo plano.

**Uso de try-catch-finally:**
- `MenuService.lerInteiro()` usa `try-catch-finally` (o `finally` garante que o buffer do Scanner seja sempre limpo).
- Entradas de dados (nome, CPF, idade, valor) são protegidas com `try-catch (InputMismatchException)`.
- Criação de planos e matrículas trata as exceções customizadas.
