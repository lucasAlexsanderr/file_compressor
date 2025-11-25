// ============================================
// COMPRESSOR HUFFMAN - VERSÃO SIMPLIFICADA
// Algoritmo Guloso + Merge Sort + Validação
// ============================================

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.zip.CRC32;

// ============================================
// NÓ DA ÁRVORE DE HUFFMAN
// ============================================
class HuffmanNode implements Comparable<HuffmanNode> {
    int byteValue;
    long frequency;
    HuffmanNode left, right;

    public HuffmanNode(int byteValue, long frequency) {
        this.byteValue = byteValue;
        this.frequency = frequency;
    }

    public HuffmanNode(long frequency, HuffmanNode left, HuffmanNode right) {
        this.byteValue = -1;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return Long.compare(this.frequency, other.frequency);
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }
}

// ============================================
// ANALISADOR DE FREQUÊNCIA
// ============================================
class FrequencyAnalyzer {

    public Map<Integer, Long> analyzeFrequency(byte[] data) {
        Map<Integer, Long> frequencyMap = new HashMap<>();
        for (byte b : data) {
            int byteValue = b & 0xFF;
            frequencyMap.put(byteValue, frequencyMap.getOrDefault(byteValue, 0L) + 1);
        }
        return frequencyMap;
    }

    // ============================================
    // MERGE SORT MANUAL - Requisito do Trabalho
    // Complexidade: O(n log n)
    // ============================================
    public List<HuffmanNode> mergeSort(List<HuffmanNode> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        List<HuffmanNode> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
        List<HuffmanNode> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private List<HuffmanNode> merge(List<HuffmanNode> left, List<HuffmanNode> right) {
        List<HuffmanNode> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).frequency <= right.get(j).frequency) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}

// ============================================
// CONSTRUTOR DA ÁRVORE DE HUFFMAN
// ============================================
class HuffmanTreeBuilder {

    // Constrói árvore usando MERGE SORT + Algoritmo Guloso
    public HuffmanNode buildTree(Map<Integer, Long> frequencies) {
        FrequencyAnalyzer analyzer = new FrequencyAnalyzer();

        // Cria lista de nós folha
        List<HuffmanNode> nodes = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : frequencies.entrySet()) {
            nodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // ============================================
        // USA MERGE SORT MANUAL para ordenação inicial
        // ============================================
        System.out.println("   Ordenando nós com Merge Sort (O(k log k))...");
        nodes = analyzer.mergeSort(nodes);

        // ============================================
        // Algoritmo Guloso de Huffman
        // Sempre combina os 2 menores nós
        // ============================================
        while (nodes.size() > 1) {
            // Remove os 2 menores (início da lista ordenada)
            HuffmanNode left = nodes.remove(0);
            HuffmanNode right = nodes.remove(0);

            // Cria nó pai
            HuffmanNode parent = new HuffmanNode(
                    left.frequency + right.frequency,
                    left,
                    right
            );

            // Insere mantendo ordenação (inserção ordenada)
            insertSorted(nodes, parent);
        }

        return nodes.isEmpty() ? null : nodes.get(0);
    }

    // Inserção ordenada (mantém lista ordenada após adicionar novo nó)
    private void insertSorted(List<HuffmanNode> nodes, HuffmanNode newNode) {
        int i = 0;
        while (i < nodes.size() && nodes.get(i).frequency < newNode.frequency) {
            i++;
        }
        nodes.add(i, newNode);
    }

    // Gera códigos binários da árvore
    public Map<Integer, String> generateCodes(HuffmanNode root) {
        Map<Integer, String> codes = new HashMap<>();
        if (root == null) return codes;

        if (root.isLeaf()) {
            codes.put(root.byteValue, "0");
        } else {
            generateCodesRecursive(root, "", codes);
        }

        return codes;
    }

    private void generateCodesRecursive(HuffmanNode node, String code, Map<Integer, String> codes) {
        if (node == null) return;

        if (node.isLeaf()) {
            codes.put(node.byteValue, code);
            return;
        }

        generateCodesRecursive(node.left, code + "0", codes);
        generateCodesRecursive(node.right, code + "1", codes);
    }
}

// ============================================
// COMPRESSOR
// ============================================
class HuffmanCompressor {

    public void compress(String inputPath, String outputPath) throws Exception {
        System.out.println("📖 Lendo arquivo: " + inputPath);
        byte[] data = Files.readAllBytes(Paths.get(inputPath));

        System.out.println("🔍 Analisando frequências...");
        FrequencyAnalyzer analyzer = new FrequencyAnalyzer();
        Map<Integer, Long> frequencies = analyzer.analyzeFrequency(data);

        System.out.println("🌳 Construindo árvore de Huffman (Algoritmo Guloso)...");
        HuffmanTreeBuilder builder = new HuffmanTreeBuilder();
        HuffmanNode root = builder.buildTree(frequencies);

        System.out.println("🔐 Gerando códigos de compressão...");
        Map<Integer, String> codes = builder.generateCodes(root);

        System.out.println("🔒 Calculando checksums...");
        CRC32 crc = new CRC32();
        crc.update(data);
        long crc32 = crc.getValue();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] sha256 = sha.digest(data);

        System.out.println("📦 Comprimindo...");
        saveCompressed(outputPath, data, codes, crc32, sha256,
                new File(inputPath).getName());

        System.out.println("✅ Arquivo comprimido: " + outputPath);

        // Estatísticas
        long originalSize = data.length;
        long compressedSize = new File(outputPath).length();
        double ratio = 100.0 * (1 - (double) compressedSize / originalSize);
        System.out.printf("📊 Original: %d bytes | Comprimido: %d bytes | Taxa: %.2f%%%n",
                originalSize, compressedSize, ratio);
    }

    private void saveCompressed(String path, byte[] data, Map<Integer, String> codes,
                                long crc32, byte[] sha256, String originalName) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(path)))) {

            // Cabeçalho
            dos.writeInt(0x48554646); // "HUFF"
            dos.writeUTF(originalName);
            dos.writeLong(data.length);
            dos.writeLong(crc32);
            dos.write(sha256);

            // Tabela de códigos
            dos.writeInt(codes.size());
            for (Map.Entry<Integer, String> entry : codes.entrySet()) {
                dos.writeInt(entry.getKey());
                dos.writeUTF(entry.getValue());
            }

            // Dados comprimidos
            int currentByte = 0;
            int bitPos = 7;
            long totalBits = 0;

            for (byte b : data) {
                String code = codes.get(b & 0xFF);
                totalBits += code.length();

                for (char bit : code.toCharArray()) {
                    if (bit == '1') {
                        currentByte |= (1 << bitPos);
                    }
                    bitPos--;

                    if (bitPos < 0) {
                        dos.writeByte(currentByte);
                        currentByte = 0;
                        bitPos = 7;
                    }
                }
            }

            if (bitPos < 7) {
                dos.writeByte(currentByte);
            }
        }
    }

    public void decompress(String inputPath, String outputDir) throws Exception {
        System.out.println("📖 Lendo arquivo comprimido: " + inputPath);

        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(inputPath)))) {

            // Cabeçalho
            int magic = dis.readInt();
            if (magic != 0x48554646) {
                throw new IOException("Arquivo inválido!");
            }

            String originalName = dis.readUTF();
            long originalSize = dis.readLong();
            long expectedCRC32 = dis.readLong();
            byte[] expectedSHA256 = new byte[32];
            dis.readFully(expectedSHA256);

            System.out.println("📄 Arquivo: " + originalName);

            // Lê tabela e reconstrói árvore
            int numCodes = dis.readInt();
            HuffmanNode root = new HuffmanNode(-1, 0);

            for (int i = 0; i < numCodes; i++) {
                int byteValue = dis.readInt();
                String code = dis.readUTF();

                HuffmanNode current = root;
                for (char bit : code.toCharArray()) {
                    if (bit == '0') {
                        if (current.left == null) current.left = new HuffmanNode(-1, 0);
                        current = current.left;
                    } else {
                        if (current.right == null) current.right = new HuffmanNode(-1, 0);
                        current = current.right;
                    }
                }
                current.byteValue = byteValue;
            }

            System.out.println("🔄 Descomprimindo...");

            // Descomprime
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            HuffmanNode current = root;

            try {
                while (output.size() < originalSize) {
                    int byteValue = dis.readUnsignedByte();

                    for (int bitPos = 7; bitPos >= 0 && output.size() < originalSize; bitPos--) {
                        boolean bit = ((byteValue >> bitPos) & 1) == 1;
                        current = bit ? current.right : current.left;

                        if (current.isLeaf()) {
                            output.write(current.byteValue);
                            current = root;
                        }
                    }
                }
            } catch (EOFException e) {
                // Fim do arquivo
            }

            byte[] decompressedData = output.toByteArray();

            // Valida integridade
            System.out.println("🔍 Validando integridade...");

            CRC32 crc = new CRC32();
            crc.update(decompressedData);
            boolean crcValid = (crc.getValue() == expectedCRC32);

            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            boolean sha256Valid = Arrays.equals(sha.digest(decompressedData), expectedSHA256);

            System.out.println("   CRC32: " + (crcValid ? "✅" : "❌"));
            System.out.println("   SHA-256: " + (sha256Valid ? "✅" : "❌"));

            if (!crcValid || !sha256Valid) {
                System.err.println("⚠️  AVISO: Falha na validação!");
            }

            // Salva
            String outputPath = outputDir + File.separator + originalName;
            Files.write(Paths.get(outputPath), decompressedData);
            System.out.println("✅ Arquivo restaurado: " + outputPath);
        }
    }
}

// ============================================
// MAIN
// ============================================
public class HuffmanIoTSystem {

    public static void main(String[] args) {
        if (args.length == 0) {
            runInteractive();
        } else {
            runCommandLine(args);
        }
    }

    private static void runCommandLine(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso:");
            System.out.println("  java HuffmanIoTSystem compress <arquivo>");
            System.out.println("  java HuffmanIoTSystem decompress <arquivo.huff>");
            return;
        }

        HuffmanCompressor compressor = new HuffmanCompressor();

        try {
            if (args[0].equals("compress")) {
                String input = args[1];
                String output = input + ".huff";
                compressor.compress(input, output);
            } else if (args[0].equals("decompress")) {
                String input = args[1];
                String outputDir = new File(input).getParent();
                if (outputDir == null) outputDir = ".";
                compressor.decompress(input, outputDir);
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runInteractive() {
        Scanner scanner = new Scanner(System.in);
        HuffmanCompressor compressor = new HuffmanCompressor();

        System.out.println("=".repeat(60));
        System.out.println("🗜️  HUFFMAN COMPRESSOR");
        System.out.println("=".repeat(60));

        while (true) {
            System.out.println("\n1. Comprimir arquivo");
            System.out.println("2. Descomprimir arquivo");
            System.out.println("3. Sair");
            System.out.print("\nEscolha: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Arquivo: ");
                    String input = scanner.nextLine().trim();
                    String output = input + ".huff";
                    compressor.compress(input, output);

                } else if (choice == 2) {
                    System.out.print("Arquivo .huff: ");
                    String input = scanner.nextLine().trim();
                    String outputDir = new File(input).getParent();
                    if (outputDir == null) outputDir = ".";
                    compressor.decompress(input, outputDir);

                } else if (choice == 3) {
                    System.out.println("Saindo...");
                    break;
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }

            System.out.print("\nPressione ENTER...");
            scanner.nextLine();
        }

        scanner.close();
    }
}