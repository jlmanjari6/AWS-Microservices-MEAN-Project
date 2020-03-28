module.exports = {
  DB_CREATIION: `
  CREATE SCHEMA IF NOT EXISTS \`ccgroup7\` DEFAULT CHARACTER SET latin1 ;
  USE \`ccgroup7\` ;

  -- -----------------------------------------------------
  -- Table \`ccgroup7\`.\`users\`
  -- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS \`ccgroup7\`.\`users\` (
    \`id\` INT(11) NOT NULL AUTO_INCREMENT,
    \`firstname\` VARCHAR(30) NULL DEFAULT NULL,
    \`lastname\` VARCHAR(30) NULL DEFAULT NULL,
    \`email\` VARCHAR(255) NULL DEFAULT NULL,
    \`password\` VARCHAR(30) NULL DEFAULT NULL,
    \`gender\` VARCHAR(30) NULL DEFAULT NULL,
    \`age\` INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (\`id\`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARACTER SET = latin1;
  `
};
