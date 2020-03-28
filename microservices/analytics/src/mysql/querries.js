module.exports = {
  DB_CREATIION: `
  CREATE SCHEMA IF NOT EXISTS \`analytics\` DEFAULT CHARACTER SET latin1 ;
  USE \`analytics\` ;

  -- -----------------------------------------------------
  -- Table \`analytics\`.\`locations\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`analytics\`.\`locations\` (
    \`id\` INT NOT NULL AUTO_INCREMENT,
    \`name\` VARCHAR(45) NOT NULL,
    \`numberOfHits\` INT NULL,
    \`imageURL\` VARCHAR(255) NULL,
    PRIMARY KEY (\`id\`))
  ENGINE = InnoDB;  
  `
};
