package dao;

import beans.PlanoBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
    private Connection connection;

    public PlanoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(PlanoBean plano) throws SQLException {
        String sql = "INSERT INTO plano (nome, preco, limite_telas, periodicidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getPreco());
            stmt.setInt(3, plano.getLimiteTelas());
            stmt.setString(4, plano.getPeriodicidade());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    plano.setIdPlano(rs.getInt(1));
                }
            }
        }
    }

    public PlanoBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM plano WHERE id_plano = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PlanoBean plano = new PlanoBean();
                    plano.setIdPlano(rs.getInt("id_plano"));
                    plano.setNome(rs.getString("nome"));
                    plano.setPreco(rs.getDouble("preco"));
                    plano.setLimiteTelas(rs.getInt("limite_telas"));
                    plano.setPeriodicidade(rs.getString("periodicidade"));
                    return plano;
                }
            }
        }
        return null;
    }

    public List<PlanoBean> listarTodos() throws SQLException {
        List<PlanoBean> planos = new ArrayList<>();
        String sql = "SELECT * FROM plano";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PlanoBean plano = new PlanoBean();
                plano.setIdPlano(rs.getInt("id_plano"));
                plano.setNome(rs.getString("nome"));
                plano.setPreco(rs.getDouble("preco"));
                plano.setLimiteTelas(rs.getInt("limite_telas"));
                plano.setPeriodicidade(rs.getString("periodicidade"));
                planos.add(plano);
            }
        }
        return planos;
    }

    public void atualizar(PlanoBean plano) throws SQLException {
        String sql = "UPDATE plano SET nome = ?, preco = ?, limite_telas = ?, periodicidade = ? WHERE id_plano = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getPreco());
            stmt.setInt(3, plano.getLimiteTelas());
            stmt.setString(4, plano.getPeriodicidade());
            stmt.setInt(5, plano.getIdPlano());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM plano WHERE id_plano = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

