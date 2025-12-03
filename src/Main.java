import dao.*;
import beans.*;
import queries.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    private static Conexao conexao;
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioBean loggedUser = null;
    private static String userType = null; // "cliente" or "administrador"

    public static void main(String[] args) {
        System.out.println("=== Sistema de Streaming ===");

        try {
            conexao = new Conexao();
            if (conexao.getConnection() == null) {
                System.err.println("Erro ao conectar ao banco de dados!");
                return;
            }
            System.out.println("Conexão estabelecida com sucesso!\n");

            boolean running = true;
            while (running) {
                if (loggedUser == null) {
                    int opcao = menuLogin();
                    switch (opcao) {
                        case 1:
                            login();
                            break;
                        case 2:
                            registerClient();
                            break;
                        case 3:
                            registerAdmin();
                            break;
                        case 0:
                            running = false;
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                } else {
                    if ("cliente".equals(userType)) {
                        menuClienteUser();
                    } else if ("administrador".equals(userType)) {
                        menuAdminUser();
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                conexao.fecharConexao();
            }
            scanner.close();
        }
    }

    private static int menuLogin() {
        System.out.println("\n=== MENU DE LOGIN ===");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar como Cliente");
        System.out.println("3. Cadastrar como Administrador");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        ClienteDAO clienteDAO = new ClienteDAO(conexao.getConnection());
        AdministradorDAO adminDAO = new AdministradorDAO(conexao.getConnection());

        try {
            UsuarioBean user = usuarioDAO.buscarPorCpf(cpf);
            if (user != null && user.getSenha().equals(senha)) {
                ClienteBean cliente = clienteDAO.buscarPorId(user.getIdUsuario());
                if (cliente != null) {
                    loggedUser = user;
                    userType = "cliente";
                    System.out.println("Login realizado como cliente!");
                    return;
                }
                AdministradorBean admin = adminDAO.buscarPorId(user.getIdUsuario());
                if (admin != null) {
                    loggedUser = user;
                    userType = "administrador";
                    System.out.println("Login realizado como administrador!");
                    return;
                }
            }
            System.out.println("CPF ou senha incorretos!");
        } catch (SQLException e) {
            System.err.println("Erro no login: " + e.getMessage());
        }
    }

    private static void registerClient() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        ClienteDAO clienteDAO = new ClienteDAO(conexao.getConnection());

        try {
            UsuarioBean usuario = new UsuarioBean();
            System.out.println("\n=== CADASTRO DE CLIENTE ===");
            System.out.print("Primeiro nome: ");
            usuario.setPrimeiro_nome(scanner.nextLine());
            System.out.print("Último nome: ");
            usuario.setUltimo_nome(scanner.nextLine());
            System.out.print("CPF: ");
            usuario.setCpf(scanner.nextLine());
            System.out.print("Senha: ");
            usuario.setSenha(scanner.nextLine());
            System.out.print("Data nascimento (YYYY-MM-DD): ");
            usuario.setData_nascimento(java.sql.Date.valueOf(scanner.nextLine()));
            usuarioDAO.inserir(usuario);

            ClienteBean cliente = new ClienteBean();
            cliente.setIdUsuario(usuario.getIdUsuario());
            clienteDAO.inserir(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
        }
    }

    private static void registerAdmin() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        AdministradorDAO adminDAO = new AdministradorDAO(conexao.getConnection());

        try {
            UsuarioBean usuario = new UsuarioBean();
            System.out.println("\n=== CADASTRO DE ADMINISTRADOR ===");
            System.out.print("Primeiro nome: ");
            usuario.setPrimeiro_nome(scanner.nextLine());
            System.out.print("Último nome: ");
            usuario.setUltimo_nome(scanner.nextLine());
            System.out.print("CPF: ");
            usuario.setCpf(scanner.nextLine());
            System.out.print("Senha: ");
            usuario.setSenha(scanner.nextLine());
            System.out.print("Data nascimento (YYYY-MM-DD): ");
            usuario.setData_nascimento(java.sql.Date.valueOf(scanner.nextLine()));
            usuarioDAO.inserir(usuario);

            AdministradorBean admin = new AdministradorBean();
            admin.setIdUsuario(usuario.getIdUsuario());
            System.out.print("Credencial: ");
            admin.setCredencial(scanner.nextLine());
            adminDAO.inserir(admin);
            System.out.println("Administrador cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
        }
    }

    private static int menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Usuários");
        System.out.println("2. Gerenciar Clientes");
        System.out.println("3. Gerenciar Administradores");
        System.out.println("4. Gerenciar Planos");
        System.out.println("5. Gerenciar Assinaturas");
        System.out.println("6. Gerenciar Conteúdos");
        System.out.println("7. Gerenciar Avaliações");
        System.out.println("8. Gerenciar Histórico");
        System.out.println("9. Consultas Especiais");
        System.out.println("0. Logout");
        System.out.print("Escolha uma opção: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void menuUsuario() {
        UsuarioDAO dao = new UsuarioDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR USUÁRIOS ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Buscar Usuário por ID");
            System.out.println("3. Buscar Usuário por CPF");
            System.out.println("4. Listar Todos");
            System.out.println("5. Atualizar Usuário");
            System.out.println("6. Deletar Usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        UsuarioBean usuario = new UsuarioBean();
                        System.out.print("Primeiro nome: ");
                        usuario.setPrimeiro_nome(scanner.nextLine());
                        System.out.print("Último nome: ");
                        usuario.setUltimo_nome(scanner.nextLine());
                        System.out.print("CPF: ");
                        usuario.setCpf(scanner.nextLine());
                        System.out.print("Senha: ");
                        usuario.setSenha(scanner.nextLine());
                        System.out.print("Data nascimento (YYYY-MM-DD): ");
                        usuario.setData_nascimento(java.sql.Date.valueOf(scanner.nextLine()));
                        dao.inserir(usuario);
                        System.out.println("Usuário cadastrado com sucesso! ID: " + usuario.getIdUsuario());
                        break;
                    case 2:
                        System.out.print("ID do usuário: ");
                        String idStr = scanner.nextLine();
                        UsuarioBean u = dao.buscarPorId(Integer.parseInt(idStr.trim()));
                        if (u != null) {
                            System.out.println("Usuário: " + u.getPrimeiro_nome() + " " + u.getUltimo_nome());
                        } else {
                            System.out.println("Usuário não encontrado!");
                        }
                        break;
                    case 3:
                        System.out.print("CPF: ");
                        UsuarioBean u2 = dao.buscarPorCpf(scanner.nextLine());
                        if (u2 != null) {
                            System.out.println("Usuário: " + u2.getPrimeiro_nome() + " " + u2.getUltimo_nome());
                        } else {
                            System.out.println("Usuário não encontrado!");
                        }
                        break;
                    case 4:
                        List<UsuarioBean> usuarios = dao.listarTodos();
                        for (UsuarioBean usr : usuarios) {
                            System.out.println("ID: " + usr.getIdUsuario() + " - " + usr.getPrimeiro_nome() + " " + usr.getUltimo_nome());
                        }
                        break;
                    case 5:
                        System.out.print("ID do usuário: ");
                        String idStr2 = scanner.nextLine();
                        int id = Integer.parseInt(idStr2.trim());
                        UsuarioBean u3 = dao.buscarPorId(id);
                        if (u3 != null) {
                            System.out.print("Novo primeiro nome: ");
                            u3.setPrimeiro_nome(scanner.nextLine());
                            System.out.print("Novo último nome: ");
                            u3.setUltimo_nome(scanner.nextLine());
                            dao.atualizar(u3);
                            System.out.println("Usuário atualizado!");
                        } else {
                            System.out.println("Usuário não encontrado!");
                        }
                        break;
                    case 6:
                        System.out.print("ID do usuário: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Usuário deletado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuClienteUser() {
        ConteudoDAO conteudoDAO = new ConteudoDAO(conexao.getConnection());
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(conexao.getConnection());
        HistoricoDAO historicoDAO = new HistoricoDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== MENU DO CLIENTE ===");
            System.out.println("Bem-vindo, " + loggedUser.getPrimeiro_nome() + "!");
            System.out.println("1. Ver Conteúdos");
            System.out.println("2. Avaliar Conteúdo");
            System.out.println("3. Ver Meu Histórico");
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        List<ConteudoBean> conteudos = conteudoDAO.listarTodos();
                        for (ConteudoBean c : conteudos) {
                            System.out.println("ID: " + c.getIdConteudo() + " - " + c.getTitulo() + " (" + c.getAnoLancamento() + ")");
                        }
                        break;
                    case 2:
                        AvaliacaoBean avaliacao = new AvaliacaoBean();
                        System.out.print("ID do conteúdo: ");
                        String idConteudoStr = scanner.nextLine();
                        avaliacao.setIdConteudo(Integer.parseInt(idConteudoStr.trim()));
                        System.out.print("Nota (0-10): ");
                        String notaStr = scanner.nextLine();
                        avaliacao.setNota(Double.parseDouble(notaStr.trim()));
                        System.out.print("Comentário: ");
                        avaliacao.setComentario(scanner.nextLine());
                        avaliacao.setIdUsuario(loggedUser.getIdUsuario());
                        avaliacao.setDataAvaliacao(new Date());
                        avaliacaoDAO.inserir(avaliacao);
                        System.out.println("Avaliação registrada!");
                        break;
                    case 3:
                        List<HistoricoBean> historicos = historicoDAO.buscarPorUsuario(loggedUser.getIdUsuario());
                        for (HistoricoBean h : historicos) {
                            System.out.println("Conteúdo ID: " + h.getIdConteudo() + " - Progresso: " + h.getProgresso() + "% - Data: " + h.getDataVisualizacao());
                        }
                        break;
                    case 0:
                        loggedUser = null;
                        userType = null;
                        System.out.println("Logout realizado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0 && loggedUser != null);
    }

    private static void menuCliente() {
        ClienteDAO dao = new ClienteDAO(conexao.getConnection());
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Deletar Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        // Primeiro criar o usuário
                        UsuarioBean usuario = new UsuarioBean();
                        System.out.print("Primeiro nome: ");
                        usuario.setPrimeiro_nome(scanner.nextLine());
                        System.out.print("Último nome: ");
                        usuario.setUltimo_nome(scanner.nextLine());
                        System.out.print("CPF: ");
                        usuario.setCpf(scanner.nextLine());
                        System.out.print("Senha: ");
                        usuario.setSenha(scanner.nextLine());
                        System.out.print("Data nascimento (YYYY-MM-DD): ");
                        usuario.setData_nascimento(java.sql.Date.valueOf(scanner.nextLine()));
                        usuarioDAO.inserir(usuario);

                        // Depois criar o cliente
                        ClienteBean cliente = new ClienteBean();
                        cliente.setIdUsuario(usuario.getIdUsuario());
                        dao.inserir(cliente);
                        System.out.println("Cliente cadastrado com sucesso!");
                        break;
                    case 2:
                        List<ClienteBean> clientes = dao.listarTodos();
                        for (ClienteBean c : clientes) {
                            UsuarioBean u = usuarioDAO.buscarPorId(c.getIdUsuario());
                            if (u != null) {
                                System.out.println("ID: " + c.getIdUsuario() + " - " + u.getPrimeiro_nome() + " " + u.getUltimo_nome());
                            }
                        }
                        break;
                    case 3:
                        System.out.print("ID do cliente: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        UsuarioBean u = usuarioDAO.buscarPorId(id);
                        if (u != null) {
                            System.out.print("Novo primeiro nome: ");
                            u.setPrimeiro_nome(scanner.nextLine());
                            System.out.print("Novo último nome: ");
                            u.setUltimo_nome(scanner.nextLine());
                            usuarioDAO.atualizar(u);
                            System.out.println("Cliente atualizado!");
                        } else {
                            System.out.println("Cliente não encontrado!");
                        }
                        break;
                    case 4:
                        System.out.print("ID do cliente: ");
                        String idDelStr = scanner.nextLine();
                        int idDel = Integer.parseInt(idDelStr.trim());
                        dao.deletar(idDel);
                        usuarioDAO.deletar(idDel);
                        System.out.println("Cliente deletado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuAdminUser() {
        int opcao;
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuCliente();
                    break;
                case 3:
                    menuAdministrador();
                    break;
                case 4:
                    menuPlano();
                    break;
                case 5:
                    menuAssinatura();
                    break;
                case 6:
                    menuConteudo();
                    break;
                case 7:
                    menuAvaliacao();
                    break;
                case 8:
                    menuHistorico();
                    break;
                case 9:
                    menuConsultas();
                    break;
                case 0:
                    loggedUser = null;
                    userType = null;
                    System.out.println("Logout realizado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0 && loggedUser != null);
    }

    private static void menuConsultas() {
        JoinQuery joinQuery = new JoinQuery();
        SubqueryAggregationQuery subQuery = new SubqueryAggregationQuery();
        int opcao;

        do {
            System.out.println("\n=== CONSULTAS ESPECIAIS ===");
            System.out.println("1. Assinaturas com Usuário e Plano (JOIN)");
            System.out.println("2. Conteúdos com Avaliação Acima da Média (Subquery + Agregação)");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        joinQuery.listarAssinaturasComUsuarioEPlano(conexao.getConnection());
                        break;
                    case 2:
                        subQuery.listarConteudosComAvaliacaoAcimaDaMedia(conexao.getConnection());
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuAdministrador() {
        AdministradorDAO dao = new AdministradorDAO(conexao.getConnection());
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR ADMINISTRADORES ===");
            System.out.println("1. Cadastrar Administrador");
            System.out.println("2. Listar Administradores");
            System.out.println("3. Atualizar Administrador");
            System.out.println("4. Deletar Administrador");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        UsuarioBean usuario = new UsuarioBean();
                        System.out.print("Primeiro nome: ");
                        usuario.setPrimeiro_nome(scanner.nextLine());
                        System.out.print("Último nome: ");
                        usuario.setUltimo_nome(scanner.nextLine());
                        System.out.print("CPF: ");
                        usuario.setCpf(scanner.nextLine());
                        System.out.print("Senha: ");
                        usuario.setSenha(scanner.nextLine());
                        System.out.print("Data nascimento (YYYY-MM-DD): ");
                        usuario.setData_nascimento(java.sql.Date.valueOf(scanner.nextLine()));
                        usuarioDAO.inserir(usuario);

                        AdministradorBean admin = new AdministradorBean();
                        admin.setIdUsuario(usuario.getIdUsuario());
                        System.out.print("Credencial: ");
                        admin.setCredencial(scanner.nextLine());
                        dao.inserir(admin);
                        System.out.println("Administrador cadastrado com sucesso!");
                        break;
                    case 2:
                        List<AdministradorBean> admins = dao.listarTodos();
                        for (AdministradorBean a : admins) {
                            UsuarioBean u = usuarioDAO.buscarPorId(a.getIdUsuario());
                            if (u != null) {
                                System.out.println("ID: " + a.getIdUsuario() + " - " + u.getPrimeiro_nome() + " " + u.getUltimo_nome() + " - Credencial: " + a.getCredencial());
                            }
                        }
                        break;
                    case 3:
                        System.out.print("ID do administrador: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        AdministradorBean a = dao.buscarPorId(id);
                        if (a != null) {
                            System.out.print("Nova credencial: ");
                            a.setCredencial(scanner.nextLine());
                            dao.atualizar(a);
                            System.out.println("Administrador atualizado!");
                        } else {
                            System.out.println("Administrador não encontrado!");
                        }
                        break;
                    case 4:
                        System.out.print("ID do administrador: ");
                        String idDelStr = scanner.nextLine();
                        int idDel = Integer.parseInt(idDelStr.trim());
                        dao.deletar(idDel);
                        usuarioDAO.deletar(idDel);
                        System.out.println("Administrador deletado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuPlano() {
        PlanoDAO dao = new PlanoDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR PLANOS ===");
            System.out.println("1. Cadastrar Plano");
            System.out.println("2. Listar Planos");
            System.out.println("3. Atualizar Plano");
            System.out.println("4. Deletar Plano");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        PlanoBean plano = new PlanoBean();
                        System.out.print("Nome: ");
                        plano.setNome(scanner.nextLine());
                        System.out.print("Preço: ");
                        String precoStr = scanner.nextLine();
                        plano.setPreco(Double.parseDouble(precoStr.trim()));
                        System.out.print("Limite de telas: ");
                        String telasStr = scanner.nextLine();
                        plano.setLimiteTelas(Integer.parseInt(telasStr.trim()));
                        System.out.print("Periodicidade: ");
                        plano.setPeriodicidade(scanner.nextLine());
                        dao.inserir(plano);
                        System.out.println("Plano cadastrado com sucesso! ID: " + plano.getIdPlano());
                        break;
                    case 2:
                        List<PlanoBean> planos = dao.listarTodos();
                        for (PlanoBean p : planos) {
                            System.out.println("ID: " + p.getIdPlano() + " - " + p.getNome() + " - R$ " + p.getPreco());
                        }
                        break;
                    case 3:
                        System.out.print("ID do plano: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        PlanoBean p = dao.buscarPorId(id);
                        if (p != null) {
                            System.out.print("Novo nome: ");
                            p.setNome(scanner.nextLine());
                            System.out.print("Novo preço: ");
                            String precoStr2 = scanner.nextLine();
                            p.setPreco(Double.parseDouble(precoStr2.trim()));
                            System.out.print("Novo limite de telas: ");
                            String telasStr2 = scanner.nextLine();
                            p.setLimiteTelas(Integer.parseInt(telasStr2.trim()));
                            System.out.print("Nova periodicidade: ");
                            p.setPeriodicidade(scanner.nextLine());
                            dao.atualizar(p);
                            System.out.println("Plano atualizado!");
                        } else {
                            System.out.println("Plano não encontrado!");
                        }
                        break;
                    case 4:
                        System.out.print("ID do plano: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Plano deletado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuAssinatura() {
        AssinaturaDAO dao = new AssinaturaDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR ASSINATURAS ===");
            System.out.println("1. Cadastrar Assinatura");
            System.out.println("2. Listar Assinaturas");
            System.out.println("3. Atualizar Assinatura");
            System.out.println("4. Deletar Assinatura");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        AssinaturaBean assinatura = new AssinaturaBean();
                        System.out.print("ID do usuário: ");
                        String idUserStr = scanner.nextLine();
                        assinatura.setIdUsuario(Integer.parseInt(idUserStr.trim()));
                        System.out.print("ID do plano: ");
                        String idPlanoStr = scanner.nextLine();
                        assinatura.setIdPlano(Integer.parseInt(idPlanoStr.trim()));
                        System.out.print("Duração (dias): ");
                        String duracaoStr = scanner.nextLine();
                        assinatura.setDuracao(Integer.parseInt(duracaoStr.trim()));
                        System.out.print("Data início (YYYY-MM-DD): ");
                        assinatura.setDataInicio(java.sql.Date.valueOf(scanner.nextLine()));
                        System.out.print("Data fim (YYYY-MM-DD): ");
                        assinatura.setDataFim(java.sql.Date.valueOf(scanner.nextLine()));
                        System.out.print("Status (Ativo/Desativado): ");
                        assinatura.setStatus(scanner.nextLine());
                        dao.inserir(assinatura);
                        System.out.println("Assinatura cadastrada com sucesso!");
                        break;
                    case 2:
                        List<AssinaturaBean> assinaturas = dao.listarTodos();
                        for (AssinaturaBean a : assinaturas) {
                            System.out.println("ID: " + a.getIdAssinatura() + " - Usuário: " + a.getIdUsuario() + " - Status: " + a.getStatus());
                        }
                        break;
                    case 3:
                        System.out.print("ID da assinatura: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        AssinaturaBean a = dao.buscarPorId(id);
                        if (a != null) {
                            System.out.print("Novo ID do plano: ");
                            String idPlanoStr2 = scanner.nextLine();
                            a.setIdPlano(Integer.parseInt(idPlanoStr2.trim()));
                            System.out.print("Nova duração (dias): ");
                            String duracaoStr2 = scanner.nextLine();
                            a.setDuracao(Integer.parseInt(duracaoStr2.trim()));
                            System.out.print("Nova data início (YYYY-MM-DD): ");
                            a.setDataInicio(java.sql.Date.valueOf(scanner.nextLine()));
                            System.out.print("Nova data fim (YYYY-MM-DD): ");
                            a.setDataFim(java.sql.Date.valueOf(scanner.nextLine()));
                            System.out.print("Novo status (Ativo/Desativado): ");
                            a.setStatus(scanner.nextLine());
                            dao.atualizar(a);
                            System.out.println("Assinatura atualizada!");
                        } else {
                            System.out.println("Assinatura não encontrada!");
                        }
                        break;
                    case 4:
                        System.out.print("ID da assinatura: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Assinatura deletada!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuConteudo() {
        ConteudoDAO dao = new ConteudoDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR CONTEÚDOS ===");
            System.out.println("1. Cadastrar Conteúdo");
            System.out.println("2. Listar Conteúdos");
            System.out.println("3. Atualizar Conteúdo");
            System.out.println("4. Deletar Conteúdo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        ConteudoBean conteudo = new ConteudoBean();
                        System.out.print("Título: ");
                        conteudo.setTitulo(scanner.nextLine());
                        System.out.print("Ano de lançamento: ");
                        String anoStr = scanner.nextLine();
                        conteudo.setAnoLancamento(Integer.parseInt(anoStr.trim()));
                        System.out.print("Classificação etária: ");
                        conteudo.setClassificacaoEtaria(scanner.nextLine());
                        System.out.print("Sinopse: ");
                        conteudo.setSinopse(scanner.nextLine());
                        System.out.print("ID do administrador: ");
                        String idUserStr = scanner.nextLine();
                        conteudo.setIdUsuario(Integer.parseInt(idUserStr.trim()));
                        conteudo.setData_cadastro(new Date());
                        dao.inserir(conteudo);
                        System.out.println("Conteúdo cadastrado com sucesso! ID: " + conteudo.getIdConteudo());
                        break;
                    case 2:
                        List<ConteudoBean> conteudos = dao.listarTodos();
                        for (ConteudoBean c : conteudos) {
                            System.out.println("ID: " + c.getIdConteudo() + " - " + c.getTitulo() + " (" + c.getAnoLancamento() + ")");
                        }
                        break;
                    case 3:
                        System.out.print("ID do conteúdo: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        ConteudoBean c = dao.buscarPorId(id);
                        if (c != null) {
                            System.out.print("Novo título: ");
                            c.setTitulo(scanner.nextLine());
                            System.out.print("Novo ano de lançamento: ");
                            String anoStr2 = scanner.nextLine();
                            c.setAnoLancamento(Integer.parseInt(anoStr2.trim()));
                            System.out.print("Nova classificação etária: ");
                            c.setClassificacaoEtaria(scanner.nextLine());
                            System.out.print("Nova sinopse: ");
                            c.setSinopse(scanner.nextLine());
                            dao.atualizar(c);
                            System.out.println("Conteúdo atualizado!");
                        } else {
                            System.out.println("Conteúdo não encontrado!");
                        }
                        break;
                    case 4:
                        System.out.print("ID do conteúdo: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Conteúdo deletado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuAvaliacao() {
        AvaliacaoDAO dao = new AvaliacaoDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR AVALIAÇÕES ===");
            System.out.println("1. Cadastrar Avaliação");
            System.out.println("2. Listar Avaliações");
            System.out.println("3. Atualizar Avaliação");
            System.out.println("4. Deletar Avaliação");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        AvaliacaoBean avaliacao = new AvaliacaoBean();
                        System.out.print("ID do usuário: ");
                        String idUserStr = scanner.nextLine();
                        avaliacao.setIdUsuario(Integer.parseInt(idUserStr.trim()));
                        System.out.print("ID do conteúdo: ");
                        String idConteudoStr = scanner.nextLine();
                        avaliacao.setIdConteudo(Integer.parseInt(idConteudoStr.trim()));
                        System.out.print("Nota (0-10): ");
                        String notaStr = scanner.nextLine();
                        avaliacao.setNota(Double.parseDouble(notaStr.trim()));
                        System.out.print("Comentário: ");
                        avaliacao.setComentario(scanner.nextLine());
                        avaliacao.setDataAvaliacao(new Date());
                        dao.inserir(avaliacao);
                        System.out.println("Avaliação cadastrada com sucesso!");
                        break;
                    case 2:
                        List<AvaliacaoBean> avaliacoes = dao.listarTodos();
                        for (AvaliacaoBean a : avaliacoes) {
                            System.out.println("ID: " + a.getIdAvaliacao() + " - Nota: " + a.getNota() + " - " + a.getComentario());
                        }
                        break;
                    case 3:
                        System.out.print("ID da avaliação: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        AvaliacaoBean a = dao.buscarPorId(id);
                        if (a != null) {
                            System.out.print("Nova nota (0-10): ");
                            String notaStr2 = scanner.nextLine();
                            a.setNota(Double.parseDouble(notaStr2.trim()));
                            System.out.print("Novo comentário: ");
                            a.setComentario(scanner.nextLine());
                            a.setDataAvaliacao(new Date());
                            dao.atualizar(a);
                            System.out.println("Avaliação atualizada!");
                        } else {
                            System.out.println("Avaliação não encontrada!");
                        }
                        break;
                    case 4:
                        System.out.print("ID da avaliação: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Avaliação deletada!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuHistorico() {
        HistoricoDAO dao = new HistoricoDAO(conexao.getConnection());
        int opcao;

        do {
            System.out.println("\n=== GERENCIAR HISTÓRICO ===");
            System.out.println("1. Cadastrar Visualização");
            System.out.println("2. Listar Histórico");
            System.out.println("3. Atualizar Visualização");
            System.out.println("4. Deletar Visualização");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        HistoricoBean historico = new HistoricoBean();
                        System.out.print("ID do usuário: ");
                        String idUserStr = scanner.nextLine();
                        historico.setIdUsuario(Integer.parseInt(idUserStr.trim()));
                        System.out.print("ID do conteúdo: ");
                        String idConteudoStr = scanner.nextLine();
                        historico.setIdConteudo(Integer.parseInt(idConteudoStr.trim()));
                        System.out.print("Progresso (0-100): ");
                        String progressoStr = scanner.nextLine();
                        historico.setProgresso(Double.parseDouble(progressoStr.trim()));
                        historico.setDataVisualizacao(new Date());
                        dao.inserir(historico);
                        System.out.println("Visualização registrada com sucesso!");
                        break;
                    case 2:
                        List<HistoricoBean> historicos = dao.listarTodos();
                        for (HistoricoBean h : historicos) {
                            System.out.println("ID: " + h.getIdHistorico() + " - Usuário: " + h.getIdUsuario() + " - Progresso: " + h.getProgresso() + "%");
                        }
                        break;
                    case 3:
                        System.out.print("ID do histórico: ");
                        String idStr = scanner.nextLine();
                        int id = Integer.parseInt(idStr.trim());
                        HistoricoBean h = dao.buscarPorId(id);
                        if (h != null) {
                            System.out.print("Novo progresso (0-100): ");
                            String progressoStr2 = scanner.nextLine();
                            h.setProgresso(Double.parseDouble(progressoStr2.trim()));
                            h.setDataVisualizacao(new Date());
                            dao.atualizar(h);
                            System.out.println("Visualização atualizada!");
                        } else {
                            System.out.println("Histórico não encontrado!");
                        }
                        break;
                    case 4:
                        System.out.print("ID do histórico: ");
                        String idDelStr = scanner.nextLine();
                        dao.deletar(Integer.parseInt(idDelStr.trim()));
                        System.out.println("Visualização deletada!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}
