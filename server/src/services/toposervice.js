import axios from 'axios';

export default async function (city){
    try {
        const response = await axios.get('http://localhost:8080/api/topographicdetails/'+city);
        console.log("TopoService Response: "+response.data.landscape);
        return response.data.landscape;
      } catch (error) {
        console.error(error);
        return 'nd';
      }
}



