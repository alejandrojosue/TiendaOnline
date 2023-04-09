module.exports = {
    beforeCreate(event) {
        const { data, where, select, populate } = event.params;

        // let's do a 20% discount everytime
        //   event.params.data.price = event.params.data.price * 0.8;

    },

    afterCreate(event) {
        const { result, params } = event;

        // do something to the result;
        // strapi.io.emit("products", "create new");
    },

    async afterUpdate(event) {
        const { result, params } = event;
        const res = {
            id: result.id,
            SKU: result.SKU,
            Name: result.Name,
            Price: result.Price,
            Quantity: result.Quantity,
            Description: result.Description,
            publishedAt: result.publishedAt
        }
        strapi.io.emit("products", result);
        // strapi.io.emit("test", result);
        try {
            await strapi.plugins['email'].services.email.send({
                to: 'alejandro.diaz@ujcv.edu.hn',
                from: 'alejandro.diaz@ujcv.edu.hn', // e.g. single sender verification in SendGrid
                // cc: 'valid email address',
                // bcc: 'valid email address',
                // replyTo: 'valid email address',
                subject: 'The Strapi Email plugin worked successfully',
                text: `${result.Description}`, // Replace with a valid field ID
                html: '<h1>Hello world!</h1>',

            })
        } catch (err) {
            console.log(err);
        }


    },

    async afterFindMany(e) {
        const { result, params } = e;
        // strapi.io.emit("products", `get`);
    }
};