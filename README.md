💈 Sistema de Gestión de Peluquería
Sistema de escritorio desarrollado en Java con Swing para la administración completa de una peluquería. Permite gestionar clientes, servicios, turnos y visualizar estadísticas en tiempo real.

📋 Descripción del Proyecto
Sistema de Gestión de Peluquería es una aplicación de escritorio robusta que implementa una arquitectura en capas (MVC) para crear un sistema mantenible y escalable. El sistema incluye gestión completa de clientes con historial, catálogo de servicios con precios dinámicos, agenda de turnos con validación de disponibilidad, y un dashboard con métricas del negocio en tiempo real.

🎯 Funcionalidades Principales
👥 Gestión de Clientes
✅ Registro completo de clientes con datos de contacto
✅ Búsqueda por nombre, apellido o teléfono
✅ Edición y eliminación de clientes
✅ Validación de datos (teléfono, email)
✅ Historial de fechas de registro
✂️ Gestión de Servicios
✅ Catálogo de servicios con categorías (Corte, Tintura, Peinado, etc.)
✅ Control de precios y duración de cada servicio
✅ Activar/desactivar servicios
✅ Filtrado por tipo de servicio
✅ Búsqueda por nombre o descripción
📅 Gestión de Turnos
✅ Creación de turnos con fecha y hora específica
✅ Validación de disponibilidad de horarios
✅ Estados: Pendiente, Confirmado, Completado, Cancelado, Ausente
✅ Sistema de pagos: Efectivo, Débito, Crédito, Transferencia
✅ Control de estado de pago: Pendiente, Pagado, Abonado Parcial
✅ Cálculo automático de saldo pendiente
✅ Filtrado por estado y fecha
✅ Búsqueda por cliente
📊 Dashboard
✅ Estadísticas en tiempo real
✅ Total de clientes registrados
✅ Servicios activos
✅ Turnos del día
✅ Ingresos diarios calculados automáticamente
✅ Vista detallada de turnos de hoy

🛠️ ¿Qué tecnologías usa?

Lenguaje: Java 8+
GUI: Java Swing (Nimbus Look and Feel)
Base de Datos: MySQL
Conexión: JDBC (Java Database Connectivity)
IDE: NetBeans (compatible con IntelliJ IDEA y Eclipse)
Arquitectura: MVC (Model-View-Controller) en capas

🎮 Uso de la Aplicación
Menú Principal
El sistema cuenta con una barra de navegación superior con las siguientes secciones:

🏠 Dashboard - Vista principal con estadísticas
👥 Clientes - Gestión completa de clientes
✂️ Servicios - Administración de servicios
📅 Turnos - Agenda y gestión de turnos
🚪 Salir - Cerrar la aplicación

🏛️ Patrones de Diseño Aplicados
🔹 Singleton (DatabaseConfig)
Propósito: Garantizar una única instancia de conexión a la base de datos.
Implementación: La clase DatabaseConfig mantiene una única conexión compartida por toda la aplicación, evitando múltiples conexiones innecesarias.
🔹 MVC (Model-View-Controller)
Propósito: Separar la lógica de negocio, presentación y control.
Capas:

Model: Entidades (Cliente, Servicio, Turno) + Enums
View: Frames, Panels y Dialogs (Swing)
Controller: Coordinación entre Vista y Servicios

🔹 DAO (Data Access Object)
Propósito: Abstraer y encapsular el acceso a datos.
Implementación: Interfaces DAO + Implementaciones separadas para cada entidad.
🔹 Service Layer
Propósito: Centralizar la lógica de negocio y validaciones.
Implementación: Capa intermedia entre Controllers y DAOs con validaciones de negocio.
🔹 Exception Handling Hierarchy
Propósito: Manejo estructurado de errores por capa.
Tipos:

DAOException - Errores de persistencia
ServiceException - Errores de lógica de negocio
ValidacionException - Errores de validación de datos

🚀 Próximas Mejoras

 Sistema de recordatorios por email/SMS.
 Reportes PDF de ventas diarias/mensuales.
 Sistema de usuarios con roles (Admin, Recepcionista).
 Integración con WhatsApp.
 Backup automático de base de datos.
 Sistema de promociones y descuentos.
 Módulo de inventario de productos.
 Calendario visual para agendar turnos.
 Impresión de comprobantes.

 📞 Contacto
 
Email: franco97chocou@outlook.com

📄 Licencia
Este proyecto es de código abierto y está disponible bajo la licencia MIT.
