public class ContaPoupanca extends ContaBancaria {
    private int diaRendimento;

    public ContaPoupanca(String cliente, int num_conta, float saldo, int diaRendimento) {
        super(cliente, num_conta, saldo);
        this.diaRendimento = diaRendimento;
    }

    public int getDiaRendimento() { return diaRendimento; }

    // Recebe a taxa de rendimento (em %, ex.: 0.5 = 0,5%) e atualiza o saldo.
    public void calcularNovoSaldo(float taxa) {
        saldo += saldo * taxa / 100;
    }

    @Override
    public String tipo() { return "Conta Poupança"; }

    @Override
    public void dados() {
        super.dados();
        System.out.println("Dia de rendimento: " + diaRendimento);
    }
}
