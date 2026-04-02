# 软件设计模式与重构 — 课程作业

## 项目简介

本仓库用于《软件设计模式与重构》课程的作业实践，使用 Java + Gradle 实现各设计模式。

## 技术栈

| 工具 | 版本 |
|------|------|
| Java | 21+ |
| Gradle | 9.x (Kotlin DSL) |
| 测试框架 | JUnit Jupiter |

## 分支策略

```
main  ──────────────────────────────────────────► (最新稳定版)
        │ PR merge              │ PR merge
        ▼                       ▼
   assignment/1            assignment/2
   (第1次作业)              (第2次作业)
```

- `main`：始终保持最新、可运行的代码，每次作业合并后打 Tag
- `assignment/<N>`：每次作业独立分支，基于上一次 `main` 创建
- Tag 格式：`assignment_<N>`（例：`assignment_1`）

## 作业目录

| 分支 | Tag | 内容 |
|------|-----|------|
| `assignment/1` | `assignment_1` | *(待完成)* |

## 快速开始

```bash
# 构建项目
./gradlew build

# 运行应用
./gradlew :app:run

# 运行测试
./gradlew test
```

## 新建作业分支

```bash
# 基于 main 创建下一次作业分支
git switch main
git switch -c assignment/<N>

# 完成作业后，提交并推送
git add .
git commit -m "feat(assignment<N>): ..."
git push -u origin assignment/<N>

# 在 GitHub 上创建 PR → main，合并后打 Tag
git switch main && git pull
git tag assignment_<N>
git push origin assignment_<N>
```
