package Controle;

import Modelo.Categoria;
import http.ApiClient;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

public class CategoriaControle {
    private final ApiClient apiClient;
    private List<Categoria> cacheCategorias;

    public CategoriaControle() {
        this.apiClient = new ApiClient();
        this.cacheCategorias = null;
    }

    /**
     * Obter todas as categorias (com cache)
     */
    public List<Categoria> obterTodasCategorias() {
        // Retorna do cache se disponível
        if (cacheCategorias != null && !cacheCategorias.isEmpty()) {
            return cacheCategorias;
        }
        
        try {
            List<Categoria> categorias = apiClient.obterCategorias();
            
            if (categorias != null && !categorias.isEmpty()) {
                cacheCategorias = categorias;
                return categorias;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Nenhuma categoria encontrada no servidor.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return new ArrayList<>();
            }
        } catch (Exception e) {
            exibirErro("Erro ao carregar categorias", e);
            
            // Retorna lista vazia em caso de erro
            return new ArrayList<>();
        }
    }

    /**
     * Obter apenas nomes das categorias (para combobox)
     */
    public List<String> obterNomesCategorias() {
        List<Categoria> categorias = obterTodasCategorias();
        List<String> nomes = new ArrayList<>();
        
        for (Categoria cat : categorias) {
            nomes.add(cat.getNome());
        }
        
        return nomes;
    }

    /**
     * Cadastrar nova categoria (sem descrição)
     */
    public boolean cadastrarCategoria(String nome) {
        try {
            // Validação básica
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Nome da categoria é obrigatório!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Usando o ApiClient para cadastrar, passando apenas o nome
            // O id é gerado pelo backend
            Categoria novaCategoria = new Categoria(null, nome);
            Categoria categoriaSalva = apiClient.cadastrarCategoria(novaCategoria);
            
            // Limpa cache para forçar atualização
            cacheCategorias = null;
            
            JOptionPane.showMessageDialog(null,
                "Categoria cadastrada com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            exibirErro("Erro ao cadastrar categoria", e);
            return false;
        }
    }

    /**
     * Excluir categoria por ID
     */
    public boolean excluirCategoria(Long id) {
        try {
            if (id == null) {
                JOptionPane.showMessageDialog(null,
                    "ID da categoria é obrigatório!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Confirmação
            int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir esta categoria?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmacao != JOptionPane.YES_OPTION) {
                return false;
            }
            
            apiClient.excluirCategoria(id);
            
            // Limpa cache
            cacheCategorias = null;
            
            JOptionPane.showMessageDialog(null,
                "Categoria excluída com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            exibirErro("Erro ao excluir categoria", e);
            return false;
        }
    }

    /**
     * Buscar categoria por ID
     */
    public Categoria buscarCategoriaPorId(Long id) {
        if (id == null) return null;
        
        List<Categoria> categorias = obterTodasCategorias();
        for (Categoria cat : categorias) {
            if (cat.getId() != null && cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }

    /**
     * Buscar categoria por nome
     */
    public Categoria buscarCategoriaPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;
        
        List<Categoria> categorias = obterTodasCategorias();
        for (Categoria cat : categorias) {
            if (cat.getNome() != null && cat.getNome().equalsIgnoreCase(nome)) {
                return cat;
            }
        }
        return null;
    }

    /**
     * Verificar se categoria existe pelo nome
     */
    public boolean categoriaExiste(String nome) {
        return buscarCategoriaPorNome(nome) != null;
    }

    /**
     * Limpar cache de categorias
     */
    public void limparCache() {
        cacheCategorias = null;
    }

    /**
     * Testar conexão com servidor
     */
    public boolean testarConexao() {
        try {
            // Tenta obter categorias
            List<Categoria> categorias = apiClient.obterCategorias();
            return categorias != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método auxiliar para exibir erros
     */
    private void exibirErro(String titulo, Exception e) {
        String mensagem = e.getMessage();
        if (mensagem == null || mensagem.isEmpty()) {
            mensagem = "Erro desconhecido. Verifique a conexão com o servidor.";
        }
        
        System.err.println("ERRO em " + titulo + ": " + mensagem);
        
        JOptionPane.showMessageDialog(null,
            titulo + ":\n" + mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Método para obter categorias para JComboBox
     */
    public Categoria[] obterCategoriasArray() {
        List<Categoria> categorias = obterTodasCategorias();
        return categorias.toArray(new Categoria[0]);
    }
    
    /**
     * Método para atualizar categoria
     */
    public boolean atualizarCategoria(Categoria categoria) {
        try {
            if (categoria.getId() == null) {
                JOptionPane.showMessageDialog(null,
                    "ID da categoria é obrigatório para atualização!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Nome da categoria é obrigatório!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Usando o ApiClient para atualizar
            apiClient.atualizarCategoria(categoria.getId(), categoria);
            
            // Limpa cache
            cacheCategorias = null;
            
            JOptionPane.showMessageDialog(null,
                "Categoria atualizada com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            exibirErro("Erro ao atualizar categoria", e);
            return false;
        }
    }
}