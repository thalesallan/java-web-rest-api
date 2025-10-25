# 🔧 CORREÇÃO: Erro de Permissões GitHub Actions

## ❌ **Problema Identificado:**
```
remote: Permission to thalesallan/java-web-rest-api.git denied to github-actions[bot]
```

O GitHub Actions não tem permissão para criar releases no seu repositório.

## ✅ **SOLUÇÃO - Configure Permissões no Repositório:**

### 🎯 **Passo 1: Configurar Permissões do GitHub Actions**

1. **Vá para seu repositório no GitHub**
   ```
   https://github.com/thalesallan/java-web-rest-api
   ```

2. **Acesse Settings**
   - Clique na aba **"Settings"**
   - No menu lateral, clique em **"Actions"**
   - Clique em **"General"**

3. **Configure Workflow Permissions**
   - Procure por **"Workflow permissions"**
   - Selecione: **"Read and write permissions"**
   - ✅ Marque: **"Allow GitHub Actions to create and approve pull requests"**
   - Clique em **"Save"**

### 🎯 **Passo 2: Verificar Token Permissions**

No mesmo menu **Settings > Actions > General**:
- Verifique se **"Allow actions and reusable workflows"** está selecionado
- Confirme que **"Allow all actions and reusable workflows"** está marcado

### 🎯 **Passo 3: Criar Release Manual (Teste)**

Para testar se está funcionando, você pode criar uma release manual:

```powershell
# 1. Fazer um commit conventional
git add .
git commit -m "feat(release): configurar sistema de releases automáticas

Implementa:
- Workflows de CI/CD completos
- Sistema de conventional commits  
- Releases automáticas com semantic versioning
- Documentação completa do processo

BREAKING CHANGE: Implementação inicial do sistema de releases"

# 2. Push para main
git push origin main
```

## 🔍 **O que foi corrigido no workflow:**

### ✅ **Permissões Adicionadas:**
```yaml
permissions:
  contents: write     # Para criar releases
  issues: write       # Para comentar issues
  pull-requests: write # Para comentar PRs
```

### ✅ **Verificação Inteligente:**
- Só cria release se houver commits `feat:`, `fix:` ou `BREAKING CHANGE`
- Evita releases desnecessárias
- Pula se commit contém `[skip ci]`

### ✅ **Configuração Simplificada:**
- Removidas dependências problemáticas (`@semantic-release/git`)
- Configuração mais estável e confiável
- Instalação local dos pacotes npm

## 🚀 **Próximos Passos:**

1. **Configure as permissões** no GitHub (passos acima)
2. **Faça um commit conventional** de teste
3. **Verifique se a release é criada** automaticamente
4. **Confirme se o pipeline de CI/CD roda** após a release

## ⚠️ **Se ainda der erro:**

### **Alternativa 1: Personal Access Token**
Se as permissões não funcionarem, você pode criar um PAT:

1. GitHub > Settings > Developer settings > Personal access tokens
2. Generate new token (classic)
3. Scope: `repo`, `write:packages`
4. Adicionar como secret: `GITHUB_TOKEN`

### **Alternativa 2: Workflow Manual**
Posso criar um workflow que só executa quando você disparar manualmente.

---

**🎯 Configure as permissões e teste! O sistema está pronto para funcionar.** 🚀
