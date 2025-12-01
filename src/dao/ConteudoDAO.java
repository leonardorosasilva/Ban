package dao;

import beans.ConteudoBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConteudoDAO {
    private Connection connection;

    public ConteudoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(ConteudoBean conteudo) throws SQLException {
        String sql = "INSERT INTO conteudo (titulo, ano_lancamento, classificacao_etaria, sinopse, id_usuario, data_cadastro) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setInt(2, conteudo.getAnoLancamento());
            stmt.setString(3, conteudo.getClassificacaoEtaria());
            stmt.setString(4, conteudo.getSinopse());
            stmt.setInt(5, conteudo.getIdUsuario());
            stmt.setDate(6, new java.sql.Date(conteudo.getData_cadastro().getTime()));
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    conteudo.setIdConteudo(rs.getInt(1));
                }
            }
        }
    }

    public ConteudoBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM conteudo WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ConteudoBean conteudo = new ConteudoBean();
                    conteudo.setIdConteudo(rs.getInt("id_conteudo"));
                    conteudo.setTitulo(rs.getString("titulo"));
                    conteudo.setAnoLancamento(rs.getInt("ano_lancamento"));
                    conteudo.setClassificacaoEtaria(rs.getString("classificacao_etaria"));
                    conteudo.setSinopse(rs.getString("sinopse"));
                    conteudo.setIdUsuario(rs.getInt("id_usuario"));
                    conteudo.setData_cadastro(rs.getDate("data_cadastro"));
                    return conteudo;
                }
            }
        }
        return null;
    }

    public List<ConteudoBean> listarTodos() throws SQLException {
        List<ConteudoBean> conteudos = new ArrayList<>();
        String sql = "SELECT * FROM conteudo";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ConteudoBean conteudo = new ConteudoBean();
                conteudo.setIdConteudo(rs.getInt("id_conteudo"));
                conteudo.setTitulo(rs.getString("titulo"));
                conteudo.setAnoLancamento(rs.getInt("ano_lancamento"));
                conteudo.setClassificacaoEtaria(rs.getString("classificacao_etaria"));
                conteudo.setSinopse(rs.getString("sinopse"));
                conteudo.setIdUsuario(rs.getInt("id_usuario"));
                conteudo.setData_cadastro(rs.getDate("data_cadastro"));
                conteudos.add(conteudo);
            }
        }
        return conteudos;
    }

    public void atualizar(ConteudoBean conteudo) throws SQLException {
        String sql = "UPDATE conteudo SET titulo = ?, ano_lancamento = ?, classificacao_etaria = ?, sinopse = ? WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setInt(2, conteudo.getAnoLancamento());
            stmt.setString(3, conteudo.getClassificacaoEtaria());
            stmt.setString(4, conteudo.getSinopse());
            stmt.setInt(5, conteudo.getIdConteudo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM conteudo WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

