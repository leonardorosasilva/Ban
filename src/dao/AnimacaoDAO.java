package dao;

import beans.AnimacaoBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimacaoDAO {
    private Connection connection;

    public AnimacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AnimacaoBean animacao) throws SQLException {
        String sql = "INSERT INTO animacao (id_conteudo, studio) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, animacao.getIdConteudo());
            stmt.setString(2, animacao.getStudio());
            stmt.executeUpdate();
        }
    }

    public AnimacaoBean buscarPorId(int idConteudo) throws SQLException {
        String sql = "SELECT * FROM animacao WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AnimacaoBean animacao = new AnimacaoBean();
                    animacao.setIdConteudo(idConteudo);
                    animacao.setStudio(rs.getString("studio"));
                    return animacao;
                }
            }
        }
        return null;
    }

    public List<AnimacaoBean> listarTodos() throws SQLException {
        List<AnimacaoBean> animacoes = new ArrayList<>();
        String sql = "SELECT * FROM animacao";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AnimacaoBean animacao = new AnimacaoBean();
                animacao.setIdConteudo(rs.getInt("id_conteudo"));
                animacao.setStudio(rs.getString("studio"));
                animacoes.add(animacao);
            }
        }
        return animacoes;
    }

    public void atualizar(AnimacaoBean animacao) throws SQLException {
        String sql = "UPDATE animacao SET studio = ? WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animacao.getStudio());
            stmt.setInt(2, animacao.getIdConteudo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idConteudo) throws SQLException {
        String sql = "DELETE FROM animacao WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            stmt.executeUpdate();
        }
    }
}

