import dao.*;
import beans.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    private static Conexao conexao;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Sistema de Streaming ===");
        
        try {
            conexao = new Conexao();
            if (conexao.getConnection() == null) {
                System.err.println("Erro ao conectar ao banco de dados!");
                return;
            }
            System.out.println("Conexão estabelecida com sucesso!\n");
            
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
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 0);
            
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
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
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
            opcao = scanner.nextInt();
            scanner.nextLine();
            
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
                        UsuarioBean u = dao.buscarPorId(scanner.nextInt());
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
                        int id = scanner.nextInt();
                        scanner.nextLine();
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
                        dao.deletar(scanner.nextInt());
                        System.out.println("Usuário deletado!");
                        break;
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void menuCliente() {
        ClienteDAO dao = new ClienteDAO(conexao.getConnection());
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
        int opcao;
        
        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        PlanoBean plano = new PlanoBean();
                        System.out.print("Nome: ");
                        plano.setNome(scanner.nextLine());
                        System.out.print("Preço: ");
                        plano.setPreco(scanner.nextDouble());
                        System.out.print("Limite de telas: ");
                        plano.setLimiteTelas(scanner.nextInt());
                        scanner.nextLine();
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        AssinaturaBean assinatura = new AssinaturaBean();
                        System.out.print("ID do usuário: ");
                        assinatura.setIdUsuario(scanner.nextInt());
                        System.out.print("ID do plano: ");
                        assinatura.setIdPlano(scanner.nextInt());
                        System.out.print("Duração (dias): ");
                        assinatura.setDuracao(scanner.nextInt());
                        scanner.nextLine();
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        ConteudoBean conteudo = new ConteudoBean();
                        System.out.print("Título: ");
                        conteudo.setTitulo(scanner.nextLine());
                        System.out.print("Ano de lançamento: ");
                        conteudo.setAnoLancamento(scanner.nextInt());
                        scanner.nextLine();
                        System.out.print("Classificação etária: ");
                        conteudo.setClassificacaoEtaria(scanner.nextLine());
                        System.out.print("Sinopse: ");
                        conteudo.setSinopse(scanner.nextLine());
                        System.out.print("ID do administrador: ");
                        conteudo.setIdUsuario(scanner.nextInt());
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        AvaliacaoBean avaliacao = new AvaliacaoBean();
                        System.out.print("ID do usuário: ");
                        avaliacao.setIdUsuario(scanner.nextInt());
                        System.out.print("ID do conteúdo: ");
                        avaliacao.setIdConteudo(scanner.nextInt());
                        System.out.print("Nota (0-10): ");
                        avaliacao.setNota(scanner.nextDouble());
                        scanner.nextLine();
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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        HistoricoBean historico = new HistoricoBean();
                        System.out.print("ID do usuário: ");
                        historico.setIdUsuario(scanner.nextInt());
                        System.out.print("ID do conteúdo: ");
                        historico.setIdConteudo(scanner.nextInt());
                        System.out.print("Progresso (0-100): ");
                        historico.setProgresso(scanner.nextDouble());
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
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}
