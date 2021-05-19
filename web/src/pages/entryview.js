import React from "react"
import {Link} from "gatsby"
import queryString from "query-string"
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
						<li> <Link to={`?dictionary=${dictionary}&entry=${ entry.word.word } `}> { entry.word.word == thisWord ? <span style={{fontWeight: "bold"}}> {entry.word.word} </span> :<span> {entry.word.word}</span> } </Link> {entry.subentries.length > 0 && showEntries(entry.subentries, dictionary, thisWord)} </li>
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
						</div> </div> : getThisEntryInfo(entries[0].subentries, thisWord) 
	)
}


const test = (thisWord) => {
	return (
		<div> {thisWord}  </div>
	)
}

export default class NewDictionary extends React.Component {
	
	values = queryString.parse(this.props.location.search)
	
	dictionaryParam = this.values.dictionary
	entryParam = this.values.entry
	
	state = {
		entry: null
	}

	componentDidMount() {
		fetch('http://localhost:9090/entries/'+this.dictionaryParam+'/'+this.entryParam+'/overview', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => this.setState({entry:json}));
	}
	
	API = {
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
		const { entry } = this.state
		
		if ( this.entryParam == undefined || this.dictionaryParam == undefined)
		{
			return (
				<Container>
					<Header></Header>
					<SubpageHeader subpageName="Wpis w słowniku"></SubpageHeader>
					<MainWrapper>
					<div className={entryViewStyle.entryWrapper}>
						
						<div>Należy podać oba parametry - dictionary oraz entry <br />
						?dictionary=[nazwa_slownika]&entry=[wpis]</div>
						
					</div>
					</MainWrapper>
					<Footer></Footer>
				</Container>

			)
		}
		
		if ( entry === null) { 
		
			return (
				<Container>
					<Header></Header>
					<SubpageHeader subpageName="Wpis w słowniku"></SubpageHeader>
					<MainWrapper>
					<div className={entryViewStyle.entryWrapper}>
						{console.log("Test")}
						{console.log(this.entryParam)}
						ŁADOWANIE
						
					</div>
					</MainWrapper>
					<Footer></Footer>
				</Container>

			)
			
		}
		
		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Wpis w słowniku"></SubpageHeader>
				<MainWrapper>
				<div className={entryViewStyle.entryWrapper}>
					<div className={entryViewStyle.entryInfo, entryViewStyle.entryWrapperElement}>
					
							{ getThisEntryInfo([this.state.entry], this.entryParam) }
							
						<div className={indexStyle.indexButtonDiv}><Link to="#"  style={{width: "80%", fontSize: "10pt"}} className={indexStyle.indexButton}>Dodaj wpis potomny</Link></div>
						
					</div>
					<div className={entryViewStyle.entryTree, entryViewStyle.entryWrapperElement}>
						<div style={{marginBottom: "25px"}}> Drzewo wpisu: </div>
						
								{showEntries([this.state.entry], this.dictionaryParam, this.entryParam) }
						
					</div>
				</div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}