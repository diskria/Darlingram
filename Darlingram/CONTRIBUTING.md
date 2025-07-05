# 🌸 Contributing to Darlingram

Thank you for your interest in contributing!  
**Darlingram** is a lovingly maintained open-source fork of Telegram — and we care about structure, clarity, and joy ✨

To make the experience smooth for everyone, we follow a few conventions.  
Please read below before submitting a pull request.

---

## 🎯 Project Goals

The goal of **Darlingram** is to **fully replicate the UI and behavior** of the official Telegram Android client —  
but on top of a **modern Android architecture**, using clean patterns and powerful tools.

We welcome pull requests that help move in this direction, including:

- 🧱 Implementing missing Telegram UI and features in the new modular stack
- 🧹 Cleaning up, organizing, or isolating legacy Telegram code
- 🐞 Fixing bugs or regressions in upstream source
- 🚀 Improving build tooling, automation, and scripting
- 🧰 Enhancing the developer experience with **Forky-compatible** workflows

> Whether you're improving the new client or pruning the old one —  
> all contributions that support clean, testable, and fork-friendly development are appreciated 💖

---

## 🧸 Forky

We use a convention called [**Forky**](Forky/README.md) — a _fork-friendly_ structure for modifying upstream code.

Instead of scattering changes throughout upstream files, **Forky** asks that all changes be **clearly marked and easy to find**.  
This ensures merges remain clean, safe, and understandable — even months later.

If you edit files from the upstream Telegram source, make sure to:

- Wrap changes with Forky comment markers
- Use our provided Live Templates for easy insertion
- Run the `forky` checker script to validate everything

> Think of Forky as your little merge buddy —  
> he watches your diffs and protects your peace

---

## 📝 Commit Message Format

We enforce a lightweight commit convention using Git hooks.  
Each commit message must follow this format:

```
<type>: <emoji> <description>
```

### ✅ Allowed types:

- `feat`: A new feature
- `dx`: Developer experience improvements
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Code style changes (formatting, etc)
- `refactor`: Code changes that neither fix a bug nor add a feature
- `test`: Adding or updating tests
- `build`: Build system or dependency changes
- `chore`: Routine tasks and cleanup
- `perf`: Performance improvements
- `ci`: Continuous integration-related
- `revert`: Reverts a previous commit
- `merge`: Merge commits

### 📌 Example:

```
feat: ✨ added intro screen
fix: 🐛 crash on startup
refactor: 🤡 commented legacy code
```

Yes, emoji are required 😎  
This slows you down just enough to think about your changes — and makes the log fun to read 💖

---

## 🔧 Git Hooks

To enable commit message checks and other local hooks, run:

```
sh hooks/setup.sh
```

---

## Local Maven

Run the `Setup Local Maven` run configuration before building for the first time,
or after making changes in the `kotlin-tools` module.

> This ensures internal plugins resolve correctly during build.
 
---

Happy coding! 🌸
