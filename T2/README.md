# Trabalho 2 - Linguagem de Programação Orientada a Objetos 2026

## Autores

- Guilherme Duarte Balduino
- Leonardo Marcon Leal
- Otávio Gabriel de Oliveira Santana

## 1. Descrição do trabalho

O programa é uma calculadora de derivadas parciais de funções de R³ em R, nas
variáveis `x`, `y` e `z`. Ela permite definir nomes associados a expressões,
avaliá-las numericamente, calcular derivadas parciais simbólicas e gerenciar a
tabela de nomes.

As expressões são representadas por uma árvore de objetos. A hierarquia parte
da classe abstrata `Expression`, que declara as operações `eval` (avaliação em
um ponto `(x, y, z)`), `diff` (derivada parcial em relação a uma variável),
`simplify` (simplificação) e `substituteX` (substituição de `x` por outra
expressão).

- `Constant` representa os valores numéricos, incluindo as constantes `pi` e `e`.
- `X`, `Y` e `Z` representam as variáveis e são singletons: cada uma possui uma
  única instância, acessada por `INSTANCE`, e construtor privado. O enum
  `Variable` identifica a variável de derivação.
- `UnaryExpression` é a base das operações unárias, implementadas por `Minus`.
- `BinaryExpression` é a base das operações binárias, implementadas por `Add`,
  `Sub`, `Mul`, `Div` e `Pow`, cada uma com sua regra de derivação e de
  simplificação.
- `Function` aplica uma função nativa (`sin`, `cos` ou `loge`) a um argumento e
  deriva pela regra da cadeia.

`Pow` aceita qualquer expressão como expoente. Expoentes constantes usam a
regra da potência, e os demais usam a derivada de `f^g`, dada por
`f^g * (g' * loge(f) + g * f'/f)`.

O pacote `lpoo.sym.parser` processa a entrada. O `Scanner` converte o texto em
tokens, e `Keywords` reconhece as palavras reservadas. O `Parser` é um
analisador descendente recursivo que segue a gramática de `grammar.txt`:
constrói a árvore da expressão e devolve o `Command` correspondente, com seus
argumentos. Cada comando é um objeto de `Command` que implementa o método
`run`. `SymbolTable` armazena os nomes definidos em ordem alfabética. `Calc`
liga o parser à execução dos comandos, e `Main` lê os comandos do console ou
de arquivos.

## 2. Atividades

- **A1 - Concluída.** As expressões foram refatoradas para as variáveis
  `(x, y, z)`: `eval` recebe os três valores, e `diff` recebe a variável de
  derivação (`X`, `Y` ou `Z`). `X`, `Y` e `Z` são singletons. `Pow` admite
  expoentes que são expressões quaisquer, e foram adicionadas a constante `e`
  e a função `loge`.
- **A2 - Concluída.** O interpretador trata todos os comandos da gramática:
  `eval`, `diff`, `define`, atribuição, `clear` (com e sem nome), `list` e
  `exit`.
- **A3 - Concluída.** Cada argumento da linha de comando é lido como um arquivo
  cujas linhas são comandos, executados em sequência, com a resposta de cada um
  exibida na saída padrão. Sem argumentos, a calculadora funciona em modo
  interativo. O arquivo `comandos-teste.txt` acompanha o trabalho.
- **A4 - Concluída.** O link para o vídeo está no final deste arquivo.

## 3. Organização do projeto

```
src/lpoo/t2/                   Classe principal Main, gramática e comandos de teste.
src/lpoo/t2/lpoo/sym/          Expressões, funções, comandos e tabela de símbolos.
src/lpoo/t2/lpoo/sym/parser/   Scanner, Parser, tokens e exceções de análise.
src/lpoo/t2/lpoo/util/         Classe Console para leitura e escrita.
out/                           Diretório gerado para arquivos compilados.
```

## 4. Compilação e execução no Linux

A partir da pasta `T2`:

```sh
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
```

Modo interativo (os comandos são digitados após o prompt `>`):

```sh
java -cp out Main
```

Execução dos comandos de um ou mais arquivos, um comando por linha:

```sh
java -cp out Main src/lpoo/t2/comandos-teste.txt
```

O programa termina com o comando `exit`.

## 5. Comandos e expressões

| Comando | Descrição |
| --- | --- |
| `define nome` | Declara um nome sem expressão associada. |
| `define nome = expressão` | Declara um nome associado a uma expressão. |
| `nome = expressão` | Associa (ou substitui) a expressão de um nome. |
| `eval(expressão, x, y, z)` | Avalia a expressão nos valores de `x`, `y` e `z`. |
| `diff(expressão, v)` | Deriva a expressão em relação a `v` (`x`, `y` ou `z`). |
| `list` | Lista os nomes definidos e suas expressões. |
| `clear nome` | Remove um nome. |
| `clear` | Remove todos os nomes. |
| `exit` | Encerra o programa. |

As expressões aceitam:

- números reais e as constantes `pi` e `e`;
- as variáveis `x`, `y` e `z`;
- os operadores `+`, `-`, `*`, `/` e `^`, além de `+` e `-` unários;
- parênteses para agrupamento;
- as funções `sin`, `cos` e `loge` (logaritmo natural);
- nomes definidos, usados diretamente (`q`) ou como função (`q(2)`), caso em
  que o `x` da definição é substituído pelo argumento.

A precedência, da menor para a maior, é: `+` e `-`; `*` e `/`; `^`. A potência
é associativa à direita (`x^y^z` equivale a `x^(y^z)`).

Exemplo de sessão:

```
> define q
Defined q
> q = x^2 + y * z
q = (x^2.0)+(y*z)
> eval(q(2), 1, 3, 4)
16.0
> diff(q, x)
2.0*x
> exit
Exiting
```

## 6. Dados de teste

`src/lpoo/t2/comandos-teste.txt` exercita todos os comandos: definição com e
sem expressão, atribuição, avaliação com substituição de `x` (`q(2)`),
derivadas de polinômios, de potências com expoente variável e de funções
trigonométricas e logarítmicas, além de `list`, `clear` (com e sem nome) e
`exit`.

## Vídeo

- https://youtu.be/uXI1kD2J-dI