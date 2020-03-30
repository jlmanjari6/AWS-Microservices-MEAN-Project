FROM node:10
WORKDIR /app/
COPY package*.json /app/
RUN npm install
COPY . /app/
ENV MYSQL_ENDPOINT=ccgroup7-booking-db.cr47oobqtee8.us-east-1.rds.amazonaws.com
ENV MYSQL_USERNAME=ccgroup7
ENV MYSQL_PASSWORD=ccgroup7
ENV PORT=4000
EXPOSE 4000
CMD ["npm", "start"]