module.exports = {
    beforeCreate(event) {
        const { data, where, select, populate } = event.params;

        // let's do a 20% discount everytime
        //   event.params.data.price = event.params.data.price * 0.8;

    },

    afterCreate(event) {
        const { result, params } = event;

        // do something to the result;
        strapi.io.emit("products", "create new");

    },

    async afterUpdate(event) {
        const { result, params } = event;
        strapi.io.emit("products", "update");
        strapi.io.emit("test", result);
    },

    async afterFindMany(e) {
        const { result, params } = e;
        strapi.io.emit("products", `get`);
    }
};