package beans;

public class SerieBean extends ConteudoBean {
    private int idConteudo;
    private int qtdTemporadas;

    @Override
    public int getIdConteudo() {
        return idConteudo;
    }

    @Override
    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }

    public int getQtdTemporadas() {
        return qtdTemporadas;
    }

    public void setQtdTemporadas(int qtdTemporadas) {
        this.qtdTemporadas = qtdTemporadas;
    }
}

