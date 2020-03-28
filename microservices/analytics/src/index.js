import express from "express";
import cors from "cors";

import "dotenv/config";
import mysqlCon from "./mysql";

const app = express();

app.use(cors());
app.use(express.json());

app.listen(process.env.PORT, () =>
  console.log(`Analytics app listening on port ${process.env.PORT}!`)
);

// GET API landing home page
app.get("/analytics", (req, res) => {
  res.send("Hello World - Analytics!");
});

// GET api to get top searched places
app.get("/analytics/topsearchedplaces", (req, res) => {
  console.log("Number of top searched places received to create");
  mysqlCon.connect(err => {
    mysqlCon.query(
      `select numberOfHits as count, name from locations 
                  order by numberOfHits desc
                  limit 5;`,
      (err, result, fields) => {
        if (err) res.send(err);
        if (result) res.send(result);
      }
    );
  });

  // POST api to increase the number of hits
app.post("/search/location", (req, res) => {
  const {
      body: {
          locationId
      } = {}
  } = req;
  if (locationId) {
      console.log("Number of hits increment Request received");
      con.connect(err => {
          con.query(
              `UPDATE ccgroup7.locations SET numberOfHits = numberOfHits+1 WHERE id = ${locationId};`,
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
});

