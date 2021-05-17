import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as formStyle from '../styles/forms.module.css'
import * as entryViewStyle from '../styles/entryview.module.css'

const showEntries = (entries) => {
	return (
		<ul className={entryViewStyle.entryViewList}>
			{ entries.map(entry => (
						<li> <Link to="#"> {entry.bold ? <span style={{fontWeight: "bold"}}> {entry.name} </span> :<span> {entry.name}</span> } </Link> {entry.nextLevel.length > 0 && showEntries(entry.nextLevel)} </li>
					)
				)
			}
		</ul>
	)
}

export default class NewDictionary extends React.Component {
	entries = [
		{
			name: "abcd",
			bold: 0,
			nextLevel: [
				{
					name: "222222",
					bold: 0,
					nextLevel: [
						{
							name: "33333",
							bold: 1,
							nextLevel: [
								{
									name: "44444.1",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.2",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.3",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.4",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.5",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.6",
									bold: 0,
									nextLevel: []
								},
								{
									name: "44444.7",
									bold: 0,
									nextLevel: []
								},
								
							]
						}
					]
				}
			]
		}
	]
	
	
	render() {
		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Wpis w słowniku"></SubpageHeader>
				<MainWrapper>
				
					{showEntries(this.entries)}

				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}