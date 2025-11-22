# 🗜️ Huffman File Compressor

**Compressor de arquivos baseado no Algoritmo de Huffman**  
Implementação acadêmica demonstrando Algoritmos Gulosos e Ordenação Manual

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Algoritmos Implementados](#-algoritmos-implementados)
- [Instalação e Uso](#-instalação-e-uso)
- [Exemplos Práticos](#-exemplos-práticos)
- [Análise de Complexidade](#-análise-de-complexidade)
- [Casos de Uso](#-casos-de-uso)
- [Limitações Conhecidas](#-limitações-conhecidas)
- [Estrutura do Código](#-estrutura-do-código)

---

## 🎯 Sobre o Projeto

Sistema de compressão/descompressão de arquivos implementado do zero em Java, utilizando o **Algoritmo de Huffman** (técnica gulosa de codificação por prefixos). O projeto demonstra:

- ✅ **Algoritmo Guloso** - Construção ótima da árvore de Huffman
- ✅ **Ordenação Manual** - Merge Sort implementado do zero
- ✅ **Min-Heap (PriorityQueue)** - Estrutura de dados eficiente
- ✅ **Validação de Integridade** - CRC32 + SHA-256
- ✅ **Análise de Complexidade** - Estudo teórico e empírico

---

## 🧮 Algoritmos Implementados

### **1. Algoritmo de Huffman (Guloso)**

```
Estratégia Gulosa: Sempre combina os 2 nós de menor frequência
Garantia: Produz código de prefixo ótimo
Complexidade: O(k log k) onde k = símbolos únicos (≤ 256)
```

**Prova de Otimalidade:** A escolha gulosa de combinar os menores nós minimiza o comprimento médio dos códigos.

### **2. Merge Sort (Ordenação Manual)**

```
Paradigma: Dividir e Conquistar
Complexidade: O(n log n) - sempre estável
Espaço: O(n) - arrays temporários
```

Implementado na classe `FrequencyAnalyzer` para demonstrar domínio de algoritmos de ordenação.

### **3. Min-Heap via PriorityQueue**

```
Operações: insert O(log k), extractMin O(log k)
Uso: Construção eficiente da árvore de Huffman
```

---

## 🚀 Instalação e Uso

### **Requisitos**

- Java JDK 8 ou superior
- Sistema operacional: Windows, Linux ou macOS

### **Verificar Java**

```bash
java -version
javac -version
```

Se não tiver Java, instale:

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Windows:**
```bash
winget install Oracle.JDK.17
```

**macOS:**
```bash
brew install openjdk@17
```

### **Compilar**

```bash
javac HuffmanIoTSystem.java
```

### **Executar**

**Modo Linha de Comando:**
```bash
# Comprimir
java HuffmanIoTSystem compress arquivo.txt

# Descomprimir
java HuffmanIoTSystem decompress arquivo.txt.huff
```

**Modo Interativo:**
```bash
java HuffmanIoTSystem
```

---

## 📝 Exemplos Práticos

### **Exemplo 1: Comprimir Arquivo de Texto**

```bash
# Criar arquivo de teste
echo "Este texto se repete muitas vezes AAAA BBBB CCCC" > teste.txt

# Comprimir
java HuffmanIoTSystem compress teste.txt
```

**Saída esperada:**
```
📖 Lendo arquivo: teste.txt
🔍 Analisando frequências...
🌳 Construindo árvore de Huffman (Algoritmo Guloso)...
🔐 Gerando códigos de compressão...
🔒 Calculando checksums...
📦 Comprimindo...
✅ Arquivo comprimido: teste.txt.huff
📊 Original: 52 bytes | Comprimido: 89 bytes | Taxa: -71.15%
```

⚠️ **Nota:** Arquivos pequenos podem aumentar devido ao overhead da tabela!

### **Exemplo 2: Descomprimir**

```bash
# Remover original
rm teste.txt

# Descomprimir
java HuffmanIoTSystem decompress teste.txt.huff
```

**Saída esperada:**
```
📖 Lendo arquivo comprimido: teste.txt.huff
📄 Arquivo: teste.txt
🔄 Descomprimindo...
🔍 Validando integridade...
   CRC32: ✅
   SHA-256: ✅
✅ Arquivo restaurado: teste.txt
```

### **Exemplo 3: Comprimir Código-Fonte**

```bash
# Comprimir o próprio código do compressor
java HuffmanIoTSystem compress HuffmanIoTSystem.java
```

**Resultado típico:** 45-55% de compressão

### **Exemplo 4: Arquivo de Log**

```bash
# Criar log grande
for i in {1..1000}; do 
    echo "[2024-11-19 17:30:$i] INFO Sistema processando requisição $i"
done > sistema.log

# Comprimir
java HuffmanIoTSystem compress sistema.log
```

**Resultado esperado:** 60-70% de compressão (logs são muito repetitivos!)

---

## 📊 Análise de Complexidade

### **Complexidade Temporal**

| Operação | Complexidade | Justificativa |
|----------|--------------|---------------|
| **Análise de Frequência** | O(n) | Percorre arquivo uma vez |
| **Construção da Árvore** | O(k log k) | k inserções/remoções no heap |
| **Geração de Códigos** | O(k) | Percorre árvore (2k-1 nós) |
| **Compressão** | O(n × m̄) | n bytes × m̄ bits/código (m̄ ≈ 6) |
| **Descompressão** | O(n × d̄) | n bytes × d̄ profundidade (d̄ ≈ 6) |
| **Validação (CRC32)** | O(n) | Hash linear |
| **Validação (SHA-256)** | O(n) | Hash linear (mais lento) |

**Onde:**
- `n` = tamanho do arquivo (bytes)
- `k` = símbolos únicos (máximo 256 para bytes)
- `m̄` = comprimento médio dos códigos
- `d̄` = profundidade média da árvore

### **Complexidade Espacial**

| Estrutura | Espaço | Descrição |
|-----------|--------|-----------|
| Tabela de Frequências | O(k) | Máximo 256 entradas |
| Árvore de Huffman | O(k) | 2k-1 nós total |
| Tabela de Códigos | O(k) | Máximo 256 códigos |
| Buffer de Dados | O(n) | Arquivo carregado na memória |
| **Total** | **O(n + k)** | Dominado por O(n) |

### **Análise Assintótica Simplificada**

Como `k ≤ 256` (constante), podemos simplificar:

```
Compressão:   O(n + k log k) ≈ O(n)
Descompressão: O(n × d̄) ≈ O(n)  [d̄ é pequeno, ~6]
```

---

## 🎯 Casos de Uso

### **✅ Arquivos Ideais para Compressão (40-70%)**

| Tipo | Extensões | Compressão Esperada | Motivo |
|------|-----------|---------------------|--------|
| **Texto** | `.txt`, `.log`, `.csv` | 50-70% | Alta repetição de caracteres |
| **Código-fonte** | `.java`, `.py`, `.js`, `.c` | 40-60% | Palavras-chave repetidas |
| **JSON/XML** | `.json`, `.xml`, `.html` | 50-70% | Estrutura repetitiva |
| **Markdown** | `.md` | 45-60% | Sintaxe repetitiva |
| **Logs** | `.log` | 60-80% | Timestamps e padrões |

### **⚠️ Arquivos com Compressão Moderada (10-40%)**

| Tipo | Extensões | Compressão | Motivo |
|------|-----------|------------|--------|
| **Documentos** | `.rtf`, `.tex` | 20-40% | Mistura de texto e formatação |
| **SVG** | `.svg` | 30-50% | XML com dados numéricos |

### **❌ Arquivos NÃO Recomendados (0-5%)**

| Tipo | Extensões | Resultado | Motivo |
|------|-----------|-----------|--------|
| **Imagens** | `.jpg`, `.png`, `.gif` | Aumenta | Já comprimidos |
| **Vídeos** | `.mp4`, `.avi`, `.mkv` | Aumenta | Já comprimidos |
| **Áudio** | `.mp3`, `.ogg`, `.flac` | Aumenta | Já comprimidos |
| **Arquivos comprimidos** | `.zip`, `.rar`, `.7z`, `.gz` | Aumenta | Compressão dupla inútil |
| **Binários aleatórios** | Executáveis, dados criptografados | Aumenta | Alta entropia |

### **Regra de Ouro:**

> **Se o arquivo tem entropia alta (dados aleatórios), Huffman não ajuda!**  
> Use apenas em arquivos com padrões repetitivos.

---

## ⚠️ Limitações Conhecidas

### **1. Uso de Memória (readAllBytes)**

```java
byte[] data = Files.readAllBytes(Paths.get(inputPath));
```

**Problema:** Carrega arquivo inteiro na memória RAM

**Limite Prático:**
- Arquivos até 500 MB: ✅ OK
- Arquivos 500 MB - 2 GB: ⚠️ Pode travar
- Arquivos > 2 GB: ❌ OutOfMemoryError

**Solução Futura:** Implementar streaming com buffer de 8KB

### **2. Arquivos Pequenos (<1 KB)**

```
Original:  500 bytes
Tabela:    ~400 bytes (overhead)
Dados:     ~350 bytes
TOTAL:     ~750 bytes (50% PIOR!)
```

**Recomendação:** Não comprimir arquivos < 2 KB

### **3. Dados com Alta Entropia**

```
Arquivo aleatório → Todos os 256 bytes com frequência similar
Resultado: Códigos de ~8 bits (SEM ganho)
Overhead: Tabela de códigos
TOTAL: Arquivo AUMENTA
```

### **4. Performance em Arquivos Grandes**

```
Arquivo de 100 MB de log repetitivo:
- Compressão: ~5 segundos
- Descompressão: ~8 segundos

Gargalo: Navegação bit-a-bit na árvore
```

---

## 🏗️ Estrutura do Código

### **Arquitetura**

```
HuffmanIoTSystem.java
├── HuffmanNode              (Nó da árvore binária)
├── FrequencyAnalyzer        (Análise + Merge Sort)
├── HuffmanTreeBuilder       (Construção gulosa + geração códigos)
├── HuffmanCompressor        (Compressão/descompressão)
└── Main                     (Interface CLI)
```

### **Fluxo de Compressão**

```
Arquivo Original
    ↓
[1] Análise de Frequência → Map<Byte, Long>
    ↓
[2] Construção da Árvore (Guloso + Min-Heap)
    ↓
[3] Geração de Códigos → Map<Byte, String>
    ↓
[4] Codificação bit-a-bit
    ↓
[5] Cálculo de Checksums (CRC32 + SHA-256)
    ↓
[6] Salvamento do .huff
```

### **Formato do Arquivo .huff**

```
[Magic Number: 0x48554646 (4 bytes)]     "HUFF"
[Nome do arquivo original (String)]
[Tamanho original (8 bytes)]
[CRC32 (8 bytes)]
[SHA-256 (32 bytes)]
[Número de códigos (4 bytes)]
[Tabela: ByteValue (4) + Código (String)] × N
[Dados comprimidos (bits empacotados)]
```

---

## 🎓 Para Relatório Acadêmico

### **Conceitos Demonstrados**

1. **Algoritmos Gulosos**
    - Prova de otimalidade do Huffman
    - Escolha local → ótimo global

2. **Estruturas de Dados**
    - Árvore binária
    - Min-Heap (PriorityQueue)
    - HashMap para códigos

3. **Análise de Complexidade**
    - Notação Big-O
    - Melhor/médio/pior caso
    - Análise empírica

4. **Ordenação**
    - Merge Sort (Dividir e Conquistar)
    - Estabilidade e previsibilidade

5. **Validação de Dados**
    - Checksums criptográficos
    - Integridade de dados

### **Experimentos Sugeridos**

```bash
# 1. Teste com diferentes tamanhos
for size in 1KB 10KB 100KB 1MB 10MB; do
    echo "Testando arquivo de $size..."
done

# 2. Teste com diferentes tipos
Texto puro, código-fonte, JSON, binário aleatório

# 3. Meça tempos
time java HuffmanIoTSystem compress arquivo.txt

# 4. Compare entropias
Arquivo repetitivo vs arquivo aleatório
```

---

## 📚 Referências

- Huffman, D. A. (1952). "A Method for the Construction of Minimum-Redundancy Codes"
- Cormen et al. "Introduction to Algorithms" (Capítulo 16: Greedy Algorithms)
- Knuth, D. E. "The Art of Computer Programming, Vol. 3: Sorting and Searching"

---

## 🐛 Solução de Problemas

### **Erro: "java: command not found"**
- Java não instalado → Veja seção [Instalação](#-instalação-e-uso)

### **Erro: "OutOfMemoryError"**
- Arquivo muito grande → Limitação conhecida (>500MB)
- Solução: Aumentar heap do Java: `java -Xmx2G HuffmanIoTSystem ...`

### **Arquivo aumentou após compressão**
- Normal para arquivos pequenos (<1KB) ou já comprimidos
- Huffman não é adequado para esses casos

### **Validação falhou (CRC32/SHA-256)**
- Arquivo .huff corrompido
- Não use o arquivo descomprimido!

---

## 📊 Resultados Esperados

### **Arquivo de Código Java (15 KB)**
```
Original:     15.42 KB
Comprimido:   8.21 KB
Taxa:         46.75% de compressão
Tempo:        12 ms
```

### **Arquivo de Log Repetitivo (50 KB)**
```
Original:     50.00 KB
Comprimido:   18.34 KB
Taxa:         63.32% de compressão
Tempo:        35 ms
```

### **Arquivo Pequeno (500 bytes)**
```
Original:     500 B
Comprimido:   750 B
Taxa:         -50% (PIOR!)
Motivo:       Overhead da tabela
```

---

## 👨‍💻 Autor

Projeto desenvolvido como trabalho acadêmico para demonstrar:
- Implementação de Algoritmos Gulosos
- Análise de Complexidade
- Estruturas de Dados Avançadas
- Técnicas de Compressão

---

## 📄 Licença

Projeto acadêmico para fins educacionais.

---

**Versão:** 2.0 (Simplificada)  
**Data:** Novembro 2024  
**Java:** 8+  
**Compatibilidade:** Windows, Linux, macOS