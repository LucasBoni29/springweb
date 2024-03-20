// Simulação de dados de produtos (pode ser substituído pela lógica de backend em Spring Boot)
const products = [
    { codigo: 1, nome: "Smartphone", quantidade: 50, valor: 1000.00, status: "ativo" },
    { codigo: 2, nome: "Smart TV", quantidade: 20, valor: 2000.00, status: "ativo" },
    { codigo: 3, nome: "Smartwatch", quantidade: 30, valor: 500.00, status: "ativo" },
    { codigo: 4, nome: "Tablet", quantidade: 10, valor: 800.00, status: "desativado" },
    { codigo: 5, nome: "Smart Speaker", quantidade: 15, valor: 150.00, status: "ativo" }
];

// Função para exibir a lista de produtos
function displayProducts(products) {
    const productList = document.getElementById("productList");
    productList.innerHTML = "";

    products.forEach(product => {
        const productElement = document.createElement("div");
        productElement.classList.add("product");
        productElement.innerHTML = `
            <span>Código: ${product.codigo}</span><br>
            <span>Nome: ${product.nome}</span><br>
            <span>Quantidade: ${product.quantidade}</span><br>
            <span>Valor: R$ ${product.valor.toFixed(2)}</span><br>
            <span>Status: ${product.status}</span><br>
            <a href="#" onclick="editProduct(${product.codigo})">Editar</a>
        `;
        productList.appendChild(productElement);
    });
}

// Função para buscar produtos
function searchProducts() {
    const searchInput = document.getElementById("searchInput");
    const searchTerm = searchInput.value.toLowerCase();

    const filteredProducts = products.filter(product =>
        product.nome.toLowerCase().includes(searchTerm)
    );

    displayProducts(filteredProducts);
}

// Função para simular edição de produto (pode ser substituída pela lógica de backend)
function editProduct(codigo) {
    alert(`Editar produto de código ${codigo}`);
}

// Exibir todos os produtos ao carregar a página
displayProducts(products);
