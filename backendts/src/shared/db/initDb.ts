import db from "./sqlite";

export function initDb(){
    db.exec(`
        CREATE TABLE IF NOT EXISTS inventory(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        description TEXT NOT NULL,
        category TEXT NOT NULL
            CHECK(category in( 'RING', 'CHAIN', 'BRACELET', 'EARRINGS' , 'ROSARY' )),
        stock INTEGER NOT NULL CHECK(stock >= 0 ),
        price REAL NOT NULL CHECK ( price > 0 ),
        status TEXT NOT NULL DEFAULT 'ACTIVE'
            CHECK (status in('ACTIVE', 'INACTIVE')),
        image_url TEXT NOT NULL,
        created_at TEXT NOT NULL DEFAULT (datetime('now')),
        updated_at TEXT NOT NULL DEFAULT(datetime('now'))
        );
    `)
}