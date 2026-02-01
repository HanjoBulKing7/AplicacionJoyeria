import * as SQLite from "expo-sqlite";
import { dbPromise } from "../SQLite";
import { InsertJewel } from '../../domain/DomainSQLite'

export const dbPromiseInit = (async () => {
  const db = await dbPromise;

  await db.execAsync(`
    CREATE TABLE IF NOT EXISTS jewel (
      id TEXT PRIMARY KEY NOT NULL,
      name TEXT NOT NULL,
      description TEXT,
      category TEXT NOT NULL,
      stock INTEGER NOT NULL DEFAULT 0,
      price REAL NOT NULL DEFAULT 0,
      status TEXT NOT NULL DEFAULT 'active',
      image_url TEXT,
      created_at TEXT DEFAULT (datetime('now')),
      updated_at TEXT DEFAULT (datetime('now'))
    );
  `);
    const TestItem: InsertJewel= {
        id: "b025978f-c566-4867-b098-f2559502f75c",
        name: "Johan",
        description: "Anillo bonito de oro",
        category: "ring",
        stock: 10,
        price: 1000,
        status: 'active',
        image_url: null
    }
  //Seed the table during development 
  await db.runAsync(`
      INSERT OR IGNORE INTO jewel
      ( id, name, description, category, stock, price, status )
      VALUES 
      ( ? , ? , ? , ? , ? , ? , ? );
    `, Object.values ( TestItem));
  return db;
})();
