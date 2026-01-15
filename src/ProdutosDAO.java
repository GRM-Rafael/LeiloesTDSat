/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public int cadastrarProduto(ProdutosDTO produto) {
        int status;
        conectaDAO conectaDao = new conectaDAO();
        try {

            conn = conectaDao.connectDB();
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            status = prep.executeUpdate();
            conectaDao.desconnectDB();
            return status;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Cadastrar: " + e.getMessage());
            return e.getErrorCode();

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conectaDAO conectadao = new conectaDAO();
        String sql = "SELECT * FROM produtos";
        try {
            conn = conectadao.connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
            return listagem;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar! \n" + e.getMessage());
            return null;

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
            }
        }

    }
    
    public ProdutosDTO consultaId(int id){
        conectaDAO conectaDao = new conectaDAO();
        try {
            ProdutosDTO produto = new ProdutosDTO();
            conn = conectaDao.connectDB();
            
            prep = conn.prepareStatement("SELECT * from produtos WHERE id = ?");
            prep.setInt(1, id);
            
            resultset = prep.executeQuery();
            if(resultset.next()){
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                return produto;
                
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            return null;
            
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
            }
        }
        
    }

    public int venderProduto(ProdutosDTO produto) {
        int status;
        conectaDAO conectaDao = new conectaDAO();
        try {
            conn = conectaDao.connectDB();
            prep = conn.prepareStatement("UPDATE produtos SET nome = ?, valor = ?, status = ? WHERE id = ?");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.setInt(4, produto.getId());
            status = prep.executeUpdate();

            return status;

        } catch (SQLException e) {
            return e.getErrorCode();
            
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
            }
        }
    }
    
        public ArrayList<ProdutosDTO> listarProdutosVendidos(String filtro) {
        conectaDAO conectadao = new conectaDAO();
        String sql = "SELECT * FROM produtos WHERE status = ?";
        try {
            conn = conectadao.connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, filtro);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
            return listagem;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar! \n" + e.getMessage());
            return null;

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
            }
        }

    }

}
