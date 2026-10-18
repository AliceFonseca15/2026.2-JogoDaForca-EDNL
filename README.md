# 2026.2 - Jogo da Forca da Árvore Binária de Pesquisa (ABB)

Trabalho prático desenvolvido para a disciplina de **Estruturas de Dados Não Lineares (EDNL) - Período 2026.2**, ministrada pelo professor **Demóstenes Sena**.

Este projeto consiste em uma aplicação interativa via terminal que adapta o clássico **Jogo da Forca** para o contexto de uma **Árvore Binária de Pesquisa (ABB)**, combinando conceitos teóricos de busca e percurso em árvores com uma experiência gamificada e didática.

---

## 👥 Integrantes do Grupo e Divisão de Atribuições

**Grupo 1 - Jogo da Forca da ABB (50 pontos)**

| Integrante | Papel | Principais Responsabilidades |
| :--- | :--- | :--- |
| **Alice Fonseca dos Santos** | Pessoa 1 | Modificação estrutural de `No.java` (atributo `revelado`, métodos `exibir()`, `revelarTodos()`), adição de `revelarTudo()` em `ArvoreBinaria.java` e elaboração dos testes de integridade em `TesteArvore.java`. |
| **Davi de Medeiros Linhares** | Pessoa 2 | Criação da classe de controle `RegrasForca.java`, geração de números aleatórios sem duplicatas (0 a 30) com `buscar()`, processamento de palpites, controle de acertos/tentativas e validação da condição de vitória. |
| **Caio Rodrigo Galiza Salin de Melo** | Pessoa 3 | Criação da classe executável principal `JogoForca.java`, desenvolvimento do menu interativo via terminal, tratamento rigoroso de exceções e entradas inválidas, renderização visual da forca em arte ASCII, histórico de rodadas e elaboração da documentação técnica no `README.md`. |

---

## 💡 Sobre o Jogo

No **Jogo da Forca da ABB**, em vez de adivinhar letras de uma palavra secreta, o jogador deve descobrir os **valores numéricos inteiros** que compõem os nós de uma Árvore Binária de Busca.

- Os números são gerados aleatoriamente entre **0 e 30** e não possuem repetições.
- Inicialmente, todos os nós da árvore são exibidos de forma oculta como `[ ? ]`.
- Ao acertar um palpite, o nó correspondente é revelado na sua posição exata na árvore.
- Caso o jogador erre o palpite, uma parte do corpo do boneco da forca é desenhada. O jogador perde se acumular **6 erros** (enforcado).
- **A grande sacada pedagógica:** o jogador não precisa chutar no escuro! Por ser uma Árvore Binária de Busca, **todo nó à esquerda é menor que seu pai**, e **todo nó à direita é maior que seu pai**. Conforme nós são revelados, o jogador pode deduzir os intervalos numéricos dos nós ainda ocultos!

---

## 🛠️ Adaptações na Estrutura de Dados Original

O projeto partiu do código base fornecido no repositório [demostenessena/ednl_algoritmos_20262](https://github.com/demostenessena/ednl_algoritmos_20262.git). A seguir estão descritas detalhadamente todas as modificações realizadas:

### 1. Modificações na classe `No.java`
- **Atributo `boolean revelado`:** adicionado à classe para controlar a visibilidade do nó. Todo nó é instanciado com `revelado = false`.
- **Método `exibir()`:** retorna o valor do dado (`String.valueOf(this.dado)`) caso `revelado` seja `true`; caso contrário, retorna a máscara `"[ ? ]"`.
- **Método `imprimir()` e `toString()`:** adaptados para utilizar o retorno de `exibir()`.
- **Método `imprimirNo(String recuo)`:** atualizado para imprimir `this.exibir()` em vez do dado diretamente, permitindo que a árvore impressa no terminal oculte os nós não adivinhados respeitando a hierarquia visual (`+- [ ? ]`).
- **Método `revelarTodos()`:** método recursivo que percorre a subárvore e define `this.revelado = true` para todos os nós descendentes. Utilizado para desmascarar a árvore inteira quando a partida termina (vitória, derrota ou desistência).

### 2. Modificações na classe `ArvoreBinaria.java`
- **Método `revelarTudo()`:** criado para disparar a revelação em cascata a partir da raiz (`raiz.revelarTodos()`), garantindo que o chamador não precise manipular nós internos diretamente.
- A árvore manteve integralmente seus métodos fundamentais de inserção (`inserir`) e pesquisa binária (`buscar`), assegurando a preservação da propriedade de busca.

### 3. Criação da classe `RegrasForca.java`
Classe intermediária que orquestra as regras da partida utilizando a ABB:
- **`gerarArvore(int qtdNos)`:** sorteia números inteiros de 0 a 30 com a classe `java.util.Random`. Utiliza o método `this.arvore.buscar(valorSorteado)` para garantir que **não existam números duplicados** antes de invocar `this.arvore.inserir(valorSorteado)`.
- **`verificarPalpite(int palpite)`:** incrementa o contador de tentativas e busca o nó na árvore. Se encontrado e ainda não revelado, altera `noEncontrado.revelado = true` e incrementa os acertos. Retorna `true` em caso de acerto e `false` caso o número não exista na árvore.
- **`verificarVitoria()`:** compara se `acertos == quantidadeNos`, determinando a vitória do jogador.
- **`revelarTudo()` e `imprimirArvore()`:** métodos delegados para exibição e finalização.

### 4. Criação da classe principal `JogoForca.java`
Classe responsável pela interface com o usuário (CLI) e controle do fluxo de jogo:
- **Menu interativo completo:** navegação intuitiva com opções para jogo rápido, jogo personalizado, visualização de regras e créditos.
- **Visualização gráfica em ASCII:** desenho dinâmico da forca com 7 estágios visuais (da forca vazia até o enforcamento completo).
- **Tratamento de entradas e exceções:**
  - Tratamento de `NumberFormatException` para evitar travamento do terminal caso o usuário digite texto em vez de números.
  - Validação de intervalos numéricos (palpites devem estar entre 0 e 30).
  - Bloqueio de palpites repetidos (informa que o número já foi tentado sem penalizar o jogador).
  - Tratamento de entradas vazias e consumo correto de quebras de linha (`Scanner.nextLine()`).
- **Painel de status da rodada:** exibe acertos, erros restantes, lista de palpites certos e errados, total de tentativas e árvore em tempo real.
- **Estatísticas de fim de partida:** exibe total de acertos, erros, tentativas e percentual de precisão.

### 5. Criação da classe `TesteArvore.java`
- Classe de teste automatizado para validar se a inserção, busca, máscara `[ ? ]` e método `revelarTudo()` funcionam perfeitamente sem afetar a lógica original da ABB.

---

## 🎮 Operações da Aplicação e Menus

### Menu Principal
Ao iniciar a aplicação (`java JogoForca`), o usuário é apresentado ao menu principal:

```text
========================================================
              JOGO DA FORCA DA ABB - EDNL               
========================================================
  [1] Novo Jogo Rápido (7 nós | 6 erros permitidos)
  [2] Novo Jogo Personalizado (configurar nós e erros)
  [0] Sair
========================================================
```

- **[1] Novo Jogo Rápido:** inicia imediatamente uma partida com 7 nós aleatórios e tolerância de até 6 erros na forca.
- **[2] Novo Jogo Personalizado:** permite ao usuário configurar a quantidade de nós (entre 3 e 20) e o limite de erros (entre 1 e 10).
- **[0] Sair:** encerra a execução do programa com mensagem de despedida.

### Menu durante a Rodada de Jogo
Durante a partida, após a exibição da forca, da árvore e do status, o jogador tem as seguintes ações:

```text
AÇÕES DA RODADA:
  [1] Arriscar um número (0 a 30)
  [2] Desistir da partida (revelar árvore completa)
  [0] Abandonar partida e voltar ao Menu
```

- **[1] Arriscar um número:** solicita um número entre 0 e 30. Se o número estiver na árvore, ele é revelado; caso contrário, é contabilizado um erro e a forca avança.
- **[2] Desistir da partida:** confirma a desistência e revela toda a árvore na tela.
- **[0] Abandonar partida:** interrompe a partida e retorna ao menu principal.

---

## 🌳 Exemplo de Visualização no Terminal

### Árvore com nós ocultos e alguns revelados:
```text
FORCA ATUAL:
  +---+
  |   |
  O   |
 /|   |
      |
      |
=========

ESTRUTURA ATUAL DA ÁRVORE (ABB):
+- 25
 +- [ ? ]
  +- 8
 +- [ ? ]
  +- 42

PAINEL DO JOGADOR:
- Acertos: 3 de 5 nós revelados
- Erros: 3 de 6 (Chances restantes: 3)
- Total de tentativas realizadas: 6
- Palpites corretos: [25, 8, 42]
- Palpites incorretos: [10, 50, 3]
```

Neste exemplo:
- O nó `[ ? ]` filho esquerdo de `25` e pai de `8` certamente contém um valor **entre 8 e 25**!
- O nó `[ ? ]` filho direito de `25` e pai de `42` certamente contém um valor **entre 25 e 42**!

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
- **Java Development Kit (JDK) 8 ou superior** instalado e configurado no PATH do sistema.

### Passo a passo pelo terminal:

1. Acesse o diretório do projeto:
```bash
cd 2026.2-JogoDaForca-EDNL
```

2. Compile todos os arquivos `.java`:
```bash
javac *.java
```

3. Para executar o jogo interativo:
```bash
java JogoForca
```

4. *(Opcional)* Para executar a bateria de testes da árvore:
```bash
java TesteArvore
```

---

## 📁 Estrutura de Arquivos

```text
2026.2-JogoDaForca-EDNL/
├── No.java              # Nó da árvore adaptado com 'revelado', 'exibir' e 'revelarTodos'
├── ArvoreBinaria.java   # Árvore Binária de Pesquisa com método 'revelarTudo'
├── RegrasForca.java     # Lógica das regras da forca, geração aleatória e validações
├── JogoForca.java       # Classe executável principal com interface CLI interativa
├── TesteArvore.java     # Testes de unidade e validação das modificações na árvore
└── README.md            # Documentação completa do projeto
```

---

## 🎓 Conclusão

O projeto atende a todos os requisitos solicitados:
1. Implementação baseada em Árvore Binária de Busca;
2. Aplicação interativa em terminal com menus de navegação;
3. Tratamento robusto de erros e exceções;
4. Documentação detalhada no `README.md` das adaptações na estrutura de dados e manual de operações.
