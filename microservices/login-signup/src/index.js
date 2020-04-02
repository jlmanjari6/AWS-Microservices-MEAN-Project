import express from "express";
import cors from "cors";

import "dotenv/config";
import con from "./mysql";

const app = express();

app.use(cors());
app.use(express.json());

app.listen(process.env.PORT, () =>
  console.log(`Profile app listening on port ${process.env.PORT}!`)
);

// GET API for landing page
app.get("/profile", (req, res) => {
  res.send("Hello world - Profile serviceee!");
});

 // POST api to save user to db
 app.post("/profile/users", (req, res) => {

  const {
      body: { firstName, lastName, email, password, age, gender } = {}
  } = req;

  if (firstName && lastName && email && gender) {
      console.log("Register Request received");
      con.connect(err => {
          con.query(
              `INSERT INTO ccgroup7.users (firstname, lastname, email, age, gender)
      VALUES ('${firstName}', '${lastName}', '${email}', '${age}', '${gender}')`,
              (err, result, fields) => {
                  if (err) res.send(err);
                  if (result) res.send(result);                        
              }
          );
      });
  } else {
      console.log("Missing a parameter");
  }
});

// GET api to get user ID by email
app.get("/profile/users/:email", (req, res) => {
  const { params: { email } = {} } = req;
  con.connect(err => {
      con.query(
          `select id from ccgroup7.users where email='${email}';`,
          (err, result, fields) => {
              if (err) res.send(err);
              if (result) res.send(result);
          }
      );
  });
});

