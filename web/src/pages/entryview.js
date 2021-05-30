import React from "react"
import {Link} from "gatsby"
import Tree from "react-tree-graph"
import queryString from "query-string"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as entryViewStyle from '../styles/entryview.module.css'
import * as indexStyle from '../styles/index.module.css'
import 'react-tree-graph/dist/style.css'


const showEntries = (entries, dictionary, thisWord) => {
	return (
		<ul className={entryViewStyle.entryViewList}>
			{ entries.map(entry => (
						<li> <a href={`?dictionary=${ dictionary }&entry=${ entry.word }`}> { entry.word === thisWord ? <span style={{fontWeight: "bold"}}> {entry.word} </span> :<span> {entry.word}</span> } </a> {entry.subentries.length > 0 && showEntries(entry.subentries, dictionary, thisWord)} </li>
					)
				)
			}
		</ul>
	)
}

const getThisEntryInfo = (entries, thisWord) => {
	console.log(entries)
	console.log(entries[0])
	return (
		 entries[0].word == thisWord ? <div> <div className={entryViewStyle.defName}>Nazwa wpisu:
							<span style={{fontWeight: "bold"}}> {entries[0].word}</span>
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

const simplifyEntriesArray = (entries) =>
{
	entries.word = entries.word.word
	{ entries.subentries.map(subEntry => ( simplifyEntriesArray(subEntry)) ) }
	return entries
}

export default class EntryView extends React.Component {
	
	values = queryString.parse(this.props.location.search)
	
	dictionaryParam = this.values.dictionary
	entryParam = this.values.entry
	
	state = {
		entry: null
	}
	
	

	componentDidMount() {
		fetch(process.env.API_URL+'entries/'+this.dictionaryParam+'/'+this.entryParam+'/overview', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => simplifyEntriesArray(json))
		.then(json => this.setState({entry:json}));
	}
	
	
	render() {
		const { entry } = this.state
		
		if ( this.entryParam === undefined || this.dictionaryParam === undefined)
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
							
						<div className={indexStyle.indexButtonDiv}><Link to={`/newentry?dictionary=${this.dictionaryParam}&parent=${this.entryParam}`}  style={{width: "80%", fontSize: "10pt"}} className={indexStyle.indexButton}>Dodaj wpis potomny</Link></div>
						
					</div>
					<div className={entryViewStyle.entryTree, entryViewStyle.entryWrapperElement}>
						<div style={{marginBottom: "25px"}}> Drzewo wpisu: </div>
						
								{showEntries([this.state.entry], this.dictionaryParam, this.entryParam) }
								
								{console.log(this.state.entry)}
								<Tree data={this.state.entry}
										height={400}
										width={400}
										getChildren={ node => node.subentries }
										keyProp={"word"}
										labelProp={"word"}
								/>
						
					</div>
				</div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}