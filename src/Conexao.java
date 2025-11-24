import java.sql.Connection;

public class Conexao {
    private Connection connection;

    public Conexao(Connection connection) {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgres://localhost:3306/streamingBan";
        String user = "postgres";
        String password = "admin";

        try{
            Class.forName(driver);
            this.connection = connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }



}
