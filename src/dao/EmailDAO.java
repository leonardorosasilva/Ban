package dao;

import beans.EmailBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {
    private Connection connection;

    public EmailDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(EmailBean email) throws SQLException {
        String sql = "INSERT INTO email (id_usuario, endereco) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, email.getIdUsuario());
            stmt.setString(2, email.getEndereco());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    email.setIdEmail(rs.getInt(1));
                }
            }
        }
    }

    public List<EmailBean> buscarPorUsuario(int idUsuario) throws SQLException {
        List<EmailBean> emails = new ArrayList<>();
        String sql = "SELECT * FROM email WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EmailBean email = new EmailBean();
                    email.setIdEmail(rs.getInt("id_email"));
                    email.setIdUsuario(rs.getInt("id_usuario"));
                    email.setEndereco(rs.getString("endereco"));
                    emails.add(email);
                }
            }
        }
        return emails;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM email WHERE id_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

