import React from "react"
import Container from "../components/container"
import { graphql } from 'gatsby';
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'


const HomePage = ({data}) => {
  return (
    <Container>
		<Header></Header>
		<SubpageHeader subpageName="Strona główna"></SubpageHeader>
      <h1>Graph Dictionary</h1>
      Rignt now we have a total of <b> {data.apiPeople.page.totalElements} </b> elements in our database :)
    </Container>
  )
}

export const query = graphql`
query MyQuery {
  apiPeople {
    page {
      totalElements
    }
  }
}
`

export default HomePage