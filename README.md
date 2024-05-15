# 🧈 Chat Application

This is a simple chat application written in Java, allowing multiple users to connect and communicate with each other.

## ⛓️ Installation

1. Clone the repository:

   ```
   git clone https://github.com/booringreader/butternet.git
   ```

2. Navigate to the `src` directory:

   ```
   cd butternet/src
   ```

3. Compile the `Client.java` and `GUI.java` files in two different Terminal windows:

   ```
   javac Client.java GUI.java
   ```

4. Open a new terminal window & compile the `Server.java` file:

   ```
   javac Server.java
   ```

## Usage

1. Launch the server by running the following command:

   ```
   java Server
   ```

2. Open two new terminal windows & launch the GUI session of the application by running:

   ```
   java -cp . src.GUI
   ```

3. You can now use the GUI to send and receive messages between the two clients.

## Configuration

By default, the server is configured to use port 8000. If this port is already in use on your machine, you can change it in the `Server.java` & `GUI.java` files before compiling.
Check for open ports on macOS using:
```
sudo lsof -i -P -n | grep LISTEN
```

