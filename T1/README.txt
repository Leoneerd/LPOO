TRABALHO 1 - LINGUAGEM DE PROGRAMACAO ORIENTADA A OBJETOS 2026

Autores
Guilherme Duarte
Otavio Gabriel
Leonardo Leal

1. Descricao do trabalho

A hierarquia parte da classe abstrata Shape, que define os dados e operacoes
comuns. Box, Sphere, Cylinder e Capsule implementam os primitivos. CompoundShape
combina formas filhas, inclusive outras composicoes, e
CompoundShapeInstance reutiliza uma definicao composta com uma pose propria.
RigidBody associa a forma raiz do ator a uma pose na cena e transforma os
resultados locais para o sistema global.

O SceneReader interpreta o arquivo de entrada, cria as formas e os atores e
relata erros de leitura com o arquivo e a linha correspondentes. A sintaxe
adotada organiza definicoes reutilizaveis antes dos atores. Main coordena o
fluxo: recebe o caminho da cena, chama SceneReader e envia os corpos lidos a
SceneReport. O relatorio descreve as propriedades de cada corpo e percorre
recursivamente as formas compostas para incluir suas filhas.

MeshShape permite representar formas por malhas triangulares. O
ObjReader carrega arquivos OBJ, e MeshShape utiliza a malha para calcular suas
propriedades geometricas. A cena carrinho.txt inclui uma malha cubo.obj como
parte da demonstracao.

2. Organizacao do projeto

src/lpoo/                           Classe principal Main.
src/lpoo/geom/                      Formas, poses e propriedades geometricas.
src/lpoo/phyx/                      Representacao dos corpos rigidos.
src/lpoo/util/                      SceneReader e SceneReport.
src/lpoo/exception/                 Excecoes usadas pelo programa.
tools/                              Classes de apoio fornecidas para o trabalho.
tests/scenes/                       Cenas e arquivos OBJ usados nos testes.
out/                                Diretorio gerado para arquivos compilados.


3. Compilacao e execucao no Linux

mkdir -p out
javac -d out $(find src tools tests -name '*.java')
java -cp out lpoo.Main tests/scenes/carrinho.txt

4. Formato das cenas

Os arquivos de cena sao textos. Comentarios comecam por #, chaves delimitam
atores e composicoes, e os valores numericos sao separados por espacos. Nomes e
caminhos nao devem conter espacos.

Definicoes de formas compostas aparecem antes dos atores:

define composite nome {
  <uma ou mais formas filhas>
}

Cada ator possui exatamente uma forma raiz. Uma pose pode especificar posicao
com pos x y z e orientacao com rot qx qy qz qw. Esses elementos sao opcionais e
podem aparecer em atores e formas. O quaternion e normalizado pelo leitor.

Tipos de forma aceitos:

box nome densidade sx sy sz [pose]
sphere nome densidade raio [pose]
cylinder nome densidade raio s [pose]
capsule nome densidade raio s [pose]
mesh nome densidade caminho_obj [pose]
composite nome [pose] { <uma ou mais formas filhas> }
instance nome nome_da_definicao [pose]

Nas caixas, sx, sy e sz sao as semi-dimensoes. Para cilindros e capsulas, s e a
semi-altura. Uma instancia pode referenciar uma definicao ja declarada. Os
caminhos OBJ relativos sao interpretados em relacao ao arquivo de cena.

Exemplo minimo:

actor a {
  box b 1 1 1 1
}

5. Dados de teste

tests/scenes/carrinho.txt demonstra os quatro primitivos, composicoes aninhadas,
instancias reutilizadas, poses e uma malha OBJ. minimo.txt e sem_define.txt sao
cenas menores. Os arquivos cujo nome comeca por erro_ representam entradas para
exercitar os erros do leitor. cubo.obj e usado pela cena carrinho.txt.

Link para o video:
https://youtu.be/E7o1KKluaBY
