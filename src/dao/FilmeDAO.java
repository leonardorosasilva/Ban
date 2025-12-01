package dao;

import beans.FilmeBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    private Connection connection;

    public FilmeDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(FilmeBean filme) throws SQLException {
        String sql = "INSERT INTO filme (id_conteudo, diretor, roteirista) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, filme.getIdConteudo());
            stmt.setString(2, filme.getDiretor());
            stmt.setString(3, filme.getRoteirista());
            stmt.executeUpdate();
        }
    }

    public FilmeBean buscarPorId(int idConteudo) throws SQLException {
        String sql = "SELECT * FROM filme WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FilmeBean filme = new FilmeBean();
                    filme.setIdConteudo(idConteudo);
                    filme.setDiretor(rs.getString("diretor"));
                    filme.setRoteirista(rs.getString("roteirista"));
                    return filme;
                }
            }
        }
        return null;
    }

    public List<FilmeBean> listarTodos() throws SQLException {
        List<FilmeBean> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filme";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FilmeBean filme = new FilmeBean();
                filme.setIdConteudo(rs.getInt("id_conteudo"));
                filme.setDiretor(rs.getString("diretor"));
                filme.setRoteirista(rs.getString("roteirista"));
                filmes.add(filme);
            }
        }
        return filmes;
    }

    public void atualizar(FilmeBean filme) throws SQLException {
        String sql = "UPDATE filme SET diretor = ?, roteirista = ? WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, filme.getDiretor());
            stmt.setString(2, filme.getRoteirista());
            stmt.setInt(3, filme.getIdConteudo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idConteudo) throws SQLException {
        String sql = "DELETE FROM filme WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            stmt.executeUpdate();
        }
    }
}

