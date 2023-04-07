'use strict';

module.exports = {
  /**
   * An asynchronous register function that runs before
   * your application is initialized.
   *
   * This gives you an opportunity to extend code.
   */
  register( /*{ strapi }*/) { },

  /**
   * An asynchronous bootstrap function that runs before
   * your application gets started.
   *
   * This gives you an opportunity to set up your data model,
   * run jobs, or perform some special logic.
   */
  bootstrap({ strapi }) {
    const io = require('socket.io')(strapi.server.httpServer, {
      path: '/socket/v1/',
      cors: { origin: '*', methods: ["GET", "POST", "PUT"] },
      credential: true,
    })
    strapi.io = io
    io.on('connection', function (socket) {
      console.log(socket.io);
      io.emit("test", "test hellow there");
      console.log("red the code");
      io.on('bla', data => {
        // do something with data
      })
    })

  },
};