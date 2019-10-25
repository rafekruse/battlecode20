Object.defineProperty(exports, "__esModule", { value: true });
const gameworld_1 = require("./gameworld");
exports.GameWorld = gameworld_1.default;
const gameworld = require("./gameworld");
exports.gameworld = gameworld;
const metadata_1 = require("./metadata");
exports.Metadata = metadata_1.default;
const metadata = require("./metadata");
exports.metadata = metadata;
const soa_1 = require("./soa");
exports.StructOfArrays = soa_1.default;
const soa = require("./soa");
exports.soa = soa;
const simulator = require("./simulator");
exports.simulator = simulator;
const match_1 = require("./match");
exports.Match = match_1.default;
const game_1 = require("./game");
exports.Game = game_1.default;
// import { schema } from 'battlecode-schema';
// import { flatbuffers } from 'flatbuffers';
const battlecode_schema_1 = require("battlecode-schema");
exports.flatbuffers = battlecode_schema_1.flatbuffers;
exports.schema = battlecode_schema_1.schema;
// TODO provide ergonomic main export
