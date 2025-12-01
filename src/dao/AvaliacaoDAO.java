package dao;

import beans.AvaliacaoBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private Connection connection;

    public AvaliacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AvaliacaoBean avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacao (id_usuario, id_conteudo, nota, comentario, data_avaliacao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, avaliacao.getIdUsuario());
            stmt.setInt(2, avaliacao.getIdConteudo());
            stmt.setDouble(3, avaliacao.getNota());
            stmt.setString(4, avaliacao.getComentario());
            stmt.setDate(5, new java.sql.Date(avaliacao.getDataAvaliacao().getTime()));
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    avaliacao.setIdAvaliacao(rs.getInt(1));
                }
            }
        }
    }

    public AvaliacaoBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM avaliacao WHERE id_avaliacao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AvaliacaoBean avaliacao = new AvaliacaoBean();
                    avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));
                    avaliacao.setIdUsuario(rs.getInt("id_usuario"));
                    avaliacao.setIdConteudo(rs.getInt("id_conteudo"));
                    avaliacao.setNota(rs.getDouble("nota"));
                    avaliacao.setComentario(rs.getString("comentario"));
                    avaliacao.setDataAvaliacao(rs.getDate("data_avaliacao"));
                    return avaliacao;
                }
            }
        }
        return null;
    }

    public List<AvaliacaoBean> buscarPorConteudo(int idConteudo) throws SQLException {
        List<AvaliacaoBean> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AvaliacaoBean avaliacao = new AvaliacaoBean();
                    avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));
                    avaliacao.setIdUsuario(rs.getInt("id_usuario"));
                    avaliacao.setIdConteudo(rs.getInt("id_conteudo"));
                    avaliacao.setNota(rs.getDouble("nota"));
                    avaliacao.setComentario(rs.getString("comentario"));
                    avaliacao.setDataAvaliacao(rs.getDate("data_avaliacao"));
                    avaliacoes.add(avaliacao);
                }
            }
        }
        return avaliacoes;
    }

    public List<AvaliacaoBean> listarTodos() throws SQLException {
        List<AvaliacaoBean> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AvaliacaoBean avaliacao = new AvaliacaoBean();
                avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));
                avaliacao.setIdUsuario(rs.getInt("id_usuario"));
                avaliacao.setIdConteudo(rs.getInt("id_conteudo"));
                avaliacao.setNota(rs.getDouble("nota"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setDataAvaliacao(rs.getDate("data_avaliacao"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    public void atualizar(AvaliacaoBean avaliacao) throws SQLException {
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ?, data_avaliacao = ? WHERE id_avaliacao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, avaliacao.getNota());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setDate(3, new java.sql.Date(avaliacao.getDataAvaliacao().getTime()));
            stmt.setInt(4, avaliacao.getIdAvaliacao());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

