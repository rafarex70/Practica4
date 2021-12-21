import express from 'express';
import cors from 'cors';
import { graphqlHTTP } from 'express-graphql';
import {db} from "./models/index.js"
import {root} from "./controllers/eolicplant.controller.js"
import {schema} from "./schema/eolicplant.schema.js"
import { __dirname } from './config/dirname.js';

const app = express();

//Allow requests from browser apps loaded in other domain
app.use(cors());

app.use('/graphql', graphqlHTTP({
  schema: schema,
  rootValue: root,
  graphiql: true,
}));

app.use(express.static(__dirname + '/../../browser'));

db.sequelize.sync();

app.listen(3000, ()=>{
    console.log('Running a GraphQL API server at http://localhost:3000/graphql');
});
