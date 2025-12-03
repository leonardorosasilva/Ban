package com.rocketseat.streaming.queries;

import com.rocketseat.streaming.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubqueryAggregationQuery {
    
    public List<String> listarConteudosComAvaliacaoAcimaDaMedia() throws SQLException {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT c.idConteudo, c.titulo, c.ano_lancamento, " +
                     "AVG(a.nota) as media_nota, COUNT(a.idAvaliacao) as qtd_avaliacoes " +
                     "FROM Conteudo c " +
                     "INNER JOIN Avaliacao a ON c.idConteudo = a.idConteudo " +
                     "GROUP BY c.idConteudo, c.titulo, c.ano_lancamento " +
                     "HAVING AVG(a.nota) > ( " +
                     "    SELECT AVG(nota) " +
                     "    FROM Avaliacao " +
                     ") " +
                     "ORDER BY media_nota DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== CONTEÚDOS COM AVALIAÇÃO ACIMA DA MÉDIA (SUBQUERY + AGREGAÇÃO) ===");
            
            String avgSql = "SELECT AVG(nota) as media_geral FROM Avaliacao";
            double mediaGeral = 0.0;
            try (Statement avgStmt = conn.createStatement();
                 ResultSet avgRs = avgStmt.executeQuery(avgSql)) {
                if (avgRs.next()) {
                    mediaGeral = avgRs.getDouble("media_geral");
                }
            }
            
            System.out.println("Média geral de avaliações: " + String.format("%.2f", mediaGeral));
            System.out.println("Conteúdos acima da média:\n");
            
            while (rs.next()) {
                String resultado = String.format(
                    "ID: %d | Título: %s | Ano: %d | " +
                    "Média: %.2f | Qtd Avaliações: %d",
                    rs.getInt("idConteudo"),
                    rs.getString("titulo"),
                    rs.getInt("ano_lancamento"),
                    rs.getDouble("media_nota"),
                    rs.getInt("qtd_avaliacoes")
                );
                resultados.add(resultado);
            }
        }
        return resultados;
    }
}

