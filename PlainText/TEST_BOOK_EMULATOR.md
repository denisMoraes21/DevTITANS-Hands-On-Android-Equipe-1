# Book de Testes - Execução em Emulador (Atualizado)

Este documento detalha o plano de teste executado no emulador para validar a experiência do usuário, navegação e novas funcionalidades de segurança do aplicativo PlainText Password Manager.

## 1. Ambiente de Teste
- **Dispositivo:** Emulador Android (Pixel 7a)
- **Versão do SO:** Android 14 (API 34/35)
- **Branch:** `code-review`

## 2. Funcionalidades de Navegação e Menu

### 2.1 Menu de Opções Global (TopBar)
- **Ação:** Clicar no ícone de três pontos em qualquer tela principal.
- **Resultado Esperado:** Exibir as opções "Configurações", "Logout", "Sobre" e "Sair".
- **Evidência:** O menu suspenso foi validado na tela de Lista com todas as quatro opções visíveis e funcionais.
- **Status:** PASSOU

### 2.2 Botão de Voltar (Navegação Hierárquica)
- **Ação:** Entrar na tela de "Configurações" ou "Editar Senha".
- **Resultado Esperado:** Exibir um ícone de seta no canto superior esquerdo para retornar à tela anterior.
- **Evidência:** O ícone de "Voltar" foi adicionado com sucesso e permite a navegação de retorno para a tela de Lista.
- **Status:** PASSOU

## 3. Novas Funcionalidades de Segurança e UX

### 3.1 Confirmação de Exclusão (Diálogo)
- **Ação:** Clicar no botão "Deletar" dentro da edição de uma senha.
- **Resultado Esperado:** Exibir um diálogo de confirmação ("AlertDialog") impedindo a exclusão acidental.
- **Evidência:** O diálogo "Confirmar Exclusão" aparece com as opções "Cancelar" e "Excluir".
- **Status:** PASSOU

### 3.2 Fluxo de Logout
- **Ação:** Selecionar "Logout" no menu.
- **Resultado Esperado:** Retornar para a tela de Login e limpar a pilha de navegação.
- **Evidência:** A navegação retornou para a tela inicial de autenticação conforme esperado.
- **Status:** PASSOU

### 3.3 Fluxo de Sair (Fechar App)
- **Ação:** Selecionar "Sair" no menu.
- **Resultado Esperado:** Encerrar a Activity principal e fechar o aplicativo.
- **Evidência:** O aplicativo foi fechado corretamente, retornando ao launcher do Android.
- **Status:** PASSOU

### 3.4 Estado de Lista Vazia
- **Ação:** Abrir a tela de Lista sem senhas cadastradas.
- **Resultado Esperado:** Exibir mensagem informativa em vez de tela vazia.
- **Evidência:** Mensagem "Nenhuma senha salva ainda." validada visualmente.
- **Status:** PASSOU

## 4. Screenshots de Referência (Descrição das Capturas)

| Funcionalidade | Evidência Visual Capturada |
| :--- | :--- |
| **Menu Expandido** | Exibe as opções de Configurações, Logout, Sobre e Sair. |
| **Botão Voltar** | Ícone de seta visível no TopBar das telas secundárias. |
| **Confirmação** | Modal centralizado com mensagem de alerta e botões de ação. |
| **Login (Auto-fill)** | Campos preenchidos automaticamente com base nas preferências. |

---
*Testes realizados e validados via inspeção de UI State e inspeção visual no emulador.*
