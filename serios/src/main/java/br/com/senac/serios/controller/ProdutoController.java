package br.com.senac.serios.controller;

import br.com.senac.serios.dto.ImagemProdutoDTO;
import br.com.senac.serios.dto.ProdutoDTO;
import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.service.ProdutoService;
import br.com.senac.serios.service.impl.ProdutoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProdutoController {

    private static final String UPLOAD_DIR = "src/main/resources/static/imagem";

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoServiceImpl serviceImpl){
        this.produtoService = serviceImpl;
    }

    /* Acessar a página de edição de produto */
    @GetMapping("/produtos/{id}/editar")
    public String mostrarFormularioEdicaoProduto(@PathVariable Long id, Model model, HttpSession session) {

        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        int indexImagemPrincipal = produtoDTO.getImagemPrincipal();

        ImagemProdutoDTO imagemPrincipal = produtoDTO.getImagens().get(indexImagemPrincipal);
        produtoDTO.setImagemPrincipalString(imagemPrincipal.getCaminho());

        model.addAttribute("usuarioLogado", usuarioLogado.getGrupo());
        // Carregar as imagens existentes do produto e adicionar ao modelo
        model.addAttribute("produtoDTO", produtoDTO);
        model.addAttribute("selectedImagePath", imagemPrincipal.getCaminho());

        return "editar-Produto";
    }

    /*esse que vai mudar o produto*/
    @PostMapping("/produtos/editar")
    public String editarProduto(@Valid @ModelAttribute("produtoDTO") ProdutoDTO produtoDTO,
            BindingResult result,
            @RequestParam("imagens") List<MultipartFile> imagens,
            RedirectAttributes attributes) throws IOException {
        produtoService.editarProduto(produtoDTO, imagens);

        attributes.addFlashAttribute("mensagem", "Produto editado com sucesso!");
        return "redirect:/produtos";
    }

    /*listar os produtos*/
    @GetMapping("/produtos")
    public String listarProdutos(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword, Principal principal) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        String tipoUsuario = usuarioLogado.getGrupo();

        model.addAttribute("tipoUsuario", tipoUsuario);

        Page<ProdutoDTO> produtosPage = produtoService.listarProdutosPorNomePaginado(keyword, PageRequest.of(page, 10, Sort.by("id").descending()));
        model.addAttribute("produtosPage", produtosPage);
        model.addAttribute("keyword", keyword);
        return "listarProduto";
    }

    /*acessar a pagina de criaçao de produto*/
    @GetMapping("/produtos/novo")
    public String mostrarFormularioCriacaoProduto(Model model) {
        model.addAttribute("produtoDTO", new ProdutoDTO());
        return "criarProduto";
    }

    /*mandando a solicçao post para criar o produto*/
    @PostMapping("/criarproduto")
    public String criarProduto(@Valid @ModelAttribute("produtoDTO") ProdutoDTO produtoDTO,
            BindingResult result,
            @RequestParam("imagens") List<MultipartFile> imagens) throws IOException {

        produtoService.criarProduto(produtoDTO, imagens); // Handle file processing errors

        return "redirect:/produtos";
    }

    /*Alterar status produto*/
    @PostMapping("/produtos/mudar-status") // Mapeado para requisições POST
    public String mudarStatusProduto(@RequestParam Long id, RedirectAttributes attributes) {
        try {
            // Chama o método da service para mudar o status do produto
            produtoService.mudarStatusProduto(id);
            attributes.addFlashAttribute("mensagem", "Produto alterado com sucesso!");
        } catch (EntityNotFoundException ex) {
            attributes.addFlashAttribute("mensagem", "Produto não encontrado!");
        }
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/{id}")
    public String visualizarProduto(@PathVariable Long id, Model model) {
        ProdutoDTO produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            model.addAttribute("produto", produto);

            if (!produto.getImagens().isEmpty()) {
                model.addAttribute("imagens", produto.getImagens());
            } else {
                model.addAttribute("imagens", new ArrayList<>());
            }

            // Corrigido: Adicionar o caminho absoluto das imagens ao modelo
            model.addAttribute("imagemAbsolutaPath", UPLOAD_DIR);

            return "visualizarProduto";
        } else {
            return "redirect:/pagina-de-erro";
        }
    }
}
