public class ContaBancaria {
    private String cliente;
    private int num_conta;
    protected float saldo;

    public ContaBancaria(String cliente, int num_conta, float saldo) {
        this.cliente = cliente;
        this.num_conta = num_conta;
        this.saldo = saldo;
    }

    public String getCliente() { return cliente; }
    public int getNum_conta() { return num_conta; }
    public float getSaldo() { return saldo; }

    // O saldo não pode ficar negativo. Retorna true se o saque foi realizado.
    public boolean sacar(float valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    // Retorna true se o depósito foi realizado.
    public boolean depositar(float valor) {
        if (valor > 0) {
            saldo += valor;
            return true;
        }
        return false;
    }

    public String tipo() { return "Conta Bancária"; }

    public void dados() {
        System.out.println("Tipo: " + tipo());
        System.out.println("Cliente: " + cliente);
        System.out.println("Conta: " + num_conta);
        System.out.printf("Saldo: R$ %.2f%n", saldo);
    }
}
