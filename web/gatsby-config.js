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
            prefix: 'api',
            // baseUrl: "http://localhost:9090",
            baseUrl: "https://agile-badlands-90260.herokuapp.com/",
            endpoints: ['people', 'profile'],
          }
        ],
      },
    },
  ],
};
