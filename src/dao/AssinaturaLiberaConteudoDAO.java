package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaLiberaConteudoDAO {
    private Connection connection;

    public AssinaturaLiberaConteudoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(int idAssinatura, int idConteudo) throws SQLException {
        String sql = "INSERT INTO assinatura_libera_conteudo (id_assinatura, id_conteudo) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAssinatura);
            stmt.setInt(2, idConteudo);
            stmt.executeUpdate();
        }
    }

    public List<Integer> buscarConteudosPorAssinatura(int idAssinatura) throws SQLException {
        List<Integer> conteudos = new ArrayList<>();
        String sql = "SELECT id_conteudo FROM assinatura_libera_conteudo WHERE id_assinatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAssinatura);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    conteudos.add(rs.getInt("id_conteudo"));
                }
            }
        }
        return conteudos;
    }

    public List<Integer> buscarAssinaturasPorConteudo(int idConteudo) throws SQLException {
        List<Integer> assinaturas = new ArrayList<>();
        String sql = "SELECT id_assinatura FROM assinatura_libera_conteudo WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assinaturas.add(rs.getInt("id_assinatura"));
                }
            }
        }
        return assinaturas;
    }

    public void deletar(int idAssinatura, int idConteudo) throws SQLException {
        String sql = "DELETE FROM assinatura_libera_conteudo WHERE id_assinatura = ? AND id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAssinatura);
            stmt.setInt(2, idConteudo);
            stmt.executeUpdate();
        }
    }

    public void deletarPorAssinatura(int idAssinatura) throws SQLException {
        String sql = "DELETE FROM assinatura_libera_conteudo WHERE id_assinatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAssinatura);
            stmt.executeUpdate();
        }
    }

    public void deletarPorConteudo(int idConteudo) throws SQLException {
        String sql = "DELETE FROM assinatura_libera_conteudo WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            stmt.executeUpdate();
        }
    }
}

