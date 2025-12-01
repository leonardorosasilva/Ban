package dao;

import beans.DocumentarioBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentarioDAO {
    private Connection connection;

    public DocumentarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(DocumentarioBean documentario) throws SQLException {
        String sql = "INSERT INTO documentario (id_conteudo) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, documentario.getIdConteudo());
            stmt.executeUpdate();
        }
    }

    public DocumentarioBean buscarPorId(int idConteudo) throws SQLException {
        String sql = "SELECT * FROM documentario WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DocumentarioBean documentario = new DocumentarioBean();
                    documentario.setIdConteudo(idConteudo);
                    return documentario;
                }
            }
        }
        return null;
    }

    public List<DocumentarioBean> listarTodos() throws SQLException {
        List<DocumentarioBean> documentarios = new ArrayList<>();
        String sql = "SELECT * FROM documentario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DocumentarioBean documentario = new DocumentarioBean();
                documentario.setIdConteudo(rs.getInt("id_conteudo"));
                documentarios.add(documentario);
            }
        }
        return documentarios;
    }

    public void deletar(int idConteudo) throws SQLException {
        String sql = "DELETE FROM documentario WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            stmt.executeUpdate();
        }
    }
}

