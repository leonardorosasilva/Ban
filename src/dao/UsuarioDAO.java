package dao;

import beans.UsuarioBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(UsuarioBean usuario) throws SQLException {
        String sql = "INSERT INTO usuario (primeiro_nome, ultimo_nome, cpf, senha, data_nascimento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getPrimeiro_nome());
            stmt.setString(2, usuario.getUltimo_nome());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, new java.sql.Date(usuario.getData_nascimento().getTime()));
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }
        }
    }

    public UsuarioBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UsuarioBean usuario = new UsuarioBean();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setPrimeiro_nome(rs.getString("primeiro_nome"));
                    usuario.setUltimo_nome(rs.getString("ultimo_nome"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setData_nascimento(rs.getDate("data_nascimento"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public UsuarioBean buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UsuarioBean usuario = new UsuarioBean();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setPrimeiro_nome(rs.getString("primeiro_nome"));
                    usuario.setUltimo_nome(rs.getString("ultimo_nome"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setData_nascimento(rs.getDate("data_nascimento"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<UsuarioBean> listarTodos() throws SQLException {
        List<UsuarioBean> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsuarioBean usuario = new UsuarioBean();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setPrimeiro_nome(rs.getString("primeiro_nome"));
                usuario.setUltimo_nome(rs.getString("ultimo_nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setData_nascimento(rs.getDate("data_nascimento"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void atualizar(UsuarioBean usuario) throws SQLException {
        String sql = "UPDATE usuario SET primeiro_nome = ?, ultimo_nome = ?, cpf = ?, senha = ?, data_nascimento = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getPrimeiro_nome());
            stmt.setString(2, usuario.getUltimo_nome());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, new java.sql.Date(usuario.getData_nascimento().getTime()));
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

