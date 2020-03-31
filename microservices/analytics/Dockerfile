FROM node:10
WORKDIR /app/
COPY package*.json /app/
RUN npm install
COPY . /app/
ENV MYSQL_ENDPOINT=ccgroup7-analytics-db.c5gpjnnit1xh.us-east-1.rds.amazonaws.com
ENV MYSQL_USERNAME=ccgroup7
ENV MYSQL_PASSWORD=ccgroup7
ENV PORT=3000
EXPOSE 3000
CMD ["npm", "start"]