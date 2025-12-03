package queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubqueryAggregationQuery {

    public List<String> listarConteudosComAvaliacaoAcimaDaMedia(Connection conn) throws SQLException {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT c.id_conteudo, c.titulo, c.ano_lancamento, " +
                     "AVG(a.nota) as media_nota, COUNT(a.id_avaliacao) as qtd_avaliacoes " +
                     "FROM conteudo c " +
                     "INNER JOIN avaliacao a ON c.id_conteudo = a.id_conteudo " +
                     "GROUP BY c.id_conteudo, c.titulo, c.ano_lancamento " +
                     "HAVING AVG(a.nota) > ( " +
                     "    SELECT AVG(nota) " +
                     "    FROM avaliacao " +
                     ") " +
                     "ORDER BY media_nota DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== CONTEÚDOS COM AVALIAÇÃO ACIMA DA MÉDIA (SUBQUERY + AGREGAÇÃO) ===");

            String avgSql = "SELECT AVG(nota) as media_geral FROM avaliacao";
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
                    rs.getInt("id_conteudo"),
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

