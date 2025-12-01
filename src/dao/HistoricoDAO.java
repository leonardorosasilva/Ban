package dao;

import beans.HistoricoBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {
    private Connection connection;

    public HistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(HistoricoBean historico) throws SQLException {
        String sql = "INSERT INTO historico (id_usuario, id_conteudo, data_visualizacao, progresso) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, historico.getIdUsuario());
            stmt.setInt(2, historico.getIdConteudo());
            stmt.setDate(3, new java.sql.Date(historico.getDataVisualizacao().getTime()));
            stmt.setDouble(4, historico.getProgresso());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    historico.setIdHistorico(rs.getInt(1));
                }
            }
        }
    }

    public HistoricoBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM historico WHERE id_historico = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HistoricoBean historico = new HistoricoBean();
                    historico.setIdHistorico(rs.getInt("id_historico"));
                    historico.setIdUsuario(rs.getInt("id_usuario"));
                    historico.setIdConteudo(rs.getInt("id_conteudo"));
                    historico.setDataVisualizacao(rs.getDate("data_visualizacao"));
                    historico.setProgresso(rs.getDouble("progresso"));
                    return historico;
                }
            }
        }
        return null;
    }

    public List<HistoricoBean> buscarPorUsuario(int idUsuario) throws SQLException {
        List<HistoricoBean> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico WHERE id_usuario = ? ORDER BY data_visualizacao DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoricoBean historico = new HistoricoBean();
                    historico.setIdHistorico(rs.getInt("id_historico"));
                    historico.setIdUsuario(rs.getInt("id_usuario"));
                    historico.setIdConteudo(rs.getInt("id_conteudo"));
                    historico.setDataVisualizacao(rs.getDate("data_visualizacao"));
                    historico.setProgresso(rs.getDouble("progresso"));
                    historicos.add(historico);
                }
            }
        }
        return historicos;
    }

    public List<HistoricoBean> listarTodos() throws SQLException {
        List<HistoricoBean> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HistoricoBean historico = new HistoricoBean();
                historico.setIdHistorico(rs.getInt("id_historico"));
                historico.setIdUsuario(rs.getInt("id_usuario"));
                historico.setIdConteudo(rs.getInt("id_conteudo"));
                historico.setDataVisualizacao(rs.getDate("data_visualizacao"));
                historico.setProgresso(rs.getDouble("progresso"));
                historicos.add(historico);
            }
        }
        return historicos;
    }

    public void atualizar(HistoricoBean historico) throws SQLException {
        String sql = "UPDATE historico SET progresso = ?, data_visualizacao = ? WHERE id_historico = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, historico.getProgresso());
            stmt.setDate(2, new java.sql.Date(historico.getDataVisualizacao().getTime()));
            stmt.setInt(3, historico.getIdHistorico());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM historico WHERE id_historico = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

