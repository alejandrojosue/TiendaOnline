'use strict';

/**
 * shipper service
 */

const { createCoreService } = require('@strapi/strapi').factories;

module.exports = createCoreService('api::shipper.shipper');
