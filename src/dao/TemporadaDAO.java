package dao;

import beans.TemporadaBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemporadaDAO {
    private Connection connection;

    public TemporadaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(TemporadaBean temporada) throws SQLException {
        String sql = "INSERT INTO temporada (id_serie, numero_temp, ano_temp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, temporada.getIdSerie());
            stmt.setInt(2, temporada.getNumeroTemp());
            stmt.setInt(3, temporada.getAnoTemp());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    temporada.setIdTemporada(rs.getInt(1));
                }
            }
        }
    }

    public TemporadaBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM temporada WHERE id_temporada = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TemporadaBean temporada = new TemporadaBean();
                    temporada.setIdTemporada(rs.getInt("id_temporada"));
                    temporada.setIdSerie(rs.getInt("id_serie"));
                    temporada.setNumeroTemp(rs.getInt("numero_temp"));
                    temporada.setAnoTemp(rs.getInt("ano_temp"));
                    return temporada;
                }
            }
        }
        return null;
    }

    public List<TemporadaBean> buscarPorSerie(int idSerie) throws SQLException {
        List<TemporadaBean> temporadas = new ArrayList<>();
        String sql = "SELECT * FROM temporada WHERE id_serie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSerie);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TemporadaBean temporada = new TemporadaBean();
                    temporada.setIdTemporada(rs.getInt("id_temporada"));
                    temporada.setIdSerie(rs.getInt("id_serie"));
                    temporada.setNumeroTemp(rs.getInt("numero_temp"));
                    temporada.setAnoTemp(rs.getInt("ano_temp"));
                    temporadas.add(temporada);
                }
            }
        }
        return temporadas;
    }

    public List<TemporadaBean> listarTodos() throws SQLException {
        List<TemporadaBean> temporadas = new ArrayList<>();
        String sql = "SELECT * FROM temporada";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TemporadaBean temporada = new TemporadaBean();
                temporada.setIdTemporada(rs.getInt("id_temporada"));
                temporada.setIdSerie(rs.getInt("id_serie"));
                temporada.setNumeroTemp(rs.getInt("numero_temp"));
                temporada.setAnoTemp(rs.getInt("ano_temp"));
                temporadas.add(temporada);
            }
        }
        return temporadas;
    }

    public void atualizar(TemporadaBean temporada) throws SQLException {
        String sql = "UPDATE temporada SET numero_temp = ?, ano_temp = ? WHERE id_temporada = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, temporada.getNumeroTemp());
            stmt.setInt(2, temporada.getAnoTemp());
            stmt.setInt(3, temporada.getIdTemporada());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM temporada WHERE id_temporada = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

