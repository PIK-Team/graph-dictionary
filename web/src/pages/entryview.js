import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as formStyle from '../styles/forms.module.css'
import * as entryViewStyle from '../styles/entryview.module.css'
import * as indexStyle from '../styles/index.module.css'


const showEntries = (entries, dictionary, thisWord) => {
	return (
		<ul className={entryViewStyle.entryViewList}>
			{ entries.map(entry => (
						<li> <Link to={`dictionary/${ entry.word.word }/ `}> { entry.word.word == thisWord ? <span style={{fontWeight: "bold"}}> {entry.word.word} </span> :<span> {entry.word.word}</span> } </Link> {entry.subentries.length > 0 && showEntries(entry.subentries, thisWord)} </li>
					)
				)
			}
		</ul>
	)
}

const getThisEntryInfo = (entries, thisWord) => {
	return (
		 entries[0].word.word == thisWord ? <div> <div className={entryViewStyle.defName}>Nazwa wpisu:
							<span style={{fontWeight: "bold"}}> {entries[0].word.word}</span>
						</div>
						<div> Definicje:
							<ul>
							{ entries[0].definitions.map(definitionObj => (
								<li> {definitionObj.definition} </li>
							))}
							</ul>
						</div> </div>: getThisEntryInfo(entries[0].subentries, thisWord) 
	)
}


export default class NewDictionary extends React.Component {
	word = "word 1.2"
	
	entry = {
		"id": 193,
		"word": {
			"word": "testura89"
		},
		"definitions": [],
		"subentries": [
			{
				"id": 192,
				"word": {
					"word": "word1"
				},
				"definitions": [],
				"subentries": [
					{
						"id": 201,
						"word": {
							"word": "word 1.2"
						},
						"definitions": [
							{
								"id": 207,
								"definition": "def4"
							},
							{
								"id": 200,
								"definition": "def3"
							}
						],
						"subentries": [
							{
								"id": 210,
								"word": {
									"word": "word2.2"
								},
								"definitions": [],
								"subentries": []
							},
							{
								"id": 204,
								"word": {
									"word": "word2.1"
								},
								"definitions": [],
								"subentries": []
							}
						]
					}
				]
			}
		]
	}
	
	
	render() {
		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Wpis w słowniku"></SubpageHeader>
				<MainWrapper>
				<div className={entryViewStyle.entryWrapper}>
					<div className={entryViewStyle.entryInfo, entryViewStyle.entryWrapperElement}>

						{ getThisEntryInfo([this.entry], this.word) }
							
						<div className={indexStyle.indexButtonDiv}><Link to="#"  style={{width: "80%", fontSize: "10pt"}} className={indexStyle.indexButton}>Dodaj wpis potomny</Link></div>
						
					</div>
					<div className={entryViewStyle.entryTree, entryViewStyle.entryWrapperElement}>
						<div style={{marginBottom: "25px"}}> Drzewo wpisu: </div>
						{showEntries([this.entry], this.word)}
					</div>
				</div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}