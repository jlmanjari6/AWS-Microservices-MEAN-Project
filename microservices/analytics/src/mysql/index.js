import "dotenv/config";
import mysql from "mysql";

import { DB_CREATIION } from "./querries";

const CONNECTION_CONFIG = {
  host: process.env.MYSQL_ENDPOINT,
  user: process.env.MYSQL_USERNAME,
  password: process.env.MYSQL_PASSWORD
};

const con = mysql.createConnection(CONNECTION_CONFIG);

const callback = (error, result, fields) => {
  // console.log(result);
};

con.connect(function(err) {
  if (err) throw err;
  console.log("Database Connected!");
  con.query("CREATE DATABASE IF NOT EXISTS ccgroup7;");
  con.query("USE ccgroup7;");
  // mysql table cration script
  con.query(DB_CREATIION, callback);
  //   con.end();
});

export default con;
