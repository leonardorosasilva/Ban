package beans;

public class FilmeBean extends ConteudoBean {
    private int idConteudo;
    private String diretor;
    private Double duracao;
    private String roteirista;

    @Override
    public int getIdConteudo() {
        return idConteudo;
    }

    @Override
    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public String getRoteirista() {
        return roteirista;
    }

    public void setRoteirista(String roteirista) {
        this.roteirista = roteirista;
    }
}
