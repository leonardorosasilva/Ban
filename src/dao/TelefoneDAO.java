package dao;

import beans.TelefoneBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {
    private Connection connection;

    public TelefoneDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(TelefoneBean telefone) throws SQLException {
        String sql = "INSERT INTO telefone (id_usuario, numero) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, telefone.getIdUsuario());
            stmt.setString(2, telefone.getNumero());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    telefone.setIdTelefone(rs.getInt(1));
                }
            }
        }
    }

    public List<TelefoneBean> buscarPorUsuario(int idUsuario) throws SQLException {
        List<TelefoneBean> telefones = new ArrayList<>();
        String sql = "SELECT * FROM telefone WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TelefoneBean telefone = new TelefoneBean();
                    telefone.setIdTelefone(rs.getInt("id_telefone"));
                    telefone.setIdUsuario(rs.getInt("id_usuario"));
                    telefone.setNumero(rs.getString("numero"));
                    telefones.add(telefone);
                }
            }
        }
        return telefones;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

