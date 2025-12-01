package dao;

import beans.ClienteBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(ClienteBean cliente) throws SQLException {
        String sql = "INSERT INTO cliente (id_usuario) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    public ClienteBean buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ClienteBean cliente = new ClienteBean();
                    cliente.setIdUsuario(idUsuario);
                    return cliente;
                }
            }
        }
        return null;
    }

    public List<ClienteBean> listarTodos() throws SQLException {
        List<ClienteBean> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ClienteBean cliente = new ClienteBean();
                cliente.setIdUsuario(rs.getInt("id_usuario"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public void deletar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }
}

