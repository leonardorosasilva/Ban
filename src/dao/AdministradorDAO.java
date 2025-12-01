package dao;

import beans.AdministradorBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    private Connection connection;

    public AdministradorDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AdministradorBean admin) throws SQLException {
        String sql = "INSERT INTO administrador (id_usuario, credencial) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, admin.getIdUsuario());
            stmt.setString(2, admin.getCredencial());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    admin.setIdAdministrador(rs.getInt(1));
                }
            }
        }
    }

    public AdministradorBean buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AdministradorBean admin = new AdministradorBean();
                    admin.setIdUsuario(idUsuario);
                    admin.setIdAdministrador(rs.getInt("id_administrador"));
                    admin.setCredencial(rs.getString("credencial"));
                    return admin;
                }
            }
        }
        return null;
    }

    public List<AdministradorBean> listarTodos() throws SQLException {
        List<AdministradorBean> admins = new ArrayList<>();
        String sql = "SELECT * FROM administrador";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AdministradorBean admin = new AdministradorBean();
                admin.setIdUsuario(rs.getInt("id_usuario"));
                admin.setIdAdministrador(rs.getInt("id_administrador"));
                admin.setCredencial(rs.getString("credencial"));
                admins.add(admin);
            }
        }
        return admins;
    }

    public void atualizar(AdministradorBean admin) throws SQLException {
        String sql = "UPDATE administrador SET credencial = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, admin.getCredencial());
            stmt.setInt(2, admin.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM administrador WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }
}

