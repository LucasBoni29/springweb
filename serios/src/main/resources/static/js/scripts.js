// Lista de usuários (apenas para exemplo)
let usuarios = [
    { id: 1, nome: 'Usuário 1', email: 'usuario1@example.com', grupo: 'Administrador', status: 'Ativo' },
    { id: 2, nome: 'Usuário 2', email: 'usuario2@example.com', grupo: 'Usuário Comum', status: 'Ativo' },
    { id: 3, nome: 'Usuário 3', email: 'usuario3@example.com', grupo: 'Administrador', status: 'Inativo' }
];

// Função para exibir os usuários na tabela
function exibirUsuarios(usuariosParaExibir) {
    const usuariosContainer = document.getElementById('usuarios');
    usuariosContainer.innerHTML = '';

    const tabela = document.createElement('table');
    tabela.classList.add('tabela-usuarios');

    const cabecalho = tabela.createTHead();
    const cabecalhoRow = cabecalho.insertRow();
    cabecalhoRow.innerHTML = `
        <th>Nome</th>
        <th>Email</th>
        <th>Grupo</th>
        <th>Status</th>
        <th>Ações</th>
    `;

    const corpoTabela = tabela.createTBody();
    usuariosParaExibir.forEach(usuario => {
        const linha = corpoTabela.insertRow();
        linha.innerHTML = `
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
            <td>${usuario.grupo}</td>
            <td>${usuario.status}</td>
            <td>
                <button class="botao-alterar" data-id="${usuario.id}">Alterar usuário </button>
                <button class="botao-inativar" data-id="${usuario.id}" data-status="${usuario.status}">${usuario.status === 'Ativo' ? 'Inativar' : 'Reativar'}</button>
            </td>
        `;
    });

    usuariosContainer.appendChild(tabela);

    // Adicionar evento de clique aos botões de inativar/reativar
    document.querySelectorAll('.botao-inativar').forEach(botao => {
        botao.addEventListener('click', function() {
            const userId = this.dataset.id;
            const usuario = usuarios.find(u => u.id == userId);
            usuario.status = usuario.status === 'Ativo' ? 'Inativo' : 'Ativo';
            exibirUsuarios(usuarios);
        });
    });

    // Adicionar evento de clique aos botões de alterar
    document.querySelectorAll('.botao-alterar').forEach(botao => {
        botao.addEventListener('click', function() {
            const userId = this.dataset.id;
            // Redirecionar para a página de edição com o ID do usuário
            window.location.href = `editarUsuario.html?userId=${userId}`;
        });
    });
}

// Função para filtrar usuários pelo nome
function filtrarUsuariosPorNome(nome) {
    const filtro = nome.toLowerCase();
    const usuariosFiltrados = usuarios.filter(usuario => usuario.nome.toLowerCase().includes(filtro));
    exibirUsuarios(usuariosFiltrados);
}

// Função para cadastrar uma nova pessoa
document.getElementById('formulario').addEventListener('reset', function(event) {
    event.preventDefault(); // Evita o envio do formulário padrão
    
    const formData = new FormData(this); // Obtém os dados do formulário

    // Constrói um objeto com os dados do formulário
    const novaPessoa = {
        nome: formData.get('nome'),
        email: formData.get('email'),
        grupo: formData.get('grupo'),
        status: formData.get('status')
    };

    // Aqui você pode adicionar a lógica para salvar a nova pessoa, por exemplo, em um banco de dados
    // No exemplo abaixo, estamos apenas adicionando a nova pessoa à lista de usuários em memória
    usuarios.push(novaPessoa);

    // Exibe uma mensagem de sucesso (você pode alterar isso conforme necessário)
    alert('Pessoa cadastrada com sucesso!');
    
    // Limpa o formulário após o envio
    this.reset();

    // Reexibe a lista de usuários para incluir a nova pessoa
    exibirUsuarios(usuarios);
});

// Exibir os usuários ao carregar a página
exibirUsuarios(usuarios);

// Alternar a visibilidade do formulário de cadastro ao clicar no botão "Cadastrar Usuário"
document.getElementById('cadastrarUsuario').addEventListener('click', function() {
    const formulario = document.getElementById('formulario');
    formulario.style.display = formulario.style.display === 'none' ? 'block' : 'none';
});

// Adicionar evento de input ao campo de filtro para filtrar os usuários pelo nome
document.getElementById('filtro').addEventListener('input', function() {
    filtrarUsuariosPorNome(this.value);
});
