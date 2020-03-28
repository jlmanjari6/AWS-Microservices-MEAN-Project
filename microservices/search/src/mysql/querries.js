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
    \`numberOfHits\` INT NULL,
    \`imageURL\` VARCHAR(255) NULL,
    PRIMARY KEY (\`id\`))
  ENGINE = InnoDB;

  -- -----------------------------------------------------
  -- Table \`ccgroup7\`.\`attractions\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`ccgroup7\`.\`attractions\` (
    \`id\` INT NOT NULL AUTO_INCREMENT,
    \`name\` VARCHAR(45) NOT NULL,
    \`description\` VARCHAR(255) NULL,
    \`imageURL\` VARCHAR(255) NULL,
    \`locationId\` INT NOT NULL,
    PRIMARY KEY (\`id\`),
    INDEX \`location_idx\` (\`locationId\` ASC),
    CONSTRAINT \`location_attraction\`
      FOREIGN KEY (\`locationId\`)
      REFERENCES \`ccgroup7\`.\`locations\` (\`id\`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '	';
  `
};
