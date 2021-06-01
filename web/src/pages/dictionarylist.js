import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryListStyle from '../styles/dictionarylist.module.css'


export default class DictionaryList extends React.Component {
	
	state = {
		dictionaryList: null
	}

    componentDidMount() {
		fetch(process.env.API_URL+'dictionaries/dicts', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => this.setState({dictionaryList:json}));
	}	
	
	render() {

        if ( this.state.dictionaryList === null) { 
		
			return (
				<Container>
					<Header></Header>
                    <SubpageHeader subpageName="Lista słowników"></SubpageHeader>
					<MainWrapper>
                        <center>
						    ŁADOWANIE
						</center>
					</MainWrapper>
					<Footer></Footer>
				</Container>

			)
			
		}
            

		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Lista słowników"></SubpageHeader>
				<MainWrapper>
                    <div className={dictionaryListStyle.list}>
                        {this.state.dictionaryList.map(dictionary => (
                            <Link to={`/dictionaryview?dictionary=${dictionary.dictionaryName}`}>
                                <div className={dictionaryListStyle.row}>
                                    { dictionary.imageURI != "" && dictionary.imageURI != null && <img src={dictionary.imageURI} alt={`Obrazek ${dictionary.dictionaryName}`} width="120" height="120"></img> }
									{ ( dictionary.imageURI == "" || dictionary.imageURI == null) && <span style={{width: "120px"}}></span> }
                                    <div>{dictionary.description}</div>
                                    <div className={dictionaryListStyle.dictName}>{dictionary.dictionaryName}</div>
                                </div>
                            </Link>
                        ))}
                    </div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}