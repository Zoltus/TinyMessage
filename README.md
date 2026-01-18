# TinyMsg
**A lightweight, powerful rich text parser for Hytale servers.**

TinyMsg allows you to use simple tags to create gradients, hex colors, clickable links, and nested styles in Hytale chat messages, similar to Minecraft's MiniMessage.

<img width="278" height="174" alt="HytaleClient_2026-01-15_08-44-16" src="https://github.com/user-attachments/assets/04816490-0a9e-4554-977c-b08fd1baee4c" />

---

## Features
* **Gradients:** `<gradient:red:blue>Hello</gradient>` or multi-color `<gradient:gold:red:black>...`
* **Hex Colors:** `<color:#FF55FF>Custom Colors</color>` or `<color:red>Named Colors</color>`
* **Standard Styles:** `<b>Bold</b>`, `<i>Italic</i>`, `<u>Underline</u>`, `<mono>Monospace</mono>`
* **Clickable Links:** `<link:https://google.com>Click me!</link>`
* **Nested Styling:** Tags can be nested indefinitely.

---

## Installation for server owners:

### Download the TinyMessage.jar
Download the latest TinyMessage.jar from the releases page and place it in your server's `mods` folder.

## Installation for developers:

Temporary until hytale plugin repository is available.
Create libs folder in your plugin directory and place the TinyMsg jar file there.
project/libs/TinyMessage-2.0.0-SNAPSHOT.jar

### manifest.json
```json
"Dependencies": {
  "Zoltus:TinyMessage": "*"
},
```

### For Gradle
```kotlin
dependencies {
    compileOnly(files("libs/tinymessage-2.0.0-SNAPSHOT.jar"))
}
```

### For Maven
```xml
<dependency>
    <groupId>fi.sulku.hytale</groupId>
    <artifactId>tinymessage</artifactId>
    <version>2.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/tinymessage-2.0.0-SNAPSHOT.jar</systemPath>
</dependency>
```

---

## Usage

### Basic Example
```java
import fi.sulku.hytale.TinyMsg;
import com.hypixel.hytale.server.core.Message;

// Parse a formatted string into a Message
Message message = TinyMsg.parse("<gradient:red:blue>Hello World!</gradient>");

player.sendMessage(message);

// Multiple styles
TinyMsg.parse("<b><color:gold>Bold Gold Text</color></b>");

TinyMsg.parse("<link:https://example.com><gradient:aqua:blue>Click me!</gradient></link>");

// Complex nested styling
TinyMsg.parse("<b>Bold <i>and italic <color:red>and red</color></i></b>");

// Reset styles mid-text
TinyMsg.parse("<b>Bold <reset>normal text");
```

---

## API Reference

### `TinyMsg.parse(String text)`
Parses a string with TinyMsg tags and returns a `Message` object.

**Parameters:**
- `text` - The string to parse

**Returns:**
- `Message` - A Hytale `Message` object ready to be sent to players

### Supported Tags

| Tag | Aliases               | Example | Description |
|-----|-----------------------|---------|-------------|
| `<color:X>` | `<c:X>`, `<colour:X>` | `<color:red>text</color>` | Sets text color (named or hex) |
| `<gradient:X:Y:Z>` | `<grnt:X:Y:Z>`        | `<gradient:red:blue>text</gradient>` | Creates a color gradient |
| `<bold>` | `<b>`                 | `<b>text</b>` | Makes text bold |
| `<italic>` | `<i>`, `<em>`         | `<i>text</i>` | Makes text italic |
| `<underline>` | `<u>`                 | `<u>text</u>` | Underlines text |
| `<monospace>` | `<mono>`              | `<mono>text</mono>` | Uses monospace font |
| `<link:URL>` | `<url:link>`          | `<link:https://google.com>click</link>` | Creates clickable link |
| `<reset>` | `<r>`                 | `<b>bold<reset>normal` | Resets all formatting |

### Named Colors
`black`, `dark_blue`, `dark_green`, `dark_aqua`, `dark_red`, `dark_purple`, `gold`, `gray`, `dark_gray`, `blue`, `green`, `aqua`, `red`, `light_purple`, `yellow`, `white`

---

## License
MIT License - feel free to use in your projects!
