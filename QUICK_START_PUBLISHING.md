# 🚀 Quick Start: Publish Your Maven Package

## 5-Minute Setup

### Step 1: Update pom.xml Version
```xml
<!-- Change from -->
<version>0.0.1-SNAPSHOT</version>

<!-- To -->
<version>1.0.0</version>
```

### Step 2: Commit Changes
```bash
cd C:\Users\arvind_sisodiya\Documents\1scor-incubation\ai-core

git add pom.xml
git add .github/workflows/maven-publish.yml
git add settings.xml
git commit -m "Add GitHub Actions publishing setup"
git push origin main
```

### Step 3: Create GitHub Release
1. Go to GitHub repository
2. Click "Releases" on the right sidebar
3. Click "Create a new release"
4. **Tag version**: `v1.0.0`
5. **Release title**: `Version 1.0.0`
6. **Description**: Your release notes
7. Click "Publish release"

### Step 4: Watch the Magic ✨
- Go to "Actions" tab in GitHub
- Watch `Maven Package Build & Publish` workflow run
- It will:
  - ✅ Build the project
  - ✅ Run all tests
  - ✅ Publish to GitHub Packages
  - ✅ Generate artifacts

### Step 5: Verify Package Published
1. Go to repository → "Packages"
2. Find `ai-core` package
3. See published version `1.0.0`

---

## 📦 Using Your Published Package

### In Your `pom.xml`:
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
    </repository>
</repositories>
```

### In Your `~/.m2/settings.xml`:
```xml
<server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_GITHUB_TOKEN</password>
</server>
```

### Get GitHub Token:
1. GitHub → Settings → Developer settings → Personal access tokens
2. Click "Tokens (classic)"
3. Click "Generate new token"
4. Name: `maven-package-token`
5. Scopes: `read:packages`, `write:packages`
6. Copy and use as password in settings.xml

---

## 🎯 Workflow at a Glance

| When | What Happens |
|------|--------------|
| Create Release | Build + Test + Publish |
| Push to main | Build + Test |
| Push to develop | Build + Test (Snapshots) |
| Pull Request | Build + Test |

---

## ✅ Files Created/Updated

- ✅ `.github/workflows/maven-publish.yml` - CI/CD workflow
- ✅ `pom.xml` - Updated with distribution management
- ✅ `settings.xml` - Maven authentication config
- ✅ `GITHUB_ACTIONS_SETUP.md` - Detailed documentation

---

## 🎊 You're Done!

Your Maven package is now set up for:
- ✅ Automated building
- ✅ Automated testing
- ✅ Automated publishing
- ✅ GitHub Packages hosting

**Next**: Create a release on GitHub and watch it publish! 🚀

---

**Time to First Release**: ~5 minutes  
**Status**: ✅ READY TO PUBLISH
