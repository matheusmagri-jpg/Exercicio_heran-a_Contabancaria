import java.util.ArrayList;
import java.util.Scanner;

public class Contas {
    private static final ArrayList<ContaBancaria> contas = new ArrayList<>();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== BANCO =====");
            System.out.println("1 - Incluir conta de um cliente");
            System.out.println("2 - Sacar");
            System.out.println("3 - Depositar");
            System.out.println("4 - Mostrar novo saldo (contas poupança)");
            System.out.println("5 - Mostrar dados das contas de um cliente");
            System.out.println("0 - Sair");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1: incluirConta(); break;
                case 2: sacar(); break;
                case 3: depositar(); break;
                case 4: mostrarNovoSaldoPoupanca(); break;
                case 5: mostrarDados(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // a) Incluir dados relativos à(s) conta(s) de um cliente
    private static void incluirConta() {
        String cliente = lerTexto("Cliente: ");
        int numero = lerInt("Número da conta: ");
        if (buscarConta(numero) != null) {
            System.out.println("Já existe uma conta com esse número.");
            return;
        }
        float saldo = lerFloat("Saldo inicial: R$ ");
        if (saldo < 0) {
            System.out.println("O saldo inicial não pode ser negativo.");
            return;
        }
        int tipo = lerInt("Tipo (1 - Corrente, 2 - Poupança, 3 - Especial): ");
        switch (tipo) {
            case 1:
                contas.add(new ContaBancaria(cliente, numero, saldo));
                break;
            case 2:
                int dia = lerInt("Dia de rendimento: ");
                contas.add(new ContaPoupanca(cliente, numero, saldo, dia));
                break;
            case 3:
                float limite = lerFloat("Limite: R$ ");
                contas.add(new ContaEspecial(cliente, numero, saldo, limite));
                break;
            default:
                System.out.println("Tipo inválido.");
                return;
        }
        System.out.println("Conta incluída com sucesso.");
    }

    // b) Sacar um determinado valor de uma conta do cliente
    private static void sacar() {
        ContaBancaria conta = escolherConta();
        if (conta == null) return;
        float valor = lerFloat("Valor do saque: R$ ");
        if (conta.sacar(valor)) {
            System.out.printf("Saque realizado. Novo saldo: R$ %.2f%n", conta.getSaldo());
        } else {
            System.out.println("Saque não realizado: valor inválido ou saldo insuficiente.");
        }
    }

    // c) Depositar um determinado valor em uma conta do cliente
    private static void depositar() {
        ContaBancaria conta = escolherConta();
        if (conta == null) return;
        float valor = lerFloat("Valor do depósito: R$ ");
        if (conta.depositar(valor)) {
            System.out.printf("Depósito realizado. Novo saldo: R$ %.2f%n", conta.getSaldo());
        } else {
            System.out.println("Depósito não realizado: valor inválido.");
        }
    }

    // d) Mostrar o novo saldo a partir da taxa de rendimento (contas poupança)
    private static void mostrarNovoSaldoPoupanca() {
        String cliente = lerTexto("Cliente: ");
        float taxa = lerFloat("Taxa de rendimento (%): ");
        boolean achou = false;
        for (ContaBancaria c : contas) {
            if (c.getCliente().equalsIgnoreCase(cliente) && c instanceof ContaPoupanca) {
                ContaPoupanca p = (ContaPoupanca) c;
                p.calcularNovoSaldo(taxa);
                System.out.printf("Conta %d - novo saldo: R$ %.2f%n", p.getNum_conta(), p.getSaldo());
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Esse cliente não possui conta poupança.");
        }
    }

    // e) Mostrar os dados da(s) conta(s) de um cliente
    private static void mostrarDados() {
        String cliente = lerTexto("Cliente: ");
        boolean achou = false;
        for (ContaBancaria c : contas) {
            if (c.getCliente().equalsIgnoreCase(cliente)) {
                System.out.println("-------------------------------");
                c.dados();
                achou = true;
            }
        }
        if (achou) {
            System.out.println("-------------------------------");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    // ---------- auxiliares ----------

    private static ContaBancaria buscarConta(int numero) {
        for (ContaBancaria c : contas) {
            if (c.getNum_conta() == numero) return c;
        }
        return null;
    }

    // Pede o cliente e o número da conta, validando se a conta pertence a ele
    private static ContaBancaria escolherConta() {
        String cliente = lerTexto("Cliente: ");
        ArrayList<ContaBancaria> doCliente = new ArrayList<>();
        for (ContaBancaria c : contas) {
            if (c.getCliente().equalsIgnoreCase(cliente)) doCliente.add(c);
        }
        if (doCliente.isEmpty()) {
            System.out.println("Cliente não encontrado.");
            return null;
        }
        System.out.println("Contas do cliente:");
        for (ContaBancaria c : doCliente) {
            System.out.printf("  %d - %s (saldo R$ %.2f)%n", c.getNum_conta(), c.tipo(), c.getSaldo());
        }
        int numero = lerInt("Número da conta: ");
        for (ContaBancaria c : doCliente) {
            if (c.getNum_conta() == numero) return c;
        }
        System.out.println("Conta não encontrada para esse cliente.");
        return null;
    }

    private static String lerTexto(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, tente novamente.");
            }
        }
    }

    private static float lerFloat(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Float.parseFloat(in.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, tente novamente.");
            }
        }
    }
}
