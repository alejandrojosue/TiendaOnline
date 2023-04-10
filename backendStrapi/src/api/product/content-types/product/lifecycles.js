module.exports = {
    async beforeCreate(event) {
        const { data, where, select, populate } = event.params;

        // let's do a 20% discount everytime
        //   event.params.data.price = event.params.data.price * 0.8;

    },

    async afterCreate(event) {
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
    },

    async afterFindMany(e) {
        const { result, params } = e;
        // strapi.io.emit("products", `get`);
    }
};