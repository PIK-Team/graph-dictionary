import * as React from 'react'
import { Link, useStaticQuery, graphql } from "gatsby"
import Img from "gatsby-image"
import * as headerStyle from '../styles/header_component.module.css'

const Header = () => {
	const data = useStaticQuery( graphql`
	  query {
		  weitiLogo: file(relativePath: { eq: "weiti_logo.png" }) {
			childImageSharp {
					fixed(height: 120) {
						...GatsbyImageSharpFixed
			  }
			}
		  }
		}
	`)
	return (
		<div className={headerStyle.headerWrapper}>
			<div className={headerStyle.headerImgWrapper}>
				<Img fixed={data.weitiLogo.childImageSharp.fixed} />
			</div>
			<Link to="/">
				<div className={headerStyle.headerTitle}>
					Projekt PIK - Słownik
				</div>
			</Link>
		</div>
	)
}

export default Header
