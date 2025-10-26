package Controle;

import DAO.FerramentasDAO;
import Modelo.Ferramentas;
import java.sql.SQLException;
import java.util.List;

public class FerramentasControle {

    // Referência para o objeto FerramentasDAO que será utilizado para acessar o banco de dados
    private FerramentasDAO ferramentaDAO;

    // Construtor da classe que recebe um objeto FerramentasDAO como parâmetro
    public FerramentasControle(FerramentasDAO ferramentaDAO) {
        this.ferramentaDAO = ferramentaDAO;
    }

    // Método para adicionar uma ferramenta ao banco de dados
    public void adicionarFerramenta(Ferramentas ferramenta) throws SQLException {
        ferramentaDAO.adicionarFerramenta(ferramenta);
    }

    // Método para listar todas as ferramentas cadastradas no banco de dados
    public List<Ferramentas> listarFerramentas() throws SQLException {
        return ferramentaDAO.listarFerramentas();
    }

    // Método para atualizar os dados de uma ferramenta no banco de dados
    public void atualizarFerramenta(Ferramentas ferramenta) throws SQLException {
        ferramentaDAO.atualizarFerramenta(ferramenta);
    }

    // Método para deletar uma ferramenta do banco de dados com base no seu ID
    public void deletarFerramenta(int id) throws SQLException {
        ferramentaDAO.deletarFerramenta(id);
    }
}
