import Database from "better-sqlite3";
import path from "path";
import fs from "fs";

const dataDir = path.join(process.cwd(), "data");//To provide the path of the DB from the directory when the project runs 
if(!fs.existsSync(dataDir)) fs.mkdirSync(dataDir, { recursive: true});

const dbPath = path.join(dataDir, "jewelry.db");//Add the name of the database on the path

const db = new Database( dbPath );

db.pragma("journal_mode = WAL");
db.pragma("foreign_keys = ON");//Allow foreign keys 

export default db;