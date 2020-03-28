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
  -- Table \`ccgroup7\`.\`buses\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`ccgroup7\`.\`buses\` (
    \`id\` INT NOT NULL,
    \`busNo\` VARCHAR(45) NOT NULL,
    \`locationId\` INT NOT NULL,
    \`startTime\` VARCHAR(45) NULL,
    \`endTime\` VARCHAR(45) NULL,
    \`fare\` INT NULL,
    PRIMARY KEY (\`id\`),
    INDEX \`location_idx\` (\`locationId\` ASC),
    CONSTRAINT \`location_bus\`
      FOREIGN KEY (\`locationId\`)
      REFERENCES \`ccgroup7\`.\`locations\` (\`id\`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB;

  -- -----------------------------------------------------
  -- Table \`ccgroup7\`.\`tickets\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`ccgroup7\`.\`tickets\` (
    \`id\` INT(11) NOT NULL AUTO_INCREMENT,
    \`userId\` INT NOT NULL,
    \`origin\` VARCHAR(255) NOT NULL,
    \`destination\` VARCHAR(255) NOT NULL,
    \`travelDate\` DATETIME NOT NULL,
    \`bookingDate\` DATETIME NOT NULL,
    \`noOfSeats\` INT(11) NULL DEFAULT 1,
    \`busId\` INT NOT NULL,
    PRIMARY KEY (\`id\`),
    INDEX \`user_idx\` (\`userId\` ASC),
    INDEX \`bus_idx\` (\`busId\` ASC),
    CONSTRAINT \`user_ticket\`
      FOREIGN KEY (\`userId\`)
      REFERENCES \`ccgroup7\`.\`users\` (\`id\`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
    CONSTRAINT \`bus_ticket\`
      FOREIGN KEY (\`busId\`)
      REFERENCES \`ccgroup7\`.\`buses\` (\`id\`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 38
  DEFAULT CHARACTER SET = latin1;
  `
};
