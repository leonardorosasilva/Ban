package beans;

public class DocumentarioBean extends ConteudoBean {
    private int idConteudo;

    @Override
    public int getIdConteudo() {
        return idConteudo;
    }

    @Override
    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }
}

