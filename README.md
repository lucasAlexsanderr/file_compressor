# 🗜️ Huffman File Compressor

**Compressor de arquivos baseado no algoritmo de Huffman**  
Projeto desenvolvido para o curso de Ciência da Computação - Módulo de Programação Dinâmica e Algoritmos Gulosos

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Características](#-características)
- [Requisitos](#-requisitos)
- [Instalação](#-instalação)
- [Como Usar](#-como-usar)
- [Exemplos Práticos](#-exemplos-práticos)
- [Caminhos de Arquivo](#-caminhos-de-arquivo)
- [Análise Técnica](#-análise-técnica)
- [Formatos Recomendados](#-formatos-recomendados)

---

## 🎯 Sobre o Projeto

Este sistema implementa um compressor de arquivos utilizando o **Algoritmo de Huffman**, uma técnica de compressão sem perdas baseada em codificação de prefixos. O projeto demonstra conceitos de:

- ✅ **Algoritmos Gulosos** (Huffman)
- ✅ **Ordenação Manual** (Merge Sort implementado do zero)
- ✅ **Estruturas de Dados** (Árvores Binárias)
- ✅ **Análise de Complexidade** (O(n log n))
- ✅ **Manipulação de Arquivos** (I/O em Java)

---

## 🌟 Características

- 🗜️ **Compressão** de qualquer tipo de arquivo
- 📂 **Descompressão** com restauração 100% fiel ao original
- 📦 **Compressão em lote** de múltiplos arquivos
- 🔍 **Análise prévia** sem comprimir
- 🖥️ **Compatível** com Windows, Linux e macOS
- 💾 Formato proprietário `.huff` com metadados
- ⚡ Interface de linha de comando e modo interativo

---

## 📦 Requisitos

- **Java JDK 8 ou superior**
- Sistema operacional: Windows, Linux ou macOS

### Verificar instalação do Java:

```bash
java -version
javac -version
```

Se não tiver Java instalado:

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Windows:**
- Baixe em: https://www.oracle.com/java/technologies/downloads/
- Ou use: `winget install Oracle.JDK.17`

**macOS:**
```bash
brew install openjdk@17
```

---

## 🚀 Instalação

### 1. Clone ou baixe o projeto

```bash
# Navegue até o diretório do projeto
cd caminho/para/HuffmanCompressor
```

### 2. Compile o código

```bash
javac HuffmanIoTSystem.java
```

✅ Você verá vários arquivos `.class` criados (isso é normal!)

---

## 💻 Como Usar

### **Modo 1: Interativo (Recomendado para iniciantes)**

```bash
java HuffmanIoTSystem
```

Um menu será exibido:

```
🗜️  HUFFMAN FILE COMPRESSOR - Compressor de Arquivos
   Algoritmo Guloso com Ordenação Manual (Merge Sort)
   Compatível com Windows, Linux e macOS

📋 MENU PRINCIPAL:
1. 🗜️  Comprimir arquivo
2. 📂 Descomprimir arquivo
3. 📦 Comprimir múltiplos arquivos
4. 🔍 Analisar arquivo (sem comprimir)
5. 💡 Ajuda (como usar caminhos)
6. 🚪 Sair
```

### **Modo 2: Linha de Comando (Rápido)**

**Comprimir um arquivo:**
```bash
java HuffmanIoTSystem compress caminho/do/arquivo.txt
```

**Descomprimir:**
```bash
java HuffmanIoTSystem decompress arquivo.txt.huff
```

**Especificar arquivo de saída:**
```bash
java HuffmanIoTSystem compress entrada.txt saida.huff
```

---

## 📝 Exemplos Práticos

### **Exemplo 1: Comprimir um arquivo de texto**

```bash
# Criar arquivo de teste
echo "Este é um teste de compressão Huffman com muitas repetições AAAAA BBBBB" > teste.txt

# Comprimir
java HuffmanIoTSystem compress teste.txt

# Resultado:
✅ COMPRESSÃO CONCLUÍDA COM SUCESSO!
============================================================
📄 Arquivo original: teste.txt
📦 Arquivo comprimido: teste.txt.huff
------------------------------------------------------------
📏 Tamanho original:    75 B
📦 Tamanho comprimido:  45 B
📉 Economia:            30 B (40.00%)
⏱️  Tempo de execução:  12.45 ms
============================================================
```

### **Exemplo 2: Descomprimir**

```bash
java HuffmanIoTSystem decompress teste.txt.huff

# Resultado:
✅ DESCOMPRESSÃO CONCLUÍDA COM SUCESSO!
============================================================
📦 Arquivo comprimido: teste.txt.huff
📄 Arquivo restaurado: teste.txt
------------------------------------------------------------
📏 Tamanho restaurado:  75 B
⏱️  Tempo de execução:  8.32 ms
✔️  Integridade:        Verificada
============================================================
```

### **Exemplo 3: Comprimir código-fonte**

```bash
java HuffmanIoTSystem compress HuffmanIoTSystem.java

# Compressão típica: 45-55% de economia
```

### **Exemplo 4: Analisar antes de comprimir**

```bash
java HuffmanIoTSystem

# Escolha opção 4
# Digite o caminho do arquivo
# Veja estatísticas sem modificar o arquivo
```

---

## 📂 Caminhos de Arquivo

O sistema **detecta automaticamente** o seu sistema operacional e aceita diferentes formatos de caminho.

### **Windows**

```bash
# Formas válidas:
C:\Users\seu_nome\Desktop\arquivo.txt
C:/Users/seu_nome/Desktop/arquivo.txt
"C:\Meus Documentos\arquivo com espaço.txt"
arquivo.txt                    # pasta atual
..\arquivo.txt                 # pasta anterior
```

💡 **Dica Windows:** Arraste o arquivo para a janela do terminal - o caminho completo será colado automaticamente!

### **Linux / macOS**

```bash
# Formas válidas:
/home/usuario/Documentos/arquivo.txt
~/Documentos/arquivo.txt       # ~ = sua home
./arquivo.txt                  # pasta atual
../arquivo.txt                 # pasta anterior
"/home/Meus Arquivos/arquivo.txt"  # com espaços
```

💡 **Dica Linux/Mac:** Use TAB para autocompletar caminhos e arraste arquivos para o terminal!

### **Caminhos Relativos vs Absolutos**

**Absoluto (caminho completo):**
- Windows: `C:\Users\nome\arquivo.txt`
- Linux: `/home/usuario/arquivo.txt`

**Relativo (a partir da pasta atual):**
- `arquivo.txt` - arquivo na pasta atual
- `pasta/arquivo.txt` - arquivo dentro de uma subpasta
- `../arquivo.txt` - arquivo na pasta anterior

**Descobrir pasta atual:**
```bash
# Linux/Mac:
pwd

# Windows:
cd
```

---

## 🔬 Análise Técnica

### **Algoritmo de Huffman (Guloso)**

1. **Análise de Frequência:** O(n) - percorre todo o arquivo
2. **Ordenação (Merge Sort):** O(k log k) - k = bytes únicos (max 256)
3. **Construção da Árvore:** O(k log k) - combinação gulosa
4. **Geração de Códigos:** O(k) - percorre árvore
5. **Compressão Final:** O(n) - codifica cada byte

**Complexidade Total:** O(n + k log k) ≈ **O(n)** na prática (k ≤ 256)

### **Estratégia Gulosa**

A cada passo, o algoritmo escolhe os **2 nós de menor frequência** para combinar. Essa escolha local ótima garante o código ótimo globalmente.

### **Ordenação Manual**

Implementação própria do **Merge Sort** (sem usar bibliotecas Java):
- Estável e eficiente
- O(n log n) garantido
- Divide e conquista recursivo

---

## 📊 Formatos Recomendados

### ✅ **Alta Compressão (40-70%)**

| Formato | Extensões | Compressão Esperada |
|---------|-----------|---------------------|
| Texto puro | `.txt`, `.log`, `.csv` | 50-70% |
| Código-fonte | `.java`, `.py`, `.c`, `.js` | 40-60% |
| Dados estruturados | `.json`, `.xml`, `.html` | 50-70% |
| Código web | `.css`, `.svg`, `.md` | 45-65% |

### ⚠️ **Compressão Moderada (10-40%)**

| Formato | Extensões | Compressão Esperada |
|---------|-----------|---------------------|
| Documentos | `.rtf`, `.tex` | 20-40% |
| Dados binários | `.bmp` (bitmap simples) | 15-30% |

### ❌ **Não Recomendado (0-5%)**

| Formato | Extensões | Motivo |
|---------|-----------|--------|
| Imagens comprimidas | `.jpg`, `.png`, `.gif` | Já estão comprimidas |
| Vídeos | `.mp4`, `.avi`, `.mkv` | Já estão comprimidos |
| Áudio | `.mp3`, `.ogg`, `.flac` | Já estão comprimidos |
| Arquivos comprimidos | `.zip`, `.rar`, `.7z`, `.gz` | Compressão dupla ineficaz |

💡 **Dica:** Use a opção "4. Analisar arquivo" para verificar a compressão estimada antes de comprimir!

---

## 🎓 Conceitos Implementados

### **1. Algoritmo Guloso**
- Escolhas localmente ótimas
- Prova de otimalidade do Huffman
- Aplicação prática em compressão

### **2. Ordenação Manual**
- Merge Sort implementado do zero
- Sem uso de `Arrays.sort()` ou `Collections.sort()`
- Análise de complexidade

### **3. Estruturas de Dados**
- Árvore binária de Huffman
- Mapa de frequências
- Serialização de objetos

### **4. Análise de Desempenho**
- Medição de tempo de execução
- Cálculo de taxa de compressão
- Comparação teórica vs prática

---

## 🐛 Solução de Problemas

### **Erro: "java: command not found"**
- Java não está instalado ou não está no PATH
- Solução: Instale o JDK e configure o PATH

### **Erro: "Arquivo não encontrado"**
- Verifique se o caminho está correto
- Use a opção "5. Ajuda" no menu para ver exemplos
- No Windows, use aspas para caminhos com espaços

### **Erro: "Can't find main method"**
- Você usou `java HuffmanIoTSystem.java` (errado)
- Use: `java HuffmanIoTSystem` (sem .java)

### **Arquivo não comprime bem (0-5%)**
- Arquivo já está comprimido (JPG, MP4, ZIP, etc.)
- Use a opção de análise prévia para verificar

---

## 📈 Resultados Esperados

### **Código Java (HuffmanIoTSystem.java)**
- Tamanho original: ~15 KB
- Comprimido: ~7 KB
- Taxa: **~50% de economia**

### **Arquivo de texto (.txt)**
- Compressão: **40-70%**
- Melhor para textos repetitivos

### **JSON/XML**
- Compressão: **50-70%**
- Estruturas repetitivas comprimem muito

### **Imagens JPG/PNG**
- Compressão: **0-5%**
- Não vale a pena (já comprimidas)

---

## 👨‍💻 Autor

Desenvolvido como projeto acadêmico para demonstrar:
- Algoritmos Gulosos (Huffman)
- Ordenação Manual (Merge Sort)
- Análise de Complexidade
- Estruturas de Dados Avançadas

---

## 📄 Licença

Projeto acadêmico desenvolvido para fins educacionais.

---

## 🆘 Precisa de Ajuda?

1. Execute `java HuffmanIoTSystem` e escolha a opção **5. Ajuda**
2. Verifique a seção [Caminhos de Arquivo](#-caminhos-de-arquivo)
3. Consulte os [Exemplos Práticos](#-exemplos-práticos)
4. Revise a [Solução de Problemas](#-solução-de-problemas)

---

**Versão:** 1.0  
**Data:** Novembro 2025  
**Compatibilidade:** Java 8+ | Windows, Linux, macOS