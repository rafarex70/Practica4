import { setTimeout } from 'timers/promises';
import {db} from "../models/index.js"
import weatherservice from "../services/weatherservice.js"
import toposervice from "../services/toposervice.js"

export const root = {
    eolicPlants: async () => await db.eolicplants.findAll(),
    
    createEolicPlant : async ({city}) => {
      const planning = { result: city };
      async function requestWeatherservice(city) {
        let weather = await weatherservice(city);
        planning.result = planning.result + '-' + weather;
      }
      async function requestToposervice(city) {
          let landscape = await toposervice(city);
          planning.result = planning.result + '-' + landscape;
      }
      try {
        console.log(city);
        await Promise.all([requestWeatherservice(city), requestToposervice(city)])

        await simulateProcessWaiting();

        let respuesta = await db.eolicplants.create({
            city: city,
            planning: formatPlanning(planning.result)
          })
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

function formatPlanning(planning) {
  if (planning.toLowerCase().charCodeAt(0)>'m'.charCodeAt(0)) {
      return planning.toUpperCase();
  } else return planning.toLowerCase();
}

async function simulateProcessWaiting() {
  await setTimeout(Math.random() * 2000 + 1000);
}