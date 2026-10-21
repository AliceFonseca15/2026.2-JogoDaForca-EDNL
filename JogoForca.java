import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * JogoForca.java
 * 
 * Classe principal responsável pela interface interativa via terminal do Jogo da Forca da ABB.
 * Implementa o menu de opções, tratamento robusto de entradas e exceções, visualização gráfica
 * em ASCII da forca, exibição dinâmica da árvore com nós ocultos e revelados, histórico
 * de tentativas e integração com as regras do jogo (RegrasForca).
 * 
 * Projeto: Grupo 1 - Jogo da Forca da ABB (EDNL 2026.2)
 * Integrantes:
 * - Alice Fonseca dos Santos (Pessoa 1)
 * - Davi de Medeiros Linhares (Pessoa 2)
 * - Caio Rodrigo Galiza Salin de Melo (Pessoa 3)
 */
public class JogoForca {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            exibirMenuPrincipal();
            // Remova o número 3 no final desta linha:
            int opcao = lerOpcaoMenu(scanner, "Escolha uma opção: ", 0, 1, 2); 

            switch (opcao) {
                case 1:
                    iniciarPartida(scanner, 7, 6);
                    break;
                case 2:
                    configurarEJogoPersonalizado(scanner);
                    break;
                case 0:
                    executando = false;
                    System.out.println("\nObrigado por jogar o Jogo da Forca da ABB! Até a próxima!\n");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Exibe o cabeçalho e as opções do Menu Principal.
     */
    private static void exibirMenuPrincipal() {
        System.out.println("\n========================================================");
        System.out.println("              JOGO DA FORCA DA ABB - EDNL               ");
        System.out.println("========================================================");
        System.out.println("  [1] Novo Jogo Rápido (7 nós | 6 erros permitidos)");
        System.out.println("  [2] Novo Jogo Personalizado (configurar nós e erros)");
        System.out.println("  [0] Sair");
        System.out.println("========================================================");
    }

    /**
     * Permite ao jogador configurar a quantidade de nós e o limite de erros para a partida.
     */
    private static void configurarEJogoPersonalizado(Scanner scanner) {
        System.out.println("\n--- CONFIGURAÇÃO DE JOGO PERSONALIZADO ---");
        int qtdNos = lerInteiro(scanner, "Quantidade de nós na árvore (entre 3 e 10) [padrão 7]: ", 3, 10);
        int maxErros = lerInteiro(scanner, "Limite máximo de erros na forca (entre 1 e 50) [padrão 6]: ", 1, 50);
        iniciarPartida(scanner, qtdNos, maxErros);
    }

    /**
     * Executa a lógica de uma partida do jogo da forca com árvore binária de busca.
     * Chama os métodos da classe RegrasForca (Pessoa 2) e No/ArvoreBinaria (Pessoa 1).
     *
     * @param scanner   Instância do leitor de teclado
     * @param qtdNos    Quantidade de nós a serem gerados na árvore
     * @param maxErros  Limite de erros antes do enforcamento
     */
    private static void iniciarPartida(Scanner scanner, int qtdNos, int maxErros) {
        RegrasForca regras = new RegrasForca();

        System.out.println("\nGerando árvore binária de busca com " + qtdNos + " valores aleatórios (0 a 50)...");
        regras.gerarArvore(qtdNos);

        Set<Integer> palpitesTentados = new LinkedHashSet<>();
        List<Integer> palpitesCorretos = new ArrayList<>();
        List<Integer> palpitesErrados = new ArrayList<>();

        boolean emJogo = true;
        System.out.println("Árvore gerada com sucesso! A partida começou!");

        while (emJogo) {
            int errosAtuais = palpitesErrados.size();
            int errosRestantes = maxErros - errosAtuais;

            System.out.println("\n--------------------------------------------------------");
            System.out.println("FORCA ATUAL:");
            desenharForca(errosAtuais, maxErros);

            System.out.println("\nESTRUTURA ATUAL DA ÁRVORE (ABB):");
            regras.imprimirArvore();

            System.out.println("PAINEL DO JOGADOR:");
            System.out.println("- Acertos: " + regras.getAcertos() + " de " + regras.getQuantidadeNos() + " nós revelados");
            System.out.println("- Erros: " + errosAtuais + " de " + maxErros + " (Chances restantes: " + errosRestantes + ")");
            System.out.println("- Total de tentativas realizadas: " + regras.getTentativas());

            System.out.println("AÇÕES DA RODADA:");
            System.out.println("  [1] Arriscar um número (0 a 50)");
            System.out.println("  [2] Desistir da partida (revelar árvore completa)");
            System.out.println("  [0] Abandonar partida e voltar ao Menu");

            int acao = lerOpcaoMenu(scanner, "Escolha sua ação: ", 0, 1, 2);

            switch (acao) {
                case 1:
                    int palpite = lerInteiro(scanner, "Digite seu palpite (0 a 50): ", 0, 50);

                    if (palpitesTentados.contains(palpite)) {
                        System.out.println("\n>> ATENÇÃO: Você já arriscou o número " + palpite + "! Tente outro número diferente.");
                        break;
                    }

                    palpitesTentados.add(palpite);
                    boolean acertou = regras.verificarPalpite(palpite);

                    if (acertou) {
                        palpitesCorretos.add(palpite);
                        System.out.println("\n========================================================");
                        System.out.println(">> ACERTOU! O número " + palpite + " está na árvore e foi revelado!");
                        System.out.println("========================================================");

                        if (regras.verificarVitoria()) {
                            exibirVitoria(regras, palpitesCorretos, palpitesErrados);
                            emJogo = false;
                        }
                    } else {
                        if (palpitesErrados.size() >= maxErros) { 
                            exibirDerrota(regras, maxErros, palpitesCorretos, palpitesErrados);
                            emJogo = false;
                        }
                        palpitesErrados.add(palpite);
                        System.out.println("\n========================================================");
                        System.out.println(">> ERROU! O número " + palpite + " NÃO pertence à árvore.");
                        System.out.println("========================================================");

                        if (palpitesErrados.size() >= maxErros) {
                            exibirDerrota(regras, maxErros, palpitesCorretos, palpitesErrados);
                            emJogo = false;
                        }
                    }
                    break;

                case 2:
                    System.out.print("Deseja realmente desistir desta partida? (S/N): ");
                    String confirmacao = scanner.nextLine().trim().toUpperCase();
                    if (confirmacao.equals("S") || confirmacao.equals("SIM")) {
                        System.out.println("\nPartida encerrada por desistência.");
                        System.out.println("Revelando a árvore completa...");
                        regras.revelarTudo();
                        regras.imprimirArvore();
                        emJogo = false;
                    }
                    break;

                case 0:
                    System.out.println("\nPartida cancelada. Retornando ao menu principal...");
                    emJogo = false;
                    break;
            }
        }

        pressionarEnterParaContinuar(scanner);
    }

    /**
     * Exibe o banner de vitória e estatísticas da partida.
     */
    private static void exibirVitoria(RegrasForca regras, List<Integer> corretos, List<Integer> errados) {
        System.out.println("\n********************************************************");
        System.out.println("      PARABÉNS! VOCÊ VENCEU O JOGO DA FORCA DA ABB!     ");
        System.out.println("********************************************************");
        System.out.println("Você desvendou todos os nós da árvore com sucesso!\n");

        System.out.println("ÁRVORE COMPLETA FINAL:");
        regras.imprimirArvore();

        int totalTentativas = regras.getTentativas();
        int totalAcertos = regras.getAcertos();
        int totalErros = errados.size();
        double precisao = totalTentativas > 0 ? ((double) totalAcertos / totalTentativas) * 100.0 : 0.0;

        System.out.println("ESTATÍSTICAS DA PARTIDA:");
        System.out.println("- Total de nós na árvore: " + regras.getQuantidadeNos());
        System.out.println("- Total de acertos: " + totalAcertos);
        System.out.println("- Total de erros cometidos: " + totalErros);
        System.out.println("- Tentativas totais: " + totalTentativas);
        System.out.printf("- Precisão dos palpites: %.1f%%\n", precisao);
        System.out.println("********************************************************");
    }

    /**
     * Exibe o banner de derrota, enforcamento e revela a árvore completa.
     */
    private static void exibirDerrota(RegrasForca regras, int maxErros, List<Integer> corretos, List<Integer> errados) {
        System.out.println("\n========================================================");
        System.out.println("               ENFORCADO! FIM DE JOGO!                  ");
        System.out.println("========================================================");
        System.out.println("Você atingiu o limite de " + maxErros + " erros e foi enforcado!\n");

        desenharForca(maxErros, maxErros);

        System.out.println("\nA árvore completa era:");
        regras.revelarTudo();
        regras.imprimirArvore();

        System.out.println("ESTATÍSTICAS DA PARTIDA:");
        System.out.println("- Nós desvendados: " + regras.getAcertos() + " de " + regras.getQuantidadeNos());
        System.out.println("- Total de erros: " + errados.size());
        System.out.println("- Total de tentativas: " + regras.getTentativas());
        System.out.println("========================================================");
    }





    /**
     * Desenha a forca em arte ASCII de acordo com a proporção de erros cometidos.
     *
     * @param erros    Número de erros cometidos até o momento
     * @param maxErros Limite máximo de erros configurado
     */
    private static void desenharForca(int erros, int maxErros) {
        int estagio = 0;
        if (maxErros == 6) {
            estagio = Math.min(erros, 6);
        } else {
            estagio = (int) Math.round(((double) erros / maxErros) * 6.0);
            if (erros > 0 && estagio == 0) estagio = 1;
            if (erros >= maxErros) estagio = 6;
        }

        System.out.println("  +---+");
        System.out.println("  |   |");

        switch (estagio) {
            case 0:
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                break;
            case 1:
                System.out.println("  O   |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                break;
            case 2:
                System.out.println("  O   |");
                System.out.println("  |   |");
                System.out.println("      |");
                System.out.println("      |");
                break;
            case 3:
                System.out.println("  O   |");
                System.out.println(" /|   |");
                System.out.println("      |");
                System.out.println("      |");
                break;
            case 4:
                System.out.println("  O   |");
                System.out.println(" /|\\  |");
                System.out.println("      |");
                System.out.println("      |");
                break;
            case 5:
                System.out.println("  O   |");
                System.out.println(" /|\\  |");
                System.out.println(" /    |");
                System.out.println("      |");
                break;
            case 6:
            default:
                System.out.println("  O   |");
                System.out.println(" /|\\  |");
                System.out.println(" / \\  |");
                System.out.println("      |");
                break;
        }
        System.out.println("=========");
    }

    /**
     * Lê com segurança uma opção válida de um menu de opções fornecidas.
     */
    private static int lerOpcaoMenu(Scanner scanner, String prompt, int... opcoesValidas) {
        Set<Integer> validas = new HashSet<>();
        for (int op : opcoesValidas) {
            validas.add(op);
        }

        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Entrada vazia! Por favor, digite uma opção.");
                continue;
            }
            try {
                int opcao = Integer.parseInt(entrada);
                if (validas.contains(opcao)) {
                    return opcao;
                }
                System.out.println("Opção inválida! Escolha uma das opções listadas.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas o número correspondente à opção desejada.");
            }
        }
    }

    /**
     * Lê com segurança um número inteiro dentro de uma faixa [min, max],
     * tratando entradas vazias e NumberFormatException.
     */
    private static int lerInteiro(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Entrada vazia! Por favor, digite um número.");
                continue;
            }
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < min || valor > max) {
                    System.out.println("Valor fora do intervalo permitido! Digite um valor entre " + min + " e " + max + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! '" + entrada + "' não é um número inteiro válido.");
            }
        }
    }

    /**
     * Pausa a execução até o usuário pressionar Enter, facilitando a leitura de telas.
     */
    private static void pressionarEnterParaContinuar(Scanner scanner) {
        System.out.print("\nPressione [ENTER] para continuar...");
        scanner.nextLine();
    }
}
