# Contributing to Mokt

Thank you for your interest in contributing to Mokt! This document provides guidelines and instructions for contributing to this project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
  - [Development Environment Setup](#development-environment-setup)
  - [Project Structure](#project-structure)
- [Development Workflow](#development-workflow)
  - [Branching Strategy](#branching-strategy)
  - [Commit Messages](#commit-messages)
  - [Pull Requests](#pull-requests)
- [Coding Standards](#coding-standards)
  - [Kotlin Style Guide](#kotlin-style-guide)
  - [Documentation](#documentation)
  - [Testing](#testing)
- [Issue Reporting](#issue-reporting)
- [License](#license)

## Code of Conduct

By participating in this project, you are expected to uphold our Code of Conduct:

- Be respectful and inclusive
- Be patient and welcoming
- Be thoughtful
- Be collaborative
- When disagreeing, try to understand why

## Getting Started

### Development Environment Setup

1. **Prerequisites**:
   - JDK 8 or higher
   - Gradle (the project uses the Gradle wrapper, so you don't need to install it separately)
   - Git
   - Rust and Cargo (for the native component)

2. **Clone the repository**:
   ```bash
   git clone https://github.com/Dev-Redtronics/Mokt.git
   cd Mokt
   ```

3. **Set up Rust (if not already installed)**:
   ```bash
   # Install Rust using rustup
   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
   # Follow the on-screen instructions

   # After installation, build the native component
   cd mokt-native
   cargo build --release
   cd ..
   ```

4. **Build the project**:
   ```bash
   ./gradlew build
   ```

5. **Run tests**:
   ```bash
   ./gradlew test
   ```

### Project Structure

Mokt is a Kotlin Multiplatform project with the following main modules:

- **common**: Common code shared across all platforms
- **core**: Core functionality of the SDK
- **authentication**: Authentication-related functionality
- **launcher**: Launcher-related functionality
- **buildSrc**: Custom Gradle plugins and build logic
- **mokt-native**: Rust component providing native functionality via C interop
- **cinterop**: C interop definitions for Kotlin/Native to interact with the Rust code

## Development Workflow

### Branching Strategy

- `main`: The main branch containing the latest stable release
- `develop`: The development branch containing the latest development changes
- Feature branches: Create a new branch for each feature or bug fix

When working on a new feature or bug fix:

1. Create a new branch from `develop`:
   ```bash
   git checkout develop
   git pull
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them
3. Push your branch to the remote repository
4. Create a pull request to merge your changes into `develop`

### Commit Messages

Follow these guidelines for commit messages:

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line

Example:
```
Add authentication support for Microsoft accounts

- Implement OAuth2 flow for Microsoft accounts
- Add tests for authentication process
- Update documentation

Fixes #123
```

### Pull Requests

When creating a pull request:

1. Fill out the pull request template
2. Reference any related issues
3. Ensure all tests pass
4. Update documentation if necessary
5. Request a review from at least one maintainer

## Coding Standards

### Kotlin Style Guide

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use explicit API mode (all public APIs must have explicit visibility modifiers)
- Use meaningful names for classes, methods, and variables
- Keep methods small and focused on a single responsibility
- Add KDoc comments for all public APIs

### Rust Style Guide

- Follow the [Rust API Guidelines](https://rust-lang.github.io/api-guidelines/)
- Use `rustfmt` to format your code (`cargo fmt`)
- Use `clippy` to catch common mistakes (`cargo clippy`)
- Write documentation comments for all public functions and types
- Ensure all public functions exposed via C interop are marked with `#[no_mangle]` and `extern "C"`
- Handle errors properly and avoid panicking in functions exposed via C interop

### Documentation

- Add KDoc comments for all public classes, methods, and properties
- Update the README.md if you add or change functionality
- Update the CHANGELOG.md for all notable changes

### Testing

- Write tests for all new functionality
- Ensure all tests pass before submitting a pull request
- Use Kotest for writing tests
- Aim for high test coverage, especially for core functionality

## Issue Reporting

When reporting issues:

1. Use the issue template
2. Provide a clear and descriptive title
3. Describe the steps to reproduce the issue
4. Include expected and actual behavior
5. Include any relevant logs or screenshots
6. Specify the version of Mokt you're using

## License

By contributing to Mokt, you agree that your contributions will be licensed under the project's [MIT License](LICENSE).

---

Thank you for contributing to Mokt! Your efforts help make this project better for everyone.
