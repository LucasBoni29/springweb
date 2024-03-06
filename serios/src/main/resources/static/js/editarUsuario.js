const mysql = require('mysql2');

// Configurações de conexão com o banco de dados
const connection = mysql.createConnection({
    host: 'localhost',
    user: '',
    password: '',
    database: 'usuarios'
});

// Função para carregar os usuários do banco de dados
function carregarUsuarios(callback) {
    connection.query(
        'SELECT * FROM usuarios',
        function(err, results, fields) {
            if (err) {
                console.error('Erro ao executar a consulta:', err);
                return callback(err, null);
            }
            callback(null, results);
        }
    );
}

// Função para salvar os usuários no banco de dados
function salvarUsuarios(usuarios, callback) {
    // Implemente a lógica para salvar os usuários no banco de dados
    // Por exemplo, você pode usar a instrução INSERT INTO
}

// Função para exibir os usuários na tabela
function exibirUsuarios() {
    carregarUsuarios(function(err, usuarios) {
        if (err) {
            console.error('Erro ao carregar usuários:', err);
            return;
        }

        const usuariosContainer = document.getElementById('usuarios');
        usuariosContainer.innerHTML = '';

        const tabela = document.createElement('table');
        tabela.classList.add('tabela-usuarios');

        const cabecalho = tabela.createTHead();
        const cabecalhoRow = cabecalho.insertRow();
        cabecalhoRow.innerHTML = `
            <th>Nome</th>
            <th>Email</th>
            <th>Status</th>
            <th>Grupo</th>
            <th>Ações</th>
        `;

        const corpoTabela = tabela.createTBody();
        usuarios.forEach(usuario => {
            const linha = corpoTabela.insertRow();
            linha.innerHTML = `
                <td>${usuario.nome}</td>
                <td>${usuario.email}</td>
                <td>${usuario.status}</td>
                <td>${usuario.grupo}</td>
                <td>
                    <button class="botao-alterar" data-id="${usuario.id}">Alterar</button>
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
                salvarUsuarios(usuarios, function(err) {
                    if (err) {
                        console.error('Erro ao salvar usuários:', err);
                        return;
                    }
                    exibirUsuarios(); // Atualiza a exibição da tabela após a alteração
                });
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
    });
}

// Exibir os usuários ao carregar a página
exibirUsuarios();