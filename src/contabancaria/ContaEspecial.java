public class ContaEspecial extends ContaBancaria {
    private float limite;

    public ContaEspecial(String cliente, int num_conta, float saldo, float limite) {
        super(cliente, num_conta, saldo);
        this.limite = limite;
    }

    public float getLimite() { return limite; }

    // Permite saldo negativo até o valor do limite.
    @Override
    public boolean sacar(float valor) {
        if (valor > 0 && valor <= saldo + limite) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public String tipo() { return "Conta Especial"; }

    @Override
    public void dados() {
        super.dados();
        System.out.printf("Limite: R$ %.2f%n", limite);
    }
}
