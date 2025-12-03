package com.rocketseat.streaming.queries;

import com.rocketseat.streaming.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoinQuery {
    
    public List<String> listarAssinaturasComUsuarioEPlano() throws SQLException {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT a.idAssinatura, " +
                     "u.primeiro_nome, u.ultimo_nome, u.cpf, " +
                     "p.nome as plano_nome, p.preco, p.limite_telas, " +
                     "a.data_inicio, a.data_fim, a.status " +
                     "FROM Assinatura a " +
                     "INNER JOIN Usuario u ON a.idUsuario = u.idUsuario " +
                     "INNER JOIN Plano p ON a.idPlano = p.idPlano " +
                     "ORDER BY a.idAssinatura";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== ASSINATURAS COM USUÁRIO E PLANO (JOIN) ===");
            while (rs.next()) {
                String resultado = String.format(
                    "Assinatura ID: %d | Usuário: %s %s (CPF: %s) | " +
                    "Plano: %s (R$ %.2f, %d telas) | " +
                    "Período: %s até %s | Status: %s",
                    rs.getInt("idAssinatura"),
                    rs.getString("primeiro_nome"),
                    rs.getString("ultimo_nome"),
                    rs.getString("cpf"),
                    rs.getString("plano_nome"),
                    rs.getBigDecimal("preco"),
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

