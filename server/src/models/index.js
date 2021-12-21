import { DB, USER, PASSWORD, HOST, dialect as _dialect, pool as _pool } from "../config/db.config.js";
import EolicPlant from "./eolicplant.model.js";
import Sequelize from "sequelize";

export var sequelize = new Sequelize(DB, USER, PASSWORD, {
  host: HOST,
  dialect: _dialect,
  operatorsAliases: false,

  pool: {
    max: _pool.max,
    min: _pool.min,
    acquire: _pool.acquire,
    idle: _pool.idle
  }
});

export var db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;


db.eolicplants = EolicPlant(sequelize, Sequelize);


