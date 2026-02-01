import * as SQLite from 'expo-sqlite';

export const dbPromise = (async () => {
  const db = await SQLite.openDatabaseAsync('Jewelry.db');
  await db.execAsync('PRAGMA journal_mode = WAL');
  await db.execAsync('PRAGMA foreign_keys = ON');
  return db;
})();
