# 🌸 Darlingram

**Darlingram** is a fully open-source fork of the Telegram Android client,
focused on modern architecture, tooling, and developer productivity.

It aims to provide:

- 🧱 **Modern Android Stack**
  Darlingram plans to adopt a modern Android tech stack, including:

  - 🌐 **Kotlin** as the primary language for all future development
  - ⚡️ **Coroutines** for asynchronous and reactive programming
  - 🧠 **MVVM** architecture as the foundational pattern
  - 🧼 Inspired by **Clean Architecture** principles for layered separation and testability
  - 🧱 Modular project structure for clear separation of concerns and scalability
  - 🧬 **Jetpack Compose** for declarative UI design
  - 🧭 **Jetpack Navigation** for structured in-app routing
  - 💾 **Room** for structured local persistence
  - 🔌 **Retrofit** for type-safe networking

- ⚙️ **Automation & Scripting**
  The build system is built for extensibility — including Gradle scripting, custom build logic, and code generation.

- 🚀 **Developer Experience**
  Emphasis on clarity, maintainability, and tooling for contributors — including debug helpers, local overrides, and project-specific conventions.

---

## 🧠 Philosophy

Darlingram is not just a fork — it’s a reinterpretation.

Instead of building a new app on top of Telegram’s code, **Darlingram evolves from within it**.
Telegram is not just our base — it’s our shell. Darlingram grows from inside,
gradually modernizing and rearchitecting itself in place.

The project layout reflects that:

- Telegram remains at the repository root.
- Darlingram lives inside Telegram as a subfolder.
- We develop inside the original Telegram tree — like a chrysalis 🐛🦋

This may seem backwards compared to typical forks, but that’s the point.
**We want the tooling, the scripts, and even the directory structure to embody our philosophy.**

---

## 🧩 How does it work?

Darlingram uses the original Telegram sources as both:

- 🛠 A scaffold — we build on top of it.
- 🧪 A mirror — we compare against it.

To help maintain that duality, we include a **Git submodule** with a clean copy of upstream Telegram Android.

This allows us to:

- Run differential tools and validation
- Cross-reference behavior
- Merge safely, while keeping our structure intact

---

## 🧸 Forky

If you want to understand **how we keep our diffs clean**, you’ll want to meet **[Forky](Forky/README.md)** — our adorable internal tooling philosophy.

> Forky was born the moment we added the upstream Telegram as a Git submodule
> **to a repo that was already a fork of Telegram.**
>
> Yes — we forked Telegram, then added Telegram as a submodule.
> This metaphysical event created Forky — a tool and a symbol of our commitment
> to respecting upstream *while making it better*. 🌸

Forky helps us:

- Mark all edits to upstream code explicitly
- Run checker scripts before merging
- Keep our diffs safe, searchable, and mergeable

Even your tools should feel loved 💘

---

## 🧪 Multi-Platform Research Setup

Darlingram includes **Telegram Android**, **Telegram iOS** and **Telegram Desktop** as Git submodules under `telegram-clients/`.

These are not used directly in the app, but serve as:

- 🔍 Reference material for cross-platform behavior
- 🧠 Reverse-engineering or feature-porting sources
- 🧰 A foundation for building tools and debug scripts across clients

All three platforms are connected through a shared toolkit —
with helper scripts, version readers, and update automation included.

> **Note:** This README is a placeholder and will be expanded as the project evolves.

---

## 🗂️ Project Structure

📁 This project lives in the `Darlingram/` subfolder of the repository.
📌 Please **open the project from this folder**, _not_ the root of the repository.
✅ Opening from `Darlingram/` ensures proper configuration of module structure and build variants.

---

## 🛠️ Build Setup

🔑 To build the app, make sure the `Darlingram/local.properties` file contains the following:

```properties
API_ID=12345678
API_HASH="your_api_hash_here"
```

📝 You can obtain these values by following the instructions in the [original Telegram README](https://github.com/DrKLO/Telegram#creating-your-telegram-application).

## 🤝 Contributing

We welcome contributions of all kinds — code, documentation, ideas, and testing.

Please read our [Contributing Guide](CONTRIBUTING.md) before opening an issue or submitting a pull request.
