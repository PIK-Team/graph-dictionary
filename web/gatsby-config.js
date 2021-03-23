module.exports = {
  siteMetadata: {
    title: 'Graph Dictionary',
    description: 'Grab your data.'
  },
  plugins: [
    {
      resolve: 'gatsby-source-multi-api',
      options: {
        apis: [
          {
            prefix: 'springApi',
            baseUrl: "http://localhost:8080",
            endpoints: ['people', 'profile'],
          }
        ],
      },
    },
  ],
};
