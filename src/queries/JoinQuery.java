package queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoinQuery {

    public List<String> listarAssinaturasComUsuarioEPlano(Connection conn) throws SQLException {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT a.id_assinatura, " +
                     "u.primeiro_nome, u.ultimo_nome, u.cpf, " +
                     "p.nome as plano_nome, p.preco, p.limite_telas, " +
                     "a.data_inicio, a.data_fim, a.status " +
                     "FROM assinatura a " +
                     "INNER JOIN usuario u ON a.id_usuario = u.id_usuario " +
                     "INNER JOIN plano p ON a.id_plano = p.id_plano " +
                     "ORDER BY a.id_assinatura";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== ASSINATURAS COM USUÁRIO E PLANO (JOIN) ===");
            while (rs.next()) {
                String resultado = String.format(
                    "Assinatura ID: %d | Usuário: %s %s (CPF: %s) | " +
                    "Plano: %s (R$ %.2f, %d telas) | " +
                    "Período: %s até %s | Status: %s",
                    rs.getInt("id_assinatura"),
                    rs.getString("primeiro_nome"),
                    rs.getString("ultimo_nome"),
                    rs.getString("cpf"),
                    rs.getString("plano_nome"),
                    rs.getDouble("preco"),
                    rs.getInt("limite_telas"),
                    rs.getDate("data_inicio").toString(),
                    rs.getDate("data_fim") != null ? rs.getDate("data_fim").toString() : "Indefinido",
                    rs.getString("status")
                );
                resultados.add(resultado);
            }
        }
        return resultados;
    }
}

