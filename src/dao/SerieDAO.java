package dao;

import beans.SerieBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {
    private Connection connection;

    public SerieDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(SerieBean serie) throws SQLException {
        String sql = "INSERT INTO serie (id_conteudo, qtd_temporadas) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serie.getIdConteudo());
            stmt.setInt(2, serie.getQtdTemporadas());
            stmt.executeUpdate();
        }
    }

    public SerieBean buscarPorId(int idConteudo) throws SQLException {
        String sql = "SELECT * FROM serie WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SerieBean serie = new SerieBean();
                    serie.setIdConteudo(idConteudo);
                    serie.setQtdTemporadas(rs.getInt("qtd_temporadas"));
                    return serie;
                }
            }
        }
        return null;
    }

    public List<SerieBean> listarTodos() throws SQLException {
        List<SerieBean> series = new ArrayList<>();
        String sql = "SELECT * FROM serie";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SerieBean serie = new SerieBean();
                serie.setIdConteudo(rs.getInt("id_conteudo"));
                serie.setQtdTemporadas(rs.getInt("qtd_temporadas"));
                series.add(serie);
            }
        }
        return series;
    }

    public void atualizar(SerieBean serie) throws SQLException {
        String sql = "UPDATE serie SET qtd_temporadas = ? WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serie.getQtdTemporadas());
            stmt.setInt(2, serie.getIdConteudo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int idConteudo) throws SQLException {
        String sql = "DELETE FROM serie WHERE id_conteudo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConteudo);
            stmt.executeUpdate();
        }
    }
}

