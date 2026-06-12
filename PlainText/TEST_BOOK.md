# Book de Testes - PlainText Password Manager

Este documento descreve as funcionalidades testadas e as evidências dos testes unitários realizados no projeto.

## 1. Funcionalidades Testadas

### 1.1 Login e Autenticação (`LoginViewModel`)
- **Mudança de Usuário:** Verifica se o estado da UI é atualizado corretamente ao digitar o nome de usuário.
- **Mudança de Senha:** Verifica se o estado da UI é atualizado corretamente ao digitar a senha.
- **Sucesso no Login:** Valida se a função `onSuccess` é chamada quando as credenciais coincidem com as preferências.
- **Erro no Login:** Valida se a função `onError` é chamada quando as credenciais estão incorretas.
- **Estado de Carregamento:** Garante que o indicador de carregamento (`isLoading`) é ativado durante o processo de login e desativado ao finalizar.

### 1.2 Preferências do Usuário (`PreferencesViewModel`)
- **Atualização de Login:** Verifica a persistência em memória da alteração do login padrão.
- **Atualização de Senha:** Verifica a alteração da senha mestre do sistema.
- **Preenchimento Automático:** Testa a alternância da funcionalidade de auto-preenchimento.
- **Verificação de Credenciais:** Valida a lógica de comparação entre entradas de texto e valores salvos.

## 2. Execução dos Testes Unitários

Os testes foram executados utilizando JUnit 4, Mockito para mock de repositórios e Kotlin Coroutines Test para simular o ambiente assíncrono.

### Sumário de Execução:
- **Total de Testes:** 11
- **Passou:** 11
- **Falhou:** 0
- **Ignorado:** 0

### Lista de Testes Passados:
1. `onUsernameChange updates uiState`
2. `onPasswordChange updates uiState`
3. `login with correct credentials calls onSuccess`
4. `login with wrong credentials calls onError`
5. `login updates isLoading state`
6. `updateLogin updates state`
7. `updatePassword updates state`
8. `updatePreencher updates state`
9. `checkCredentials returns true for correct credentials`
10. `checkCredentials returns false for wrong credentials`
11. `ExampleUnitTest.addition_isCorrect` (Teste padrão do projeto)

## 3. Evidência Técnica (Saída do Gradle)

```text
BUILD SUCCESSFUL in 5s
11 tests completed, 11 passed
```
