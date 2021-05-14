import * as React from 'react'
import { Link, useStaticQuery, graphql } from "gatsby"
import Img from "gatsby-image"
import * as footerStyle from '../styles/footer_component.module.css'


	
const Footer = () => {
	const autorzy = [
		{
			imie: "Mikołaj",
			nazwisko: "Bieńkowski"
		},
		{ 
			imie: "Maciej",
			nazwisko: "Adamski"
		},
		{
			imie: "Piotr",
			nazwisko: "Frątczak"
		},
				{
			imie: "Piotr",
			nazwisko: "Budniak"
		}
	]
	return (
		<div className={footerStyle.wrapper}>
			<div className={footerStyle.authors}>
				{autorzy.map(autor => (
					<div className={footerStyle.author}>{autor.imie} {autor.nazwisko}</div>
				))}
			</div>
		</div>
	)
}

export default Footer
