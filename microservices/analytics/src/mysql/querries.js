module.exports = {
  DB_CREATIION: `
  CREATE SCHEMA IF NOT EXISTS \`ccgroup7\` DEFAULT CHARACTER SET latin1 ;
  USE \`ccgroup7\` ;

  -- -----------------------------------------------------
  -- Table \`ccgroup7\`.\`locations\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`ccgroup7\`.\`locations\` (
    \`id\` INT NOT NULL AUTO_INCREMENT,
    \`name\` VARCHAR(45) NOT NULL,
    \`numberOfHits\` INT NULL
    PRIMARY KEY (\`id\`))
  ENGINE = InnoDB;  
  `
};
