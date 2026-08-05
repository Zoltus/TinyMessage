 proyecto abandonado, ya no está mantenido.
No había planes para agregar etiquetas personalizadas, pero como este proyecto no ofrece nada que lo que ya soporte el chat nativo de Hytale, no deberías usarlo.
Hytale [FormattedMessage](https://docs.hytale.com/com/hypixel/hytale/protocol/FormattedMessage#markupEnabled) permite markdown.

# TinyMsg
**Un analizador de texto enriquecido ligero y potente para servidores de Hytale.**

TinyMsg te permite usar etiquetas simples para crear degradados, colores hexadecimales, enlaces clicables y estilos anidados en mensajes de chat de Hytale, similar al MiniMessage de Minecraft.

<img width="278" height="174" alt="HytaleClient_2026-01-15_08-44-16" src="https://github.com/user-attachments/assets/04816490-0a9e-4554-977c-b08fd1baee4c" />

---

## Características
* **Degradados:** `<gradient:red:blue>Hola</gradient>` o multi-color `<gradient:gold:red:black>...`
* **Colores hexadecimales:** `<color:#FF55FF>Colores personalizados</color>` o `<color:red>Colores nombrados</color>`
* **Estilos estándar:** `<b>Negrita</b>`, `<i>Cursiva</i>`, `<u>Subrayado</u>`, `<mono>Monsanto</mono>`
* **Enlaces clicables:** `<link:https://google.com>Haz clic aquí</link>`
* **Estilos anidados:** Las etiquetas pueden anidarse indefinidamente.

---

## Instalación para propietarios de servidores:

### Descargar TinyMessage.jar
Descarga el último TinyMessage.jar desde la página de versiones y colócalo en la carpeta `mods` de tu servidor.

## Instalación para desarrolladores:

Temporal hasta que esté disponible el repositorio de plugins de Hytale.
Crea una carpeta libs en tu directorio de plugin y coloca el archivo TinyMsg jar allí.
project/libs/TinyMessage-2.0.1-SNAPSHOT.jar

### manifest.json
```json
"Dependencies": {
  "Zoltus:TinyMessage": "*"
},
```

### Para Gradle
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("com.github.Zoltus:TinyMessage:2.0.1")
}
```

### Para Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Zoltus</groupId>
    <artifactId>TinyMessage</artifactId>
    <version>2.0.1</version>
    <scope>provided</scope>
</dependency>
```

---

## Uso

### Ejemplo básico
```java
import fi.sulku.hytale.TinyMsg;
import com.hypixel.hytale.server.core.Message;

// Analizar una cadena con etiquetas en un objeto Message
Message message = TinyMsg.parse("<gradient:red:blue>Hola Mundo!</gradient>");

player.sendMessage(message);

// Múltiples estilos
TinyMsg.parse("<b><color:gold>Texto negrita dorado</color></b>");

TinyMsg.parse("<link:https://example.com><gradient:aqua:blue>Haz clic aquí</gradient></link>");

// Estilizado anidado complejo
TinyMsg.parse("<b>Negrita <i>y cursiva <color:red>y rojo</color></i></b>");

// Reiniciar estilos en medio del texto
TinyMsg.parse("<b>Negrita <reset>texto normal");
```

---

## Referencia de API

### `TinyMsg.parse(String text)`
Analiza una cadena con etiquetas TinyMsg y devuelve un objeto `Message`.

**Parámetros:**
- `text` - La cadena a analizar

**Devuelve:**
- `Message` - Un objeto `Message` de Hytale listo para enviarse a jugadores

### Etiquetas admitidas

| Etiqueta | Alias               | Ejemplo | Descripción |
|----------|---------------------|---------|-------------|
| `<color:X>` | `<c:X>`, `<colour:X>` | `<color:red>texto</color>` | Establece el color del texto (nombrado o hexadecimal) |
| `<gradient:X:Y:Z>` | `<grnt:X:Y:Z>`        | `<gradient:red:blue>texto</gradient>` | Crea un degradado de color |
| `<bold>` | `<b>`                 | `<b>texto</b>` | Hace que el texto sea negrita |
| `<italic>` | `<i>`, `<em>`         | `<i>texto</i>` | Hace que el texto sea cursiva |
| `<underline>` | `<u>`                 | `<u>texto</u>` | Subraya el texto |
| `<monospace>` | `<mono>`              | `<mono>texto</mono>` | Usa fuente monoespaciada |
| `<link:URL>` | `<url:link>`          | `<link:https://google.com>haz clic</link>` | Crea un enlace clickeable |
| `<reset>` | `<r>`                 | `<b>negrita<reset>normal` | Restablece todo el formato |

### Colores nombrados
`black`, `dark_blue`, `dark_green`, `dark_aqua`, `dark_red`, `dark_purple`, `gold`, `gray`, `dark_gray`, `blue`, `green`, `aqua`, `red`, `light_purple`, `yellow`, `white`

---

## Licencia
Licencia MIT - ¡Siéntete libre de usarlo en tus proyectos!
