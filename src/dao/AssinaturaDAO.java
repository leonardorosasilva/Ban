package dao;

import beans.AssinaturaBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaDAO {
    private Connection connection;

    public AssinaturaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AssinaturaBean assinatura) throws SQLException {
        String sql = "INSERT INTO assinatura (id_usuario, id_plano, duracao, data_inicio, data_fim, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, assinatura.getIdUsuario());
            stmt.setInt(2, assinatura.getIdPlano());
            stmt.setInt(3, assinatura.getDuracao());
            stmt.setDate(4, new java.sql.Date(assinatura.getDataInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(assinatura.getDataFim().getTime()));
            stmt.setString(6, assinatura.getStatus());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    assinatura.setIdAssinatura(rs.getInt(1));
                }
            }
        }
    }

    public AssinaturaBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM assinatura WHERE id_assinatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AssinaturaBean assinatura = new AssinaturaBean();
                    assinatura.setIdAssinatura(rs.getInt("id_assinatura"));
                    assinatura.setIdUsuario(rs.getInt("id_usuario"));
                    assinatura.setIdPlano(rs.getInt("id_plano"));
                    assinatura.setDuracao(rs.getInt("duracao"));
                    assinatura.setDataInicio(rs.getDate("data_inicio"));
                    assinatura.setDataFim(rs.getDate("data_fim"));
                    assinatura.setStatus(rs.getString("status"));
                    return assinatura;
                }
            }
        }
        return null;
    }

    public List<AssinaturaBean> buscarPorUsuario(int idUsuario) throws SQLException {
        List<AssinaturaBean> assinaturas = new ArrayList<>();
        String sql = "SELECT * FROM assinatura WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AssinaturaBean assinatura = new AssinaturaBean();
                    assinatura.setIdAssinatura(rs.getInt("id_assinatura"));
                    assinatura.setIdUsuario(rs.getInt("id_usuario"));
                    assinatura.setIdPlano(rs.getInt("id_plano"));
                    assinatura.setDuracao(rs.getInt("duracao"));
                    assinatura.setDataInicio(rs.getDate("data_inicio"));
                    assinatura.setDataFim(rs.getDate("data_fim"));
                    assinatura.setStatus(rs.getString("status"));
                    assinaturas.add(assinatura);
                }
            }
        }
        return assinaturas;
    }

    public List<AssinaturaBean> listarTodos() throws SQLException {
        List<AssinaturaBean> assinaturas = new ArrayList<>();
        String sql = "SELECT * FROM assinatura";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AssinaturaBean assinatura = new AssinaturaBean();
                assinatura.setIdAssinatura(rs.getInt("id_assinatura"));
                assinatura.setIdUsuario(rs.getInt("id_usuario"));
                assinatura.setIdPlano(rs.getInt("id_plano"));
                assinatura.setDuracao(rs.getInt("duracao"));
                assinatura.setDataInicio(rs.getDate("data_inicio"));
                assinatura.setDataFim(rs.getDate("data_fim"));
                assinatura.setStatus(rs.getString("status"));
                assinaturas.add(assinatura);
            }
        }
        return assinaturas;
    }

    public void atualizar(AssinaturaBean assinatura) throws SQLException {
        String sql = "UPDATE assinatura SET id_plano = ?, duracao = ?, data_inicio = ?, data_fim = ?, status = ? WHERE id_assinatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, assinatura.getIdPlano());
            stmt.setInt(2, assinatura.getDuracao());
            stmt.setDate(3, new java.sql.Date(assinatura.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(assinatura.getDataFim().getTime()));
            stmt.setString(5, assinatura.getStatus());
            stmt.setInt(6, assinatura.getIdAssinatura());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM assinatura WHERE id_assinatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

