
import java.text.DecimalFormat;
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

        if (cpf.length() != 11) {
            System.out.println();
            System.out.println("CPF inválido.");
            pausar();
            return;
        }

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

        double saldoInicial = 0;
        while (true) {
            System.out.print("Saldo inicial (opcional; ENTER = R$ 0,00): R$ ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) break;
            if (!entrada.matches("[0-9]+([,.][0-9]{1,2})?")) {
                System.out.println("Informe zero ou um valor positivo com até duas casas decimais, sem separador de milhar.");
                continue;
            }
            try {
                saldoInicial = Double.parseDouble(entrada.replace(',', '.'));
                if (!Double.isFinite(saldoInicial)) {
                    System.out.println("Valor muito grande. Informe outro valor.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Exemplos: 100 ou 100,50.");
            }
        }
        Cliente novoCliente = new Cliente(nome, cpf, senha, saldoInicial);

        clientes.add(novoCliente);

        System.out.println();
        System.out.println("==============================================");
        System.out.println("       CONTA CRIADA COM SUCESSO!");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("Cliente: " + nome);
        System.out.printf("Saldo inicial: R$ %.2f%n", saldoInicial);

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
            System.out.println("7 - Integrantes");
            System.out.println("8 - Sair da conta");
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
                    integrantes();
                    break;

                case "8":
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

        if (valor <= 0) {
            System.out.println();
            System.out.println("O valor do saque deve ser maior que zero.");
            pausar();
            return;
        }

        if (valor % 1 != 0) {
            System.out.println();
            System.out.println(
                    "O valor do saque deve ser inteiro, pois o caixa libera apenas notas.");
            pausar();
            return;
        }

        if (valor > cliente.saldo) {
            System.out.println();
            System.out.println("Saldo insuficiente.");
            pausar();
            return;
        }

        int restante = (int) valor;

        int notas100 = restante / 100;
        restante %= 100;

        int notas50 = restante / 50;
        restante %= 50;

        int notas20 = restante / 20;
        restante %= 20;

        int notas10 = restante / 10;
        restante %= 10;

        int notas5 = 0;
        int notas2 = 0;

        if (restante % 2 == 0) {

            notas2 = restante / 2;

        } else if (restante >= 5 && (restante - 5) % 2 == 0) {

            notas5 = 1;
            notas2 = (restante - 5) / 2;

        } else {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("             SAQUE NÃO REALIZADO");
            System.out.println("==============================================");
            System.out.println();
            System.out.println("Não é possível realizar o saque.");
            System.out.println("O valor solicitado não pode ser formado");
            System.out.println("com as notas disponíveis:");
            System.out.println("R$ 100, R$ 50, R$ 20, R$ 10, R$ 5 e R$ 2.");

            pausar();
            return;
        }

        cliente.saldo -= valor;

        cliente.adicionarExtrato(
                "Saque",
                -valor);

        System.out.println();
        System.out.println("Notas liberadas:");

        if (notas100 > 0) {
            System.out.println(
                    "R$ 100,00: " + notas100 + " nota(s)");
        }

        if (notas50 > 0) {
            System.out.println(
                    "R$ 50,00: " + notas50 + " nota(s)");
        }

        if (notas20 > 0) {
            System.out.println(
                    "R$ 20,00: " + notas20 + " nota(s)");
        }

        if (notas10 > 0) {
            System.out.println(
                    "R$ 10,00: " + notas10 + " nota(s)");
        }

        if (notas5 > 0) {
            System.out.println(
                    "R$ 5,00: " + notas5 + " nota(s)");
        }

        if (notas2 > 0) {
            System.out.println(
                    "R$ 2,00: " + notas2 + " nota(s)");
        }

        System.out.printf(
                "%nSaque realizado!%nSaldo atual: R$ %.2f%n",
                cliente.saldo);

    } catch (NumberFormatException e) {

        System.out.println();
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
        System.out.println("Nome do cliente: " + cliente.nome);
        System.out.println("CPF: " + cliente.cpf);
        System.out.printf("Saldo inicial: R$ %.2f%n", cliente.saldoInicial);
        System.out.printf("Saldo atual: R$ %.2f%n", cliente.saldo);
        System.out.println("----------------------------------------------");
        System.out.println("Depósitos: quantidade = " + cliente.quantidadeDepositos);
        System.out.printf("Depósitos: valor total = R$ %.2f%n", cliente.totalDepositos);
        System.out.println("Saques: quantidade = " + cliente.quantidadeSaques);
        System.out.printf("Saques: valor total = R$ %.2f%n", cliente.totalSaques);
        System.out.printf("Total dos juros recebidos: R$ %.2f%n", cliente.totalJurosRecebidos);
        System.out.printf("Saldo mínimo da conta: R$ %.2f%n", cliente.saldoMinimo);
        System.out.printf("Saldo máximo da conta: R$ %.2f%n", cliente.saldoMaximo);
        System.out.println("----------------------------------------------");
        System.out.println("HISTÓRICO DE MOVIMENTAÇÕES");
        for (String movimentacao : cliente.extrato) {
            System.out.println(movimentacao);
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

        System.out.print("Digite a porcentagem (%) de juros a ser aplicada (deve ser maior que 0):");

        Double juros = scanner.nextDouble();
        scanner.nextLine();

        if (juros < 0) {
            System.out.println("o valor deve ser maior do que zero");
            System.out.println();
            pausar();
            return;
        }

        System.out.printf(
                "Saldo atual: R$ %.2f%n",
                cliente.saldo);

        double rendimento = cliente.saldo * (juros/100);

        System.out.printf(
                "Rendimento de juros: R$ %.2f%n",
                rendimento);

        cliente.saldo += rendimento;

        cliente.adicionarExtrato(
                "Rendimento de" + juros,
                rendimento);

        DecimalFormat df = new DecimalFormat("0.##");
        System.out.println();

        System.out.println("Juros aplicado: " + df.format(juros) + "%");
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

    try {

        System.out.print("Valor a ser emprestado: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        if (valor <= 0) {
            System.out.println("O valor deve ser maior que zero.");
            pausar();
            return;
        }

        System.out.print("Taxa de juros mensal (%): ");
        double taxa = Double.parseDouble(scanner.nextLine());

        if (taxa <= 0) {
            System.out.println("A taxa de juros deve ser maior que zero.");
            pausar();
            return;
        }

        System.out.print("Quantidade de parcelas: ");
        int parcelas = Integer.parseInt(scanner.nextLine());

        if (parcelas <= 0) {
            System.out.println("A quantidade de parcelas deve ser maior que zero.");
            pausar();
            return;
        }

        double juros = valor * (taxa / 100) * parcelas;
        double total = valor + juros;
        double valorParcela = total / parcelas;

        System.out.println();
        System.out.println("==============================================");
        System.out.println("          SIMULAÇÃO DE EMPRÉSTIMO");
        System.out.println("==============================================");

        System.out.printf("Valor emprestado: R$ %.2f%n", valor);
        System.out.printf("Taxa mensal: %.2f%%%n", taxa);
        System.out.printf("Quantidade de parcelas: %d%n", parcelas);
        System.out.printf("Valor de cada parcela: R$ %.2f%n", valorParcela);
        System.out.printf("Total de juros: R$ %.2f%n", juros);
        System.out.printf("Total a pagar: R$ %.2f%n", total);

        System.out.println();
        System.out.println("Simulação realizada com sucesso.");
        System.out.println("O valor do empréstimo não foi adicionado ao saldo.");

    } catch (NumberFormatException e) {

        System.out.println("Digite valores numéricos válidos.");
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

     static void integrantes() {

        System.out.println("====== INTEGRANTES ======");
        System.out.println();
        System.out.println("Maria Clara Siqueira, Nicolas Renan da Silva Jablonski, Henry de Lima Coitinho, Jennifer Brandalize Rodrigues");

        pausar();
    }

    // =====================================================
    // CLASSE CLIENTE
    // =====================================================

    static class Cliente {

        String nome;
        String cpf;
        String senha;
        double saldo;
        final double saldoInicial;
        double saldoMinimo;
        double saldoMaximo;
        int quantidadeDepositos;
        int quantidadeSaques;
        double totalDepositos;
        double totalSaques;
        double totalJurosRecebidos;

        ArrayList<String> extrato = new ArrayList<>();

        Cliente(String nome, String cpf, String senha, double saldoInicial) {

            this.nome = nome;
            this.cpf = cpf;
            this.senha = senha;
            this.saldoInicial = saldoInicial;
            this.saldo = saldoInicial;
            this.saldoMinimo = saldoInicial;
            this.saldoMaximo = saldoInicial;
            // Abertura não é contada como depósito.
            adicionarExtrato("Saldo inicial", saldoInicial);
        }

        void adicionarExtrato(String operacao, double valor) {
            if (operacao.equals("Depósito")) {
                quantidadeDepositos++;
                totalDepositos += valor;
            } else if (operacao.equals("Saque")) {
                quantidadeSaques++;
                totalSaques += Math.abs(valor);
            } else if (operacao.startsWith("Rendimento")) {
                totalJurosRecebidos += valor;
            }
            // Cada operação original altera o saldo antes de registrar o extrato.
            saldoMinimo = Math.min(saldoMinimo, saldo);
            saldoMaximo = Math.max(saldoMaximo, saldo);

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
                    + String.format("R$ %.2f", valor)
                    + String.format(" | Saldo: R$ %.2f", saldo);

            extrato.add(movimentacao);
        }
    }
}
