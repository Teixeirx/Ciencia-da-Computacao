# 🚗 Cadastro de Veículos

**Aluno:** Gabriel Maier Teixeira  
**Disciplina:** Aplicativos Móveis  
**Ano:** 2026  

---

## 📱 Sobre o Aplicativo

O **Cadastro de Veículos** é um aplicativo móvel desenvolvido com **React Native** e **Expo**, voltado para o gerenciamento de veículos. O aplicativo permite cadastrar, visualizar, editar e excluir veículos, com persistência de dados local via **AsyncStorage**.

---

## ✅ Funcionalidades

- 🏠 **Tela Principal**: Exibe o título do sistema e a lista de veículos cadastrados
- ➕ **Cadastro de Veículos**: Formulário completo com os campos:
  - **Proprietário** – Nome do dono do veículo
  - **Marca** – Ex: Toyota, Honda, Ford
  - **Modelo** – Ex: Corolla, Civic, Ka
  - **Ano** – Ano de fabricação
  - **Placa** – Placa no formato antigo ou Mercosul
  - **Cor** – Cor do veículo
  - **Tipo** – Carro, Moto, Caminhão, SUV, Van ou Ônibus
- 👁️ **Listagem**: Exibe todos os veículos em cards expansíveis
- ✏️ **Edição**: Permite editar os dados de qualquer veículo
- 🗑️ **Exclusão**: Remove um veículo com confirmação
- 💾 **Persistência**: Dados salvos localmente com AsyncStorage

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| React Native | 0.76 | Framework mobile |
| Expo | 52 | Plataforma de desenvolvimento |
| AsyncStorage | ^2.1.0 | Armazenamento de dados local |

---

## 🚀 Como Executar

### Pré-requisitos

- [Node.js](https://nodejs.org/) (versão 18 ou superior)
- [Expo Go](https://expo.dev/go) instalado no celular (Android ou iOS)
- OU um emulador Android/iOS configurado

### Passos

1. **Clone ou acesse a pasta do projeto:**
   ```bash
   cd CadastroVeiculos
   ```

2. **Instale as dependências:**
   ```bash
   npm install
   ```

3. **Inicie o servidor de desenvolvimento:**
   ```bash
   npx expo start
   ```

4. **Execute no dispositivo:**
   - **Celular físico**: Abra o app **Expo Go** e escaneie o QR Code exibido no terminal
   - **Emulador Android**: Pressione `a` no terminal
   - **Navegador Web**: Pressione `w` no terminal

---

## 📁 Estrutura de Arquivos

```
CadastroVeiculos/
├── App.js                        # Componente raiz - gerencia estado e navegação
├── components/
│   ├── FormularioCadastro.js     # Formulário de cadastro/edição de veículos
│   └── CartaoVeiculo.js          # Card expansível com dados do veículo
├── package.json                  # Dependências e configurações do projeto
├── app.json                      # Configurações do Expo
└── README.md                     # Este arquivo
```

---

## 🗃️ Estrutura de Dados

Cada veículo é armazenado com o seguinte formato:

```json
{
  "id": "1718500000000",
  "proprietario": "João da Silva",
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": "2022",
  "placa": "ABC1D23",
  "cor": "Prata",
  "tipo": "Carro",
  "dataCadastro": "16/06/2026"
}
```

---

## 🏗️ Organização do Código

O projeto segue uma estrutura **componentizada**:

- **`App.js`**: Componente principal. Contém:
  - Estado global (lista de veículos)
  - Lógica de persistência com AsyncStorage
  - Controle de navegação entre telas
  - Funções de salvar, editar e excluir

- **`components/FormularioCadastro.js`**: Formulário com:
  - 7 campos de entrada
  - Validação de dados
  - Suporte a modo edição (pré-preenchimento)

- **`components/CartaoVeiculo.js`**: Card de veículo com:
  - Visualização resumida e expandida
  - Ícones por tipo de veículo
  - Botões de editar e excluir

---

*Desenvolvido como trabalho final da disciplina de Aplicativos Móveis — 2026*
