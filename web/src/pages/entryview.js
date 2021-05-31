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
import '../styles/entryTree.css'


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
	if (entries[0] != undefined)
	{
		if (entries[0].word == thisWord)
		{
			return (
				<div> <div className={entryViewStyle.defName}>Nazwa wpisu:
					<span style={{fontWeight: "bold"}}> {entries[0].word}</span>
					</div>
					<div> Definicje:
						<ul>
						{ entries[0].definitions.map(definitionObj => (
							<li> {definitionObj.definition} </li>
						))}
						</ul>
				</div> </div>
			)
		}
		
		return getThisEntryInfo(entries[0].subentries, thisWord)
	}
}

const simplifyEntriesArray = (entries) =>
{
	entries.word = entries.word.word
	{ entries.subentries.map(subEntry => ( simplifyEntriesArray(subEntry)) ) }
	return entries
}

const prepareDataForTree = (entries, thisWord, thisDictionary) =>
{
	{ entries.map(entry => {
		if (entry.word == thisWord)
		{
			entry.gProps = { className:  "node treeWord treeThisWord", 
							onClick: function (event, nodeKey) 
									{
										window.location.href = `?dictionary=${ thisDictionary }&entry=${ nodeKey }`
									}
							}
		}
		else
		{
			entry.gProps = { className: "node treeWord", 
							onClick: function (event, nodeKey) 
										{
											window.location.href = `?dictionary=${ thisDictionary }&entry=${ nodeKey }`
										}
							}
		}
		
		prepareDataForTree(entry.subentries, thisWord, thisDictionary)
	}) }
}

const calcWidthTree = (data) =>
{
	if (data.subentries.length == 0)
	{
		return 100
	}
	
	let width = calcWidthTree(data.subentries[0])
	width = width + 100
	return width
}

const calcHeightTree = (data) =>
{
	if (data[0].subentries.length == 0)
	{
		let height = 0
		data.map(entry => {
			height = height + 1
		})
		height = height * 60
		return height
	}
	
	return calcHeightTree(data[0].subentries)
}

export default class EntryView extends React.Component {
	
	values = queryString.parse(this.props.location.search)
	
	state = {
		dictionaryParam: this.values.dictionary,
		entryParam: this.values.entry,
		entry: null
	}
	
	
	componentDidMount() {
		fetch(process.env.API_URL+'entries/'+this.state.dictionaryParam+'/'+this.state.entryParam+'/overview', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => {
			simplifyEntriesArray(json)
			prepareDataForTree([json], this.state.entryParam, this.state.dictionaryParam)
			
			return json
		})
		.then(json => this.setState({entry:json}))
	}
	
	
	render() {
		const {entry} = this.state
		
		if ( this.state.entryParam === undefined || this.state.dictionaryParam === undefined)
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

								{ getThisEntryInfo([this.state.entry], this.state.entryParam) }
							
						</div>
						<div className={entryViewStyle.entryTree, entryViewStyle.entryWrapperElement}>
									
							<div className={indexStyle.indexButtonDiv}><Link to={`/newentry?dictionary=${this.state.dictionaryParam}&parent=${this.state.entryParam}`}  style={{width: "80%", fontSize: "10pt"}} className={indexStyle.indexButton}>Dodaj wpis potomny</Link></div>
							
						</div>
					</div>
					<div style={{textAlign: "center", marginTop: "50px", marginBottom: "25px"}}> Drzewo wpisu: </div>
					<div style={{textAlign: "center"}}>
						<Tree data={this.state.entry}
										height={ calcHeightTree([this.state.entry]) }
										width={ calcWidthTree(this.state.entry) + 200 }
										getChildren={ node => node.subentries }
										keyProp={"word"}
										labelProp={"word"}
										textProps = {{ dx: 10 }}
									
								/>
					</div>
					<div style= {{ paddingBottom: "300px" }}></div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}