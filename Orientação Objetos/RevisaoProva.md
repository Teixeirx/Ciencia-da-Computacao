# 📘 Resumo de Orientação a Objetos (Java)

## 1. Conceitos de Orientação a Objetos

A **Programação Orientada a Objetos (POO)** é um paradigma baseado na ideia de organizar o código em **objetos**, que representam entidades do mundo real.

### 🔹 Principais conceitos:

* **Classe**: modelo (molde) que define atributos e métodos.
* **Objeto**: instância de uma classe.
* **Atributos**: características (variáveis).
* **Métodos**: comportamentos (funções).

### 🔹 Pilares da POO:

* Encapsulamento
* Herança
* Polimorfismo
* Abstração

---

## 2. Métodos e Construtores

### 🔹 Métodos

São funções dentro de uma classe.

```java
public void falar() {
    System.out.println("Olá!");
}
```

* Podem ter retorno ou não (`void`)
* Podem receber parâmetros

### 🔹 Construtores

São métodos especiais usados para **inicializar objetos**.

```java
public class Pessoa {
    String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }
}
```

* Mesmo nome da classe
* Não possuem tipo de retorno
* Executados automaticamente ao criar o objeto

---

## 3. Encapsulamento

Consiste em **proteger os dados** da classe.

### 🔹 Como fazer:

* Tornar atributos `private`
* Criar métodos `get` e `set`

```java
private String nome;

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}
```

### 🔹 Benefícios:

* Segurança
* Controle de acesso
* Organização

---

## 4. Herança

Permite que uma classe herde características de outra.

```java
class Animal {
    void fazerSom() {
        System.out.println("Som genérico");
    }
}

class Cachorro extends Animal {
    void latir() {
        System.out.println("Au Au");
    }
}
```

### 🔹 Palavras-chave:

* `extends` → herança
* `super` → acessar classe pai

### 🔹 Vantagens:

* Reutilização de código
* Organização

---

## 5. Exceções

Exceções são **erros que ocorrem durante a execução** do programa.

### 🔹 Tipos:

* **Checked** (obrigatório tratar): `IOException`
* **Unchecked** (tempo de execução): `NullPointerException`, `ArithmeticException`

### 🔹 Tratamento:

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Erro!");
} finally {
    System.out.println("Sempre executa");
}
```

### 🔹 throw e throws

* `throw`: lança uma exceção
* `throws`: declara que o método pode lançar

```java
throw new IllegalArgumentException("Erro!");
```

### 🔹 Exceções customizadas

```java
class MinhaExcecao extends Exception {
    public MinhaExcecao(String msg) {
        super(msg);
    }
}
```