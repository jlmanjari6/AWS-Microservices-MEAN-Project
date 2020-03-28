import express from "express";
import cors from "cors";
import "dotenv/config";
import con from "./mysql";

const app = express();

app.use(cors());
app.use(express.json());

app.listen(process.env.PORT, () =>
  console.log(`Search app listening on port ${process.env.PORT}!`)
);

// GET api to get landing page
app.get("/search", (req, res) => {
  res.send("Hello World - Search!");
});

// GET api to get attraction details by keyword
app.get("/search/locations/:location", (req, res) => {
  const { params: { location } = {} } = req;
  console.log("Search location request received");
  con.connect(err => {
      con.query(
          `SELECT l.id, l.name as city, a.name as attraction, a.description,a.imageURL
          FROM 
          ccgroup7.locations AS l 
          JOIN 
          ccgroup7.attractions AS a
          ON l.id=a.locationId
          WHERE l.id IN (SELECT id FROM locations WHERE name = '${location}') 
          OR
          a.id IN (SELECT id FROM attractions WHERE name LIKE '%${location}%');  
          `,
          (err, result, fields) => {
              if (err) res.send(err);
              if (result) res.send(result);
          }
      );
  });
});


