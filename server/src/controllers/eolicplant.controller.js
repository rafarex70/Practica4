import { setTimeout } from 'timers/promises';
import {db} from "../models/index.js"
import producerKafka from "../services/producerKafka.js"

export const root = {
    eolicPlants: async () => await db.eolicplants.findAll(),
    
    createEolicPlant : async ({city}) => {

      try {
        console.log(city);

        await simulateProcessWaiting();

        let respuesta = await db.eolicplants.create({
            city: city,
            planning: null
          })
        var planningKafka = {
          id: respuesta.id,
          city: city
        }
        producerKafka(planningKafka)
        return respuesta;
      } catch(err) {
        console.error(err);
      }
    },
  
    deleteEolicPlant : async ({id}) => {
      try {
        let respuesta;
        await db.eolicplants.destroy({
          where: { id: id }
        })
        .then(num => {
          if (num == 1) respuesta='EolicPlant was deleted successfully!'
          else respuesta='Cannot delete EolicPlant with id='+id+'. Maybe EolicPlant was not found!'
        })
        return respuesta;
      } catch(err) {
        console.error(err);
      }
    },
  };

async function simulateProcessWaiting() {
  await setTimeout(Math.random() * 2000 + 1000);
}