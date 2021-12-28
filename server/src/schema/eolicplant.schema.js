import { buildSchema } from 'graphql';

// Construct a schema, using GraphQL schema language
export const schema = buildSchema(`
type Query {
  eolicPlants: [EolicPlant!]!
}

type Mutation {
  createEolicPlant(city: String!): EolicPlant
  deleteEolicPlant(id: ID!): String    
}

type EolicPlant {
  id: ID!
  city: String!
  planning: String
}
`);