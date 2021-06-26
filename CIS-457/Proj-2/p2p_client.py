#!/usr/bin/env python3
# with emoji library (with pip: pip install emoji --upgrade)

import tkinter as tk
import tkinter.font as font
import socket as sock
from threading import Thread
import threading
import emoji
import re

HOST = '127.0.0.1'
PORT = 4321
BufferSize = 1024

# Code from https://stackoverflow.com/questions/40222971/python-find-equivalent-surrogate-pair-from-non-bmp-unicode-char
# to make utf-16 most emojis work on tKinter
_nonbmp = re.compile(r'[\U00010000-\U0010FFFF]')

def _surrogatepair(match):
    char = match.group()
    assert ord(char) > 0xffff
    encoded = char.encode('utf-16-le')
    return (
            chr(int.from_bytes(encoded[:2], 'little')) + 
            chr(int.from_bytes(encoded[2:], 'little')))

def with_surrogates(text):
    return _nonbmp.sub(_surrogatepair, text)

class P2P:
    def __init__(self):
        self.window = tk.Tk()
        self.font = font.Font(family="Arial", size = 11)
        self.setup_gui(self.window)
        self.client = ()
        self.mode = ""
        self.server = sock.socket(sock.AF_INET, sock.SOCK_STREAM)
        self.connected = False
        self.server_lock = threading.Lock()

    def setup_gui(self, window):
        self.titleLabel = tk.Label(window, text = "P2P Chat App")

        # buttons
        self.hostBtn = tk.Button(window,
                text = "Host",
                command = self.host)
        self.connectBtn = tk.Button(window, 
                text = "Connect",
                command = self.connect)
        self.disconnectBtn = tk.Button(window,
                text = "Disconnect",
                command = self.disconnect,
                state = tk.DISABLED)
        self.sendBtn = tk.Button(window,
                text = "Send",
                command = self.send)

        # IP
        self.ipEntryText = tk.StringVar()
        self.ipEntryLabel = tk.Label(window, text = "IP")
        self.ipEntry = tk.Entry(window, textvariable = self.ipEntryText)

        # chat
        self.messageEntryText = tk.StringVar()
        self.messageEntry = tk.Entry(window, width = 32, textvariable = self.messageEntryText)
        self.chatTextbox = tk.Text(window, height = 10, width = 50) 
        self.chatTextbox['font'] = self.font

        self.titleLabel.grid(row = 0, column = 0, columnspan = 5)
        self.ipEntryLabel.grid(row = 1, column = 0)
        self.ipEntry.grid(row = 1, column = 1)
        self.hostBtn.grid(row = 1, column = 2)
        self.disconnectBtn.grid(row = 1, column = 3)
        self.connectBtn.grid(row = 1, column = 4)

        self.messageEntry.grid(row = 3, column = 0, columnspan = 4)
        self.sendBtn.grid(row = 3, column = 4)
        self.chatTextbox.grid(row = 2, column = 0, columnspan = 5)

    def accept_connection(self):
        while self.client == ():
            self.client = self.server.accept()
        self.chatTextbox.insert(tk.END, "Someone has connected!\n")
        self.client[0].send('Welcome to the chat!'.encode())
        self.connected = True
        Thread(target=self.handle_connection, args=(self.client[0],)).start()

    def handle_connection(self, client):
        while self.connected:
            message = client.recv(BufferSize).decode()
            if (message == "<quit>"):
                self.server.close()
                self.chatTextbox.insert(tk.END, "They have disconnected\n")
                self.connected = False
                self.connectBtn.config(state = tk.ACTIVE)
                self.hostBtn.config(state = tk.ACTIVE)
                self.disconnectBtn.config(state = tk.DISABLED)
            else:
                self.server_lock.acquire(blocking=True)
                self.chatTextbox.insert(tk.END, "Them: " + with_surrogates(emoji.emojize(message, use_aliases=True)) + '\n')
                self.server_lock.release()

    def run(self):
        self.window.mainloop()

    def host(self):
        self.mode = "HOST"
        self.ipEntryText.set("Hosting")
        self.connectBtn.config(state = tk.DISABLED)
        self.hostBtn.config(state = tk.DISABLED)
        self.disconnectBtn.config(state = tk.ACTIVE)
        self.server.bind((HOST, PORT))
        self.server.listen(1)
        listen_thread = Thread(target = self.accept_connection)
        listen_thread.start()
        #listen_thread.join()
        #self.server.close()

    def connect(self):
        self.mode = "CLIENT"
        self.connectBtn.config(state = tk.DISABLED)
        self.hostBtn.config(state = tk.DISABLED)
        self.disconnectBtn.config(state = tk.ACTIVE)
        if (bool(re.match("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", self.ipEntryText.get()))):
            try:
                self.server.connect((self.ipEntryText.get(), PORT))
                self.connected = True
                Thread(target=self.handle_connection, args=(self.server,)).start()
            except:
                self.chatTextbox.insert(tk.END, "Unable to connect to host")
                self.connected = False

    def disconnect(self):
        self.server.send("<quit>".encode())
        self.server.close()
        self.chatTextbox.insert(tk.END, "You have disconnected\n")
        self.connected = False
        self.connectBtn.config(state = tk.ACTIVE)
        self.hostBtn.config(state = tk.ACTIVE)
        self.disconnectBtn.config(state = tk.DISABLED)

    def send(self):
        if (self.connected):
            message = self.messageEntryText.get()
            if (self.mode == "HOST"):
                self.client[0].send(message.encode())
            else:
                self.server.send(message.encode())
            self.server_lock.acquire(blocking = True)
            self.chatTextbox.insert(tk.END, "You: " + with_surrogates(emoji.emojize(message, use_aliases=True)) + '\n')
            self.messageEntryText.set("")
            self.server_lock.release()

app = P2P()
app.run()

