# TinyMsg 💬
**A lightweight, powerful rich text parser for Hytale servers.**

TinyMsg allows you to use simple tags to create gradients, hex colors, clickable links, and nested styles in Hytale chat similar to Minecraft's MiniMessage. It works as both a **standalone chat plugin** and a **developer library** (API).

<img width="278" height="174" alt="HytaleClient_2026-01-15_08-44-16" src="https://github.com/user-attachments/assets/04816490-0a9e-4554-977c-b08fd1baee4c" />
---

## Features
* **Gradients:** `<gradient:red:blue>Hello</gradient>` or multi-color `<gradient:gold:red:black>...`
* **Hex Colors:** `<color:#FF55FF>Custom Colors</color> or <color:red>Named Colors</color>`
* **Standard Styles:** `<b>Bold</b>`, `<i>Italic</i>`, `<u>Underline</u>`, `<mono>Monospace</mono>`
* **Clickable Links:** `<link:https://google.com>Click me!</link>`
* **Nested Styling:** Tags can be nested indefinitely.

---

## Installation (For Server Owners)
1.  Download the latest `.jar`
2.  Place the file into your Hytale server's `mods/` folder.
3.  Restart the server.

### Configuration (`config.json`)
The configuration file allows you to control who can use color codes in chat.

```json
{
  "IsChatColorEnabled": false,
  "NeedsPermission": true,
  "ChatColorPerm": "tinymsg.chat.color"
}
