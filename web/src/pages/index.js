import React from "react"
import Container from "../components/container"
import { graphql } from 'gatsby';

const HomePage = ({data}) => {
  return (
    <Container>
      <h1>Graph Dictionary</h1>
      {data.springApiPeople.page.totalElements} total elements.
    </Container>
  )
}

export const query = graphql`
query MyQuery {
  springApiPeople {
    page {
      totalElements
    }
  }
}
`

export default HomePage