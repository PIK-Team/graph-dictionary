import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as indexStyle from '../styles/index.module.css'

const HomePage = () => {
  return (
    <Container>
		<Header></Header>
		<SubpageHeader subpageName="Strona główna"></SubpageHeader>
		<MainWrapper>
			<div className={indexStyle.indexButtonDiv}><Link to="/test1/" className={indexStyle.indexButton}>Stwórz słownik</Link></div>
			<div className={indexStyle.indexButtonDiv}><Link to="/test1/" className={indexStyle.indexButton}>Lista słowników</Link></div>
			
		</MainWrapper>
		<Footer></Footer>
    </Container>
  )
}

export default HomePage