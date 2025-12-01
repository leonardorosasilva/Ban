package dao;

import beans.EpisodioBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EpisodioDAO {
    private Connection connection;

    public EpisodioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(EpisodioBean episodio) throws SQLException {
        String sql = "INSERT INTO episodio (id_temporada, num_episodio, titulo, duracao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, episodio.getIdTemporada());
            stmt.setInt(2, episodio.getNumEpisodio());
            stmt.setString(3, episodio.getTitulo());
            stmt.setDouble(4, episodio.getDuracao());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    episodio.setIdEpisodio(rs.getInt(1));
                }
            }
        }
    }

    public EpisodioBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM episodio WHERE id_episodio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EpisodioBean episodio = new EpisodioBean();
                    episodio.setIdEpisodio(rs.getInt("id_episodio"));
                    episodio.setIdTemporada(rs.getInt("id_temporada"));
                    episodio.setNumEpisodio(rs.getInt("num_episodio"));
                    episodio.setTitulo(rs.getString("titulo"));
                    episodio.setDuracao(rs.getDouble("duracao"));
                    return episodio;
                }
            }
        }
        return null;
    }

    public List<EpisodioBean> buscarPorTemporada(int idTemporada) throws SQLException {
        List<EpisodioBean> episodios = new ArrayList<>();
        String sql = "SELECT * FROM episodio WHERE id_temporada = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTemporada);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EpisodioBean episodio = new EpisodioBean();
                    episodio.setIdEpisodio(rs.getInt("id_episodio"));
                    episodio.setIdTemporada(rs.getInt("id_temporada"));
                    episodio.setNumEpisodio(rs.getInt("num_episodio"));
                    episodio.setTitulo(rs.getString("titulo"));
                    episodio.setDuracao(rs.getDouble("duracao"));
                    episodios.add(episodio);
                }
            }
        }
        return episodios;
    }

    public List<EpisodioBean> listarTodos() throws SQLException {
        List<EpisodioBean> episodios = new ArrayList<>();
        String sql = "SELECT * FROM episodio";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EpisodioBean episodio = new EpisodioBean();
                episodio.setIdEpisodio(rs.getInt("id_episodio"));
                episodio.setIdTemporada(rs.getInt("id_temporada"));
                episodio.setTitulo(rs.getString("titulo"));
                episodio.setDuracao(rs.getDouble("duracao"));
                episodios.add(episodio);
            }
        }
        return episodios;
    }

    public void atualizar(EpisodioBean episodio) throws SQLException {
        String sql = "UPDATE episodio SET num_episodio = ?, titulo = ?, duracao = ? WHERE id_episodio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, episodio.getNumEpisodio());
            stmt.setString(2, episodio.getTitulo());
            stmt.setDouble(3, episodio.getDuracao());
            stmt.setInt(4, episodio.getIdEpisodio());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM episodio WHERE id_episodio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

