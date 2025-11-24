package beans;

public class PlanoBean {
    private int idPlano;
    private String nome;
    private Double preco;
    private Integer limiteTelas;
    private Integer periodicidadeDias;

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getLimiteTelas() {
        return limiteTelas;
    }

    public void setLimiteTelas(Integer limiteTelas) {
        this.limiteTelas = limiteTelas;
    }

    public Integer getPeriodicidadeDias() {
        return periodicidadeDias;
    }

    public void setPeriodicidadeDias(Integer periodicidadeDias) {
        this.periodicidadeDias = periodicidadeDias;
    }
}
