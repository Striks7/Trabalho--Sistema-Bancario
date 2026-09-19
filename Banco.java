
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Banco {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Cliente> clientes = new ArrayList<>();

    static DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {

        telaInicial();

        while (true) {

            limparTela();

            System.out.println("==============================================");
            System.out.println("                 BANCO MAGIC");
            System.out.println("==============================================");
            System.out.println();
            System.out.println("1 - Login");
            System.out.println("2 - Criar conta");
            System.out.println("3 - Sair");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1":
                    login();
                    break;

                case "2":
                    cadastrarCliente();
                    break;

                case "3":
                    System.out.println();
                    System.out.println("Obrigado por utilizar o Banco Magic!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    pausar();
            }
        }
    }

    // =====================================================
    // TELA INICIAL
    // =====================================================

    static void telaInicial() {

        limparTela();

        System.out.println();
        System.out.println();
        System.out.println("              ███╗   ███╗");
        System.out.println("              ████╗ ████║");
        System.out.println("              ██╔████╔██║");
        System.out.println("              ██║╚██╔╝██║");
        System.out.println("              ██║ ╚═╝ ██║");
        System.out.println();
        System.out.println("             BANCO MAGIC");
        System.out.println();
        System.out.println("==============================================");
        System.out.println();
        System.out.println("       Pressione ENTER para continuar");
        System.out.println();

        scanner.nextLine();
    }

    // =====================================================
    // CADASTRO
    // =====================================================

    static void cadastrarCliente() {

        limparTela();

        System.out.println("==============================================");
        System.out.println("              CRIAR NOVA CONTA");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();

        if (!nome.matches("[a-zA-ZÀ-ÿ ]+")) {
            System.out.println("Nome inválido.");
            pausar();
            return;
        }

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        // Verifica se o CPF tem 11 digitos

        if (cpf.length() != 11) {
            System.out.println();
            System.out.println("CPF inválido.");
            pausar();
            return;
        }

        // Verifica se CPF já existe
        for (Cliente cliente : clientes) {

            if (cliente.cpf.equals(cpf)) {

                System.out.println();
                System.out.println("Já existe uma conta com esse CPF.");
                pausar();
                return;
            }
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, cpf, senha);

        clientes.add(novoCliente);

        System.out.println();
        System.out.println("==============================================");
        System.out.println("       CONTA CRIADA COM SUCESSO!");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("Cliente: " + nome);
        System.out.println("Saldo inicial: R$ 0,00");

        pausar();
    }

    // =====================================================
    // LOGIN
    // =====================================================

    static void login() {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                    LOGIN");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Cliente cliente : clientes) {

            if (cliente.cpf.equals(cpf)
                    && cliente.senha.equals(senha)) {

                System.out.println();
                System.out.println("Login realizado com sucesso!");

                pausar();

                menuConta(cliente);

                return;
            }
        }

        System.out.println();
        System.out.println("CPF ou senha incorretos.");

        pausar();
    }

    // =====================================================
    // MENU DA CONTA
    // =====================================================

    static void menuConta(Cliente cliente) {

        while (true) {

            limparTela();

            System.out.println("==============================================");
            System.out.println("              BANCO MAGIC");
            System.out.println("==============================================");
            System.out.println();
            System.out.println("Olá, " + cliente.nome);
            System.out.println();
            System.out.printf("Saldo: R$ %.2f%n", cliente.saldo);
            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.println("1 - Extrato");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Rendimento");
            System.out.println("6 - Empréstimo");
            System.out.println("7 - Sair da conta");
            System.out.println("----------------------------------------------");
            System.out.println();

            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1":
                    extrato(cliente);
                    break;

                case "2":
                    depositar(cliente);
                    break;

                case "3":
                    sacar(cliente);
                    break;

                case "4":
                    transferir(cliente);
                    break;

                case "5":
                    rendimento(cliente);
                    break;

                case "6":
                    emprestimo(cliente);
                    break;

                case "7":
                    return;

                default:
                    System.out.println("Opção inválida.");
                    pausar();
            }
        }
    }

    // =====================================================
    // DEPÓSITO
    // =====================================================

    static void depositar(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                  DEPÓSITO");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("Valor do depósito: R$ ");

        try {

            double valor = Double.parseDouble(scanner.nextLine());

            if (valor <= 0) {

                System.out.println("Valor inválido.");
                pausar();
                return;
            }

            cliente.saldo += valor;

            cliente.adicionarExtrato(
                    "Depósito",
                    valor);

            System.out.printf(
                    "%nDepósito realizado!%nNovo saldo: R$ %.2f%n",
                    cliente.saldo);

        } catch (Exception e) {

            System.out.println("Digite um valor válido.");
        }

        pausar();
    }

    // =====================================================
    // SAQUE
    // =====================================================

    static void sacar(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                    SAQUE");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("Valor do saque: R$ ");

        try {

            double valor = Double.parseDouble(scanner.nextLine());

            if (valor < 2) {

                System.out.println("O valor mínimo para saque é R$ 2,00.");
                pausar();
                return;
            }

            if (valor > cliente.saldo) {

                System.out.println();
                System.out.println("Saldo insuficiente.");
                pausar();
                return;
            }

            cliente.saldo -= valor;

            cliente.adicionarExtrato(
                    "Saque",
                    -valor);

            System.out.printf(
                    "%nSaque realizado!%nSaldo atual: R$ %.2f%n",
                    cliente.saldo);

        } catch (Exception e) {

            System.out.println("Digite um valor válido.");
        }

        pausar();
    }

    // =====================================================
    // TRANSFERÊNCIA
    // =====================================================

    static void transferir(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                TRANSFERÊNCIA");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("CPF do destinatário: ");
        String cpfDestino = scanner.nextLine();

        Cliente destinatario = null;

        for (Cliente c : clientes) {

            if (c.cpf.equals(cpfDestino)) {

                destinatario = c;
                break;
            }
        }

        if (destinatario == null) {

            System.out.println();
            System.out.println("Destinatário não encontrado.");
            pausar();
            return;
        }

        if (destinatario == cliente) {

            System.out.println();
            System.out.println("Você não pode transferir para sua própria conta.");
            pausar();
            return;
        }

        System.out.print("Valor da transferência: R$ ");

        try {

            double valor = Double.parseDouble(scanner.nextLine());

            if (valor <= 0) {

                System.out.println("Valor inválido.");
                pausar();
                return;
            }

            if (valor > cliente.saldo) {

                System.out.println("Saldo insuficiente.");
                pausar();
                return;
            }

            cliente.saldo -= valor;

            destinatario.saldo += valor;

            cliente.adicionarExtrato(
                    "Transferência para " + destinatario.nome,
                    -valor);

            destinatario.adicionarExtrato(
                    "Transferência de " + cliente.nome,
                    valor);

            System.out.println();
            System.out.println("Transferência realizada com sucesso!");
            System.out.println();
            System.out.println("Destinatário: " + destinatario.nome);
            System.out.printf("Valor: R$ %.2f%n", valor);

        } catch (Exception e) {

            System.out.println("Digite um valor válido.");
        }

        pausar();
    }

    // =====================================================
    // EXTRATO
    // =====================================================

    static void extrato(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                    EXTRATO");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("CPF: " + cliente.cpf);
        System.out.printf("Saldo atual: R$ %.2f%n", cliente.saldo);
        System.out.println();
        System.out.println("----------------------------------------------");

        if (cliente.extrato.isEmpty()) {

            System.out.println("Nenhuma movimentação encontrada.");

        } else {

            for (String movimentacao : cliente.extrato) {

                System.out.println(movimentacao);
            }
        }

        System.out.println("----------------------------------------------");

        pausar();
    }

    // =====================================================
    // RENDIMENTO
    // =====================================================

    static void rendimento(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                 RENDIMENTO");
        System.out.println("==============================================");
        System.out.println();

        System.out.printf(
                "Saldo atual: R$ %.2f%n",
                cliente.saldo);

        // Exemplo didático: rendimento de 1% sobre o saldo
        double rendimento = cliente.saldo * 0.01;

        System.out.printf(
                "Rendimento de 1%%: R$ %.2f%n",
                rendimento);

        cliente.saldo += rendimento;

        cliente.adicionarExtrato(
                "Rendimento de 1%",
                rendimento);

        System.out.println();
        System.out.printf(
                "Novo saldo: R$ %.2f%n",
                cliente.saldo);

        pausar();
    }

    // =====================================================
    // EMPRÉSTIMO
    // =====================================================

    static void emprestimo(Cliente cliente) {

        limparTela();

        System.out.println("==============================================");
        System.out.println("                 EMPRÉSTIMO");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("Valor desejado: R$ ");

        try {

            double valor = Double.parseDouble(scanner.nextLine());

            if (valor <= 0) {

                System.out.println("Valor inválido.");
                pausar();
                return;
            }

            System.out.println();
            System.out.println("Analisando solicitação...");

            // Regra simples apenas para o projeto
            if (valor > 50000) {

                System.out.println();
                System.out.println("Solicitação não aprovada.");
                System.out.println("Limite máximo: R$ 50.000,00");

                pausar();
                return;
            }

            cliente.saldo += valor;

            cliente.adicionarExtrato(
                    "Empréstimo recebido",
                    valor);

            System.out.println();
            System.out.println("==============================================");
            System.out.println("       EMPRÉSTIMO APROVADO!");
            System.out.println("==============================================");
            System.out.printf(
                    "Valor recebido: R$ %.2f%n",
                    valor);
            System.out.printf(
                    "Novo saldo: R$ %.2f%n",
                    cliente.saldo);

        } catch (Exception e) {

            System.out.println("Digite um valor válido.");
        }

        pausar();
    }

    // =====================================================
    // LIMPAR TELA
    // =====================================================

    static void limparTela() {

        for (int i = 0; i < 30; i++) {

            System.out.println();
        }
    }

    // =====================================================
    // PAUSAR
    // =====================================================

    static void pausar() {

        System.out.println();
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    // =====================================================
    // CLASSE CLIENTE
    // =====================================================

    static class Cliente {

        String nome;
        String cpf;
        String senha;
        double saldo;

        ArrayList<String> extrato = new ArrayList<>();

        Cliente(String nome, String cpf, String senha) {

            this.nome = nome;
            this.cpf = cpf;
            this.senha = senha;
            this.saldo = 0;
        }

        void adicionarExtrato(String operacao, double valor) {

            String sinal;

            if (valor >= 0) {

                sinal = "+";

            } else {

                sinal = "";
            }

            String movimentacao = LocalDateTime.now().format(formatoData)
                    + " | "
                    + operacao
                    + " | "
                    + sinal
                    + String.format("R$ %.2f", valor);

            extrato.add(movimentacao);
        }
    }
}
