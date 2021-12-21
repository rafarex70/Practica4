export default (sequelize, Sequelize) => {
    const EolicPlant = sequelize.define("eolicplant", {
      city: {
        type: Sequelize.STRING
      },
      planning: {
        type: Sequelize.STRING
      }
    });
  
    return EolicPlant;
  };