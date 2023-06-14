import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RedeSocial implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel emailLabel;
    private JLabel senhaLabel;
    private JTextField emailTextField;
    private JPasswordField senhaPasswordField;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton cadastrarButton;
    private JButton incluirAmigoButton;
    private JButton consultarAmigosButton;
    private JButton excluirAmigoButton;
    private JButton enviarMensagemButton;

    private List<Usuario> usuarios;
    private Usuario usuarioAtual;

    public RedeSocial() {
        frame = new JFrame("Mini Simulador de Rede Social");
        panel = new JPanel();

        emailLabel = new JLabel("Email:");
        senhaLabel = new JLabel("Senha:");
        emailTextField = new JTextField(20);
        senhaPasswordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");
        cadastrarButton = new JButton("Cadastrar");
        incluirAmigoButton = new JButton("Incluir Amigo");
        consultarAmigosButton = new JButton("Consultar Amigos");
        excluirAmigoButton = new JButton("Excluir Amigo");
        enviarMensagemButton = new JButton("Enviar Mensagem");

        loginButton.addActionListener(this);
        logoutButton.addActionListener(this);
        cadastrarButton.addActionListener(this);
        incluirAmigoButton.addActionListener(this);
        consultarAmigosButton.addActionListener(this);
        excluirAmigoButton.addActionListener(this);
        enviarMensagemButton.addActionListener(this);

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(senhaLabel);
        panel.add(senhaPasswordField);
        panel.add(loginButton);
        panel.add(logoutButton);
        panel.add(cadastrarButton);
        panel.add(incluirAmigoButton);
        panel.add(consultarAmigosButton);
        panel.add(excluirAmigoButton);
        panel.add(enviarMensagemButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        usuarios = new ArrayList<>();
        usuarioAtual = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RedeSocial();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailTextField.getText();
            String senha = new String(senhaPasswordField.getPassword());
            login(email, senha);
        } else if (e.getSource() == logoutButton) {
            logout();
        } else if (e.getSource() == cadastrarButton) {
            cadastrarUsuario();
        } else if (e.getSource() == incluirAmigoButton) {
            incluirAmigo();
        } else if (e.getSource() == consultarAmigosButton) {
            consultarAmigos();
        } else if (e.getSource() == excluirAmigoButton) {
            excluirAmigo();
        } else if (e.getSource() == enviarMensagemButton) {
            enviarMensagem();
        }
    }

    private void login(String email, String senha) {
        // Lógica para verificar o login
        // Aqui você pode fazer a autenticação e carregar o usuário atual
        // Exemplo:
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                usuarioAtual = u;
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                break;
            }
        }
        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(frame, "Falha no login. Verifique seu email e senha.");
        }
    }

    private void logout() {
        usuarioAtual = null;
        JOptionPane.showMessageDialog(frame, "Logout realizado com sucesso!");
    }

    private void cadastrarUsuario() {
        // Lógica para cadastrar um novo usuário
        // Aqui você pode abrir uma nova janela de cadastro ou utilizar JOptionPane
        // Exemplo:
        String nome = JOptionPane.showInputDialog(frame, "Nome:");
        String email = JOptionPane.showInputDialog(frame, "Email:");
        String senha = JOptionPane.showInputDialog(frame, "Senha:");
        // Outros dados a definir

        Usuario novoUsuario = new Usuario(nome, email, senha);
        usuarios.add(novoUsuario);
        JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso!");
    }

    private void incluirAmigo() {
        // Lógica para incluir um amigo
        // Aqui você pode abrir uma nova janela de seleção de amigos ou utilizar JOptionPane
        // Exemplo:
        String emailAmigo = JOptionPane.showInputDialog(frame, "Email do amigo:");
        Usuario amigo = buscarUsuarioPorEmail(emailAmigo);
        if (amigo != null) {
            usuarioAtual.adicionarAmigo(amigo);
            JOptionPane.showMessageDialog(frame, "Amigo incluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Amigo não encontrado.");
        }
    }

    private void consultarAmigos() {
        // Lógica para consultar os amigos do usuário atual
        // Aqui você pode exibir a lista de amigos em uma janela ou utilizar JOptionPane
        // Exemplo:
        StringBuilder mensagem = new StringBuilder();
        if (usuarioAtual.getAmigos().size() > 0) {
            mensagem.append("Amigos do usuário:\n");
            for (Usuario amigo : usuarioAtual.getAmigos()) {
                mensagem.append(amigo.getNome()).append(" (").append(amigo.getEmail()).append(")\n");
            }
        } else {
            mensagem.append("Nenhum amigo encontrado.");
        }
        JOptionPane.showMessageDialog(frame, mensagem.toString());
    }

    private void excluirAmigo() {
        // Lógica para excluir um amigo
        // Aqui você pode abrir uma nova janela de seleção de amigos ou utilizar JOptionPane
        // Exemplo:
        String emailAmigo = JOptionPane.showInputDialog(frame, "Email do amigo a ser excluído:");
        Usuario amigo = buscarUsuarioPorEmail(emailAmigo);
        if (amigo != null) {
            if (usuarioAtual.removerAmigo(amigo)) {
                JOptionPane.showMessageDialog(frame, "Amigo removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(frame, "O usuário atual não possui esse amigo.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Amigo não encontrado.");
        }
    }

    private void enviarMensagem() {
        // Lógica para enviar uma mensagem para um amigo
        // Aqui você pode abrir uma nova janela de seleção de amigos e digitação da mensagem ou utilizar JOptionPane
        // Exemplo:
        String emailAmigo = JOptionPane.showInputDialog(frame, "Email do amigo para enviar a mensagem:");
        Usuario amigo = buscarUsuarioPorEmail(emailAmigo);
        if (amigo != null) {
            String mensagem = JOptionPane.showInputDialog(frame, "Digite a mensagem:");
            // Lógica para enviar a mensagem para o amigo
            JOptionPane.showMessageDialog(frame, "Mensagem enviada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Amigo não encontrado.");
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
}
