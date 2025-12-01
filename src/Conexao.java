import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USER = "postgres";
    private static final String DEFAULT_PASSWORD = "leonard1200.";
    
    private Connection connection;

    public Conexao() {
        try {
            Class.forName(DRIVER);

            String password = System.getenv("DB_PASSWORD");
            if (password == null || password.isEmpty()) {
                password = System.getProperty("db.password");
            }
            if (password == null || password.isEmpty()) {
                password = DEFAULT_PASSWORD;
            }
            
            System.out.println("Tentando conectar ao banco de dados...");
            System.out.println("URL: " + URL);
            System.out.println("Usuário: " + USER);
            
            this.connection = DriverManager.getConnection(URL, USER, password);
            
            if (this.connection != null && !this.connection.isClosed()) {
                System.out.println("✓ Conexão estabelecida com sucesso!\n");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("ERRO: Driver PostgreSQL não encontrado!");
            System.err.println("Certifique-se de que o arquivo postgresql-42.7.1.jar está no classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("\nERRO ao conectar com o banco de dados!");
            System.err.println("\nPossíveis causas:");
            System.err.println("1. Senha incorreta para o usuário 'postgres'");
            System.err.println("2. Banco de dados 'streamingBan' não existe");
            System.err.println("3. PostgreSQL não está rodando");
            System.err.println("4. Porta incorreta (padrão: 5432)");
            System.err.println("\nSoluções:");
            System.err.println("- Verifique a senha do PostgreSQL e altere em Conexao.java (linha 10)");
            System.err.println("- Ou defina a variável de ambiente: DB_PASSWORD=sua_senha");
            System.err.println("- Ou execute com: java -Ddb.password=sua_senha Main");
            System.err.println("- Certifique-se de que o banco 'streamingBan' foi criado");
            System.err.println("\nDetalhes do erro:");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão!");
            e.printStackTrace();
        }
    }
}
