module.exports = ({ env }) => ({
    email: {
        config: {
            provider: 'sendgrid',
            providerOptions: {
                apiKey: env('SENDGRID_API_KEY'),
            },
            settings: {
                defaultFrom: 'alejandro.diaz@ujcv.edu.hn',
                defaultReplyTo: 'alejandro.diaz@ujcv.edu.hn',
            },
        },
    },
});