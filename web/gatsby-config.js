module.exports = {
  siteMetadata: {
    title: 'Graph Dictionary',
    description: 'Grab your data.',
    author: 'Piotr Frątczak, Mikołaj Bieńkowski, Paweł Budniak, Maciej Adamski'
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
    `gatsby-plugin-react-helmet`,
    `gatsby-plugin-image`,
    {
      resolve: `gatsby-source-filesystem`,
      options: {
        name: `images`,
        path: `${__dirname}/src/images`,
      },
    },
    `gatsby-transformer-sharp`,
    `gatsby-plugin-sharp`,
    {
      resolve: `gatsby-plugin-manifest`,
      options: {
        name: `gatsby-starter-default`,
        short_name: `starter`,
        start_url: `/`,
        background_color: `#663399`,
        theme_color: `#663399`,
        display: `minimal-ui`,
        icon: `src/images/icon.png`,
      },
    },
    `gatsby-plugin-gatsby-cloud`,
  ],
};
