# GitHub Actions & Maven Package Publishing Setup

## 📋 Overview

This document explains the complete setup for building and publishing your Maven package to GitHub Packages using GitHub Actions.

---

## 🎯 What Was Set Up

### 1. **GitHub Actions Workflow** (`.github/workflows/maven-publish.yml`)
Complete CI/CD pipeline with:
- ✅ Build & Test job
- ✅ Publish to GitHub Packages job
- ✅ Code Quality checks
- ✅ Security scanning

### 2. **Maven Configuration** (Updated `pom.xml`)
- ✅ Distribution Management for GitHub Packages
- ✅ Repository configuration
- ✅ Snapshot repository setup

### 3. **Maven Settings** (`settings.xml`)
- ✅ GitHub Packages repository credentials
- ✅ Environment variable configuration
- ✅ Profile setup

---

## 📦 GitHub Packages Setup

### What is GitHub Packages?

GitHub Packages is a software package hosting service that allows you to:
- Publish Maven packages privately or publicly
- Use packages as dependencies in other projects
- Integrate with GitHub Actions

### Prerequisites

1. **GitHub Repository** - Push your code to GitHub
2. **GitHub Token** - Generate a Personal Access Token (PAT) with `write:packages` scope
3. **Java 17** - Installed and configured

---

## 🔧 Configuration Files

### 1. `.github/workflows/maven-publish.yml`

**What it does:**
- Triggers on: Release creation, push to main/develop, pull requests
- Runs tests and builds the package
- Publishes to GitHub Packages on release or main push
- Performs code quality and security checks

**Key Jobs:**

```yaml
build:          # Compiles, tests, and packages the application
publish:        # Publishes to GitHub Packages
code-quality:   # Runs checkstyle and test reports
security-scan:  # OWASP dependency checks
```

### 2. `pom.xml` Changes

**Added Distribution Management:**
```xml
<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/arvind-sisodiya/ai-core</url>
    </repository>
    <snapshotRepository>
        <id>github</id>
        <name>GitHub Packages Snapshot</name>
        <url>https://maven.pkg.github.com/arvind-sisodiya/ai-core</url>
    </snapshotRepository>
</distributionManagement>
```

**Added Properties:**
```xml
<properties>
    <github.repository>arvind-sisodiya/ai-core</github.repository>
</properties>
```

### 3. `settings.xml`

**Maven authentication configuration:**
```xml
<server>
    <id>github</id>
    <username>${env.GITHUB_ACTOR}</username>
    <password>${env.GITHUB_TOKEN}</password>
</server>
```

Uses environment variables:
- `GITHUB_ACTOR` - GitHub username
- `GITHUB_TOKEN` - Personal Access Token

---

## 🚀 How to Use

### Step 1: Update Version in pom.xml

Change version from `SNAPSHOT` to release version:
```xml
<version>1.0.0</version>  <!-- Was 0.0.1-SNAPSHOT -->
```

### Step 2: Create a GitHub Release

1. Go to your GitHub repository
2. Click "Releases" → "Create a new release"
3. Tag version: `v1.0.0` (matches pom.xml version)
4. Add release notes
5. Click "Publish release"

**Workflow will automatically:**
- ✅ Build the package
- ✅ Run all tests
- ✅ Publish to GitHub Packages
- ✅ Create artifacts

### Step 3: Verify Published Package

1. Go to GitHub repository → "Packages"
2. Find your package: `ai-core`
3. View published version

---

## 📥 Using Published Package in Another Project

### Option 1: Using GitHub Token

Add to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.arvind</groupId>
    <artifactId>ai-core</artifactId>
    <version>1.0.0</version>
</dependency>

<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/arvind-sisodiya/ai-core</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```

Add to `~/.m2/settings.xml`:

```xml
<server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_GITHUB_TOKEN</password>
</server>
```

### Option 2: Generate Token

1. Go to GitHub → Settings → Developer settings → Personal access tokens
2. Click "Generate new token (classic)"
3. Select scopes: `read:packages`
4. Copy token and use as `password` in settings.xml

---

## 🔐 Security Best Practices

### Token Permissions

Your GitHub token should have:
- ✅ `read:packages` - Read packages
- ✅ `write:packages` - Publish packages (if needed)
- ✅ `repo` - Access to repository

### Protecting Secrets

- ✅ GitHub Actions automatically uses `GITHUB_TOKEN` secret
- ✅ Never commit tokens to repository
- ✅ Use GitHub Secrets for sensitive data
- ✅ Rotate tokens periodically

---

## 🎬 Workflow Triggers

The workflow is triggered by:

| Event | Action |
|-------|--------|
| **Release Created** | Build + Publish to GitHub Packages |
| **Push to main** | Build + Test (Publish on release tags) |
| **Push to develop** | Build + Test (Snapshots) |
| **Pull Request** | Build + Test only |
| **Tags (v*)** | Build + Publish to GitHub Packages |

---

## 📊 Build Matrix

The workflow supports:
- **Java**: 17 (configured in setup-java)
- **OS**: Ubuntu latest
- **Maven**: Latest from cache

---

## ✅ Verification Checklist

After setup, verify:

- [ ] `.github/workflows/maven-publish.yml` exists
- [ ] `pom.xml` has `<distributionManagement>` section
- [ ] `settings.xml` exists with GitHub server config
- [ ] Repository is pushed to GitHub
- [ ] GitHub Actions is enabled in repository settings
- [ ] Create a test release tag `v0.0.1-test`
- [ ] Workflow runs successfully
- [ ] Package appears in GitHub Packages

---

## 🐛 Troubleshooting

### Issue: "Cannot find symbol" errors

**Solution**: Ensure Java 17 is set in `setup-java@v4`

```yaml
- name: Set up JDK 17
  uses: actions/setup-java@v4
  with:
    java-version: '17'
```

### Issue: Authentication failed during publish

**Solution**: Verify `GITHUB_TOKEN` is available

```yaml
env:
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

GitHub Actions automatically provides `secrets.GITHUB_TOKEN`

### Issue: Package not published

**Solution**: Check workflow trigger condition

```yaml
if: github.event_name == 'release' || (github.ref == 'refs/heads/main' && github.event_name == 'push')
```

Only publishes on:
- Release creation
- Push to main branch

---

## 📚 Useful Commands

### Build locally (test before pushing)
```bash
mvn clean package
```

### Deploy to GitHub Packages locally (requires token)
```bash
mvn deploy -DskipTests
```

### Check workflow status
```bash
# View workflow runs in GitHub UI
# Or use GitHub CLI:
gh run list --workflow=maven-publish.yml
```

---

## 🔄 Next Steps

1. **Push to GitHub**:
   ```bash
   git add .github/workflows/maven-publish.yml settings.xml pom.xml
   git commit -m "Add GitHub Actions Maven package publishing"
   git push origin main
   ```

2. **Create a release**:
   - Update version in `pom.xml` to `1.0.0`
   - Create a GitHub release with tag `v1.0.0`
   - Watch workflow run automatically

3. **Monitor the workflow**:
   - Go to Actions tab in GitHub
   - View build logs
   - Check published packages

4. **Use the package**:
   - Add dependency to other projects
   - GitHub Actions will handle credentials

---

## 📝 Version Strategy

### Semantic Versioning

Use format: `MAJOR.MINOR.PATCH`

```xml
<!-- Development/Snapshot -->
<version>1.0.0-SNAPSHOT</version>

<!-- Release -->
<version>1.0.0</version>

<!-- Tag on GitHub -->
<tag>v1.0.0</tag>
```

### Automated Versioning (Optional)

For future: Consider using Maven Release Plugin or Semantic Release

---

## 🎯 Summary

You now have:

✅ **Automated CI/CD** - Build on every push  
✅ **Package Publishing** - To GitHub Packages on release  
✅ **Code Quality** - Checkstyle and test reporting  
✅ **Security Scanning** - OWASP dependency checks  
✅ **Artifact Storage** - GitHub Packages acts as repository  

Your Maven package is now ready for publication! 🚀

---

**Setup Date**: March 10, 2026  
**Status**: ✅ COMPLETE  
**Ready to Use**: YES
