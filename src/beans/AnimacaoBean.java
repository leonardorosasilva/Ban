package beans;

public class AnimacaoBean extends ConteudoBean {
    private int idConteudo;
    private String studio;

    @Override
    public int getIdConteudo() {
        return idConteudo;
    }

    @Override
    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }
}

