# 🌐 Smart Network Chat Server (Java Sockets)
> A robust, multi-threaded social communication server built with Java Sockets and an integrated Smart Reply Engine.

<p align="center">
  <img src="https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Architecture-Client--Server-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Networking-TCP--IP-green?style=for-the-badge" />
</p>

---

## 📖 Overview
This project is a high-performance Java-based server designed to handle simultaneous client connections. By utilizing **Socket Programming** and **Multi-threading**, the server ensures that each user interaction is processed independently without blocking others. It features a custom **SmartReplyEngin** to provide automated, context-aware responses.



## 🛠 Technical Highlights
* **Multi-threaded Architecture:** Uses `SocketHandler` (implements `Runnable`) to spawn a new thread for every client, enabling concurrent communication.
* **Safe Resource Management:** Implements `Try-with-resources` to ensure all `Input/Output Streams` and `Sockets` are closed properly, preventing memory leaks.
* **Auto-Flush Communication:** Configured `PrintWriter` with `auto-flush` enabled to ensure instant delivery of messages between server and client.
* **Smart Logic Integration:** Instead of a simple Echo server, it delegates message processing to a dedicated `SmartReplyEngin`.
* **Active Connection Tracking:** Includes a real-time counter to monitor the number of active clients currently connected to the server.

---

## ⚙️ Specifications
* **Default Port:** `7777`
* **Protocol:** TCP
* **Package Structure:** `com.seniorsteps.network`
* **Concurrency:** Thread-per-connection model.

---

## 🚀 Getting Started (How to Run)

### 1. Prerequisites
* **JDK 21** or higher installed on your machine.

### 2. Compilation
Navigate to the root directory of the project and compile the classes:
```bash
javac com/seniorsteps/network/Main.java
