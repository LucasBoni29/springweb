package br.com.senac.serios.service;

import br.com.senac.serios.data.domain.entity.ProdutoEntity;
import br.com.senac.serios.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProdutoService {

    void editarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens)throws IOException;

    void salvarImagensProdutoEdicao(ProdutoEntity produtoEntity, List<MultipartFile> imagens, int indiceImagemPrincipal) throws IOException;

    Page<ProdutoDTO> listarProdutosNomePaginado(String keyword, Pageable pageable);

    void criarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) throws IOException;

    void salvarImagensProduto(ProdutoEntity produtoEntity, List<MultipartFile> imagens, int indiceImagemPrincipal) throws IOException;

    String[] salvarImagens(List<MultipartFile> imagens) throws IOException;

    void mudarStatusProduto(Long id);

    ProdutoDTO buscarProdutoPorId(Long id);

    Page<ProdutoDTO> listarProdutosPorNomePaginado(String keyword, Pageable pageable);
}
