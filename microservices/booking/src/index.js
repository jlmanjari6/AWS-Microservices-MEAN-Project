import express from "express";
import cors from "cors";

import "dotenv/config";
import con from "./mysql";

const app = express();

app.use(cors());
app.use(express.json());

app.listen(process.env.PORT, () =>
  console.log(`Booking app listening on port ${process.env.PORT}!`)
);

// GET api to get landing page
app.get("/booking", (req, res) => {
  res.send("Hello World - Booking!");
});

// POST api to save ticket details
app.post("/booking/ticket", (req, res) => {
  const {
    body: {
      userId,
      origin,
      destination,
      travelDate,
      bookingDate,
      noOfSeats,
      busId
    } = {}
  } = req;
  if (
    userId &&
    origin &&
    destination &&
    travelDate &&
    bookingDate &&
    noOfSeats &&
    busId
  ) {
    console.log("Ticket Generation Request received");
    con.connect(err => {
      con.query(
        `INSERT INTO ccgroup7.tickets (userId, origin, destination, travelDate, bookingDate, noOfSeats, busId) 
          VALUES ('${userId}', '${origin}', '${destination}', '${travelDate}',
          current_timestamp(), '${noOfSeats}',  '${busId}')`,
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

// GET api to get list of locations
app.get("/booking/locations", (req, res) => {
  const { params: { email } = {} } = req;
  console.log("Locations list request received");
  con.connect(err => {
    con.query(
      `SELECT * FROM ccgroup7.locations;`,
      (err, result, fields) => {
        if (err) res.send(err);
        if (result) res.send(result);
      }
    );
  });
});

// GET api to get list of buses for selected destination
app.get("/booking/buses/:locationId", (req, res) => {
  const { params: { locationId } = {} } = req;
  console.log("Buses list request received");
  con.connect(err => {
    con.query(
      `SELECT * FROM ccgroup7.buses WHERE locationId = ${locationId};`,
      (err, result, fields) => {
        if (err) res.send(err);
        if (result) res.send(result);
      }
    );
  });
});

// GET api to get number of bookings in past 7 days
app.get("/booking/noOfBookings", (req, res) => {
  console.log("Number of bookings received");
  con.connect(err => {
    con.query(
      `select destination,count(*) as count from tickets
        where day(bookingDate)   between   (day(current_timestamp())-7)   and   (day(current_timestamp()))
        group by destination;`,
      (err, result, fields) => {
        if (err) res.send(err);
        if (result) res.send(result);
      }
    );
  });
});

// GET api to get ticket details of current logged in user
app.get("/booking/tickets/user/:userId", (req, res) => {
  const { params: { userId } = {} } = req;
  console.log("Ticket history request received");
  con.connect(err => {
    con.query(
      `select t.id as ticketId, t.userId,t.origin,t.destination, t.travelDate, t.bookingDate, t.noOfSeats, b.Id as busId, b.busNo, b.startTime, b.endTime, b.fare
      from tickets as t join buses as b on t.busId = b.id where t.userId = '${userId}';`,
      (err, result, fields) => {
        if (err) res.send(err);
        if (result) res.send(result);
      }
    );
  });
});


