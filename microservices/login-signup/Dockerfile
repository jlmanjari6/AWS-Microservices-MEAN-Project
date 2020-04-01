FROM node:10
WORKDIR /app/
COPY ./microservices/login-signup/package*.json /app/
RUN npm install
COPY ./microservices/login-signup/ /app/
ENV MYSQL_ENDPOINT=ccgroup7-profiledb.cdpqrynmovyz.us-east-1.rds.amazonaws.com
ENV MYSQL_USERNAME=ccgroup7
ENV MYSQL_PASSWORD=ccgroup7
ENV PORT=5000
EXPOSE 5000
CMD ["npm", "start"]