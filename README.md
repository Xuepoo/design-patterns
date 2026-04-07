# 软件设计模式与重构 — 课程作业

## 项目简介

本仓库用于《软件设计模式与重构》课程的作业实践，使用 Java + Gradle 实现各设计模式。

## 技术栈

| 工具     | 版本             |
| -------- | ---------------- |
| Java     | 21+              |
| Gradle   | 9.x (Kotlin DSL) |
| 测试框架 | JUnit Jupiter    |

---

## 项目结构

```
design-patterns/
├── app/                          ← 应用子模块（实际写代码的地方）
│   ├── build.gradle.kts          ← 子模块的构建配置（依赖、插件）
│   └── src/
│       ├── main/
│       │   ├── java/             ← ✏️ 业务代码目录（在这里写设计模式）
│       │   └── resources/        ← 配置文件、资源文件（如 .properties）
│       └── test/
│           ├── java/             ← ✏️ 测试代码目录（JUnit 单元测试）
│           └── resources/        ← 测试专用资源文件
│
├── gradle/
│   ├── libs.versions.toml        ← 统一版本管理（所有依赖版本写在这里）
│   └── wrapper/
│       ├── gradle-wrapper.jar    ← Gradle Wrapper 启动器
│       └── gradle-wrapper.properties  ← 指定 Gradle 版本号
│
├── gradlew                       ← Linux/Mac 下的 Gradle 启动脚本
├── gradlew.bat                   ← Windows 下的 Gradle 启动脚本
├── settings.gradle.kts           ← 根项目配置，声明子模块（如 app）
├── gradle.properties             ← 全局 JVM 参数、Gradle 选项
│
├── .gitignore                    ← Git 忽略规则（构建产物不入库）
├── .gitattributes                ← 统一换行符（CRLF/LF）跨平台一致性
├── README.md                     ← 项目说明文档
├── docs/                         ← 课程文档、作业说明（手动放入）
└── figures/                      ← 图片资源（如 UML 类图）
```

> **日常开发只需关注这两个目录：**
>
> - `app/src/main/java/com/designpatterns/` — 写设计模式实现代码
> - `app/src/test/java/com/designpatterns/` — 写对应的单元测试

### 关键文件说明

**`settings.gradle.kts`** — Gradle 项目入口，声明项目名称和子模块：

```kotlin
rootProject.name = "design-patterns"
include("app")  // 声明 app 是一个子模块
```

**`app/build.gradle.kts`** — 子模块的依赖与插件配置，新增第三方库在此处添加 `dependencies`：

```kotlin
plugins { application }
dependencies {
    testImplementation(libs.junit.jupiter)
}
```

**`gradle/libs.versions.toml`** — 版本目录（Version Catalog），统一管理所有依赖的版本号，避免版本分散：

```toml
[versions]
junit-jupiter = "5.11.0"
```

**`gradle/wrapper/gradle-wrapper.properties`** — 锁定 Gradle 版本，保证多人协作时构建环境一致，无需本地预装 Gradle。

**`gradle.properties`** — JVM 参数与 Gradle 全局选项（如内存分配、并行构建）。

---

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

| 分支           | Tag            | 内容                                  |
| -------------- | -------------- | ------------------------------------- |
| `assignment/1` | `assignment_1` | 温度数据分析系统（策略模式）          |
| `assignment/2` | `assignment_2` | 多数据源采集系统（工厂模式+策略模式） |
| `assignment/3` | `assignment_3` | 观察者模式（数据变化触发自动处理）    |

---

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
