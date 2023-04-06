'use strict';

/**
 * return-product service
 */

const { createCoreService } = require('@strapi/strapi').factories;

module.exports = createCoreService('api::return-product.return-product');
