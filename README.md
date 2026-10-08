# Mi Primera Aplicación

Un proyecto inicial de Android creado para aprender los conceptos fundamentales del desarrollo móvil nativo en **TI3V42 - Aplicaciones Móviles para IoT** (INACAP Puente Alto).

---

## 📱 Contenido y Funcionalidades Desarrolladas

### 🔹 Bloque 1: Construcción Incremental del Login (Incrementos 1 al 10)
1. **`ImageView` (`imgLogo`):** Logo corporativo INACAP (`@drawable/logo_inacap`), 96x96dp, centrado superiormente.
2. **`TextView` (`txtTitulo`):** Título `"Iniciar sesión"`, `24sp`, negrita y posicionado bajo el logo.
3. **`EditText` (`edtUsuario`):** Campo de entrada de texto con `hint="Usuario"` y `0dp` (match constraints).
4. **`EditText` (`edtPassword`):** Campo de contraseña con `hint="Contraseña"` y `android:inputType="textPassword"`.
5. **`CheckBox` (`chkRecordarme`):** Casilla de verificación para recordar credenciales.
6. **`Button` (`btnIngresar`):** Botón principal de acceso con ancho expandido.
7. **Lógica de Botón en Kotlin:** Conexión vía `android:onClick="onIngresarClick"`, captura de vistas con `findViewById` y validaciones básicas.
8. **Segunda Pantalla (`BienvenidaActivity`):** Creación de Activity y layout `activity_bienvenida.xml` con `txtBienvenida`.
9. **Navegación con `Intent`:** Transición de pantalla explícita con `startActivity(intent)`.
10. **Paso de Parámetros:** Envío de datos entre Activities con `intent.putExtra("usuario", usuario)` y recepción con `intent.getStringExtra("usuario")`.

---

### 🔹 Bloque 2: Mejoras de Interfaz, Experiencia de Usuario y Validaciones
11. **Botón de Limpieza (`btnLimpiar`):**
    - Botón secundario estilizado con fondo rojo (`android:backgroundTint="#FF0000"`).
    - Método `onLimpiarClick` para resetear campos de usuario, contraseña, errores visuales y desmarcar el checkbox.
12. **Alternar Visibilidad de Contraseña (`btnMostrarPassword`):**
    - `ImageButton` con icono `@drawable/ic_visibility_24` y fondo transparente.
    - Variable de estado `mostrandoPassword: Boolean` y método `onMostrarPasswordClick` que conmuta dinámicamente el `inputType` entre texto visible y oculto preservando la posición del cursor.
13. **Contador de Intentos Fallidos (`intentosFallidos`):**
    - Variable a nivel de clase que registra cada intento fallido de autenticación.
14. **Validaciones Robustas con `.error`:**
    - Validación de formato de correo electrónico mediante `Patterns.EMAIL_ADDRESS`.
    - Validación de longitud mínima para la contraseña (al menos 6 caracteres).
    - Mensajes contextuales directos en los campos mediante `edtUsuario.error` y `edtPassword.error`.

---

### 🔹 Bloque 3: Tercera Pantalla y Configuración (`PreferenciasActivity`)
15. **Navegación a Preferencias:**
    - Botón `btnPreferencias` en `activity_bienvenida.xml` vinculado al método `onPreferenciasClick`.
    - Propagación del nombre del usuario como extra en el `Intent`.
16. **Layout de Preferencias (`activity_preferencias.xml`):**
    - `TextView` de título centrado.
    - `LinearLayout` vertical para agrupar las opciones de configuración.
    - `MaterialSwitch` (`swNotificaciones`) con el texto *"Recibir notificaciones"*.
    - `Spinner` (`spIdioma`) conectado a un `string-array` de recursos (`@array/idiomas`) con opciones: *Español, Inglés, Italiano y Portugues*.

---

### 🔹 Bloque 4: Integración con Firebase (Autenticación y Base de Datos)
17. **Configuración Inicial de Firebase:**
    - Registro de la app en la consola de Firebase.
    - Agregado del archivo `google-services.json` y configuración de dependencias (BoM, Analytics, Auth y Firestore) en `build.gradle.kts`.
18. **Firebase Authentication (Registro y Login):**
    - Creación de `RegistroActivity` para permitir a los usuarios crear cuentas nuevas.
    - Modificación de `MainActivity` para que el inicio de sesión utilice `auth.signInWithEmailAndPassword()`.
19. **Arquitectura y Reestructuración (views y models):**
    - Para mantener el código limpio, se crearon los paquetes `views` (para todas las Activities) y `models` (para las clases de datos como `Usuario.kt` y `Lectura.kt`).
    - Actualización automática de `AndroidManifest.xml` con las nuevas rutas.

---

### 🔹 Bloque 5: CRUD Completo con Firestore (Crear, Leer, Actualizar, Eliminar)
20. **Modelo de Datos (`Lectura.kt`):**
    - Creación de una `data class` utilizando la anotación `@DocumentId` para que Firestore asigne y reconozca automáticamente el ID de cada documento. Atributos: `id`, `descripcion`, `valor`.
21. **Leer / Mostrar Datos en Tiempo Real (Read):**
    - Diseño de `activity_lista.xml` (con RecyclerView) y el diseño de cada fila `item_lectura.xml`.
    - Implementación de `LecturaAdapter` para conectar los datos visuales.
    - Implementación de `ListaActivity` usando `db.collection("lecturas").addSnapshotListener` para escuchar cambios en la base de datos de manera reactiva (en tiempo real).
22. **Crear y Actualizar Datos (Create & Update):**
    - Creación de `FormularioLecturaActivity` reutilizable.
    - **Modo Crear:** Accesible desde un Botón Flotante (`FloatingActionButton`). Usa `db.collection("lecturas").add(nuevaLectura)`.
    - **Modo Editar:** Accesible al hacer clic en un elemento de la lista. Pasa los datos a través del `Intent` (ID, Descripción, Valor) y usa `db.collection("lecturas").document(id).set(lecturaActualizada)` para sobreescribir el documento.
23. **Eliminar Datos (Delete):**
    - Agregado de un botón de basurero rojo en `item_lectura.xml`.
    - Uso de funciones Lambda en el `LecturaAdapter` para delegar el evento de clic a la Activity.
    - Implementación de un `AlertDialog` de confirmación antes de eliminar.
    - Borrado en Firestore mediante `db.collection("lecturas").document(id).delete()`.

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje:** Kotlin
- **Diseño UI:** Android XML (`ConstraintLayout`, `LinearLayout`, Material Components)
- **Base de Datos / Backend:** Firebase (Authentication, Firestore, Analytics)
- **Arquitectura:** Separación en paquetes (views, models, adapters)
- **Componentes Avanzados:** `RecyclerView`, `AlertDialog`, `FloatingActionButton`, `SnapshotListener`
- **SDK Mínimo:** Android API 24+
- **Control de Versiones:** Git & GitHub CLI (`gh`)
- **Documentación:** Código 100% comentado línea por línea de forma didáctica.
