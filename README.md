# Aplicación Joyería — Offline-First Mobile + Auto-Sync

[![Expo](https://img.shields.io/badge/Expo-React%20Native-black)](https://expo.dev/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-API-green)](https://spring.io/projects/spring-boot)
[![SQLite](https://img.shields.io/badge/SQLite-Local%20DB-blue)](https://www.sqlite.org/index.html)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Remote%20DB-blue)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](#licencia)

Aplicación móvil **offline-first** para gestión de **inventario, clientes y ventas** en una joyería, con **sincronización automática** hacia un backend en **Spring Boot**.  
El objetivo es mantener la app funcional **sin conexión**, almacenar cambios localmente y sincronizarlos cuando exista conectividad.

---

## ✨ Funcionalidades

- **Inventario**
  - Alta/edición/baja de productos
  - Control de stock y estado (activo/inactivo)
  - Imágenes (local/URL según configuración)
- **Clientes**
  - Registro rápido o cliente formal
- **Ventas**
  - Venta con cliente registrado o venta rápida
  - Métodos de pago (cash/card/transfer/installments)
  - Detalle de venta (productos, cantidades, totales)
- **Offline-First**
  - Persistencia local con **SQLite**
  - Operación completa sin internet
- **Auto-Sync**
  - Cola/bitácora de cambios (create/update/delete)
  - Reintentos automáticos y sincronización por lotes
  - Resolución básica de conflictos (configurable)

> Nota: Algunas funciones pueden estar en progreso según el roadmap.

---

## 🧱 Arquitectura (alto nivel)

- **Mobile (Expo / React Native)**
  - DB local: SQLite
  - Repositorios/servicios locales para CRUD
  - Módulo Sync: detecta conectividad, envía cambios pendientes al API y actualiza el estado de sincronización

- **Backend (Spring Boot)**
  - API REST para entidades (productos, clientes, ventas)
  - DB remota: PostgreSQL
  - Endpoints para sync (batch upsert / confirmación de operaciones)

---

## 📁 Estructura del repositorio (Monorepo)

```txt
aplicacion-joyeria/
  apps/
    mobile/               # Expo / React Native
    api/                  # Spring Boot
  docs/                   # Diagramas, decisiones, notas de arquitectura
  infra/                  # Docker, scripts, etc. (opcional)
  README.md