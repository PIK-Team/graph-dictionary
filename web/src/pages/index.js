import React from "react"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'


const HomePage = () => {
  return (
    <Container>
		<Header></Header>
		<SubpageHeader subpageName="Strona główna"></SubpageHeader>
		<MainWrapper>
			<span>Graph Dictionary</span>
			TEST SZABLON TEST
		</MainWrapper>
		<Footer></Footer>
    </Container>
  )
}

export default HomePage